package viewController;

import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.BloodProduct;
import model.DTO.BloodRequestHospitalDTO;
import model.DTO.DonationReceiverNameBloodGroupDTO;
import service.TCPService;
import utils.googleMaps.Geocoding;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SendBloodProductPanelController {

    @FXML
    Label infoLabel;

    @FXML
    TableColumn<BloodRequestHospitalDTO,String> hospitalNameColumn;

    @FXML
    TableColumn<BloodRequestHospitalDTO,String> hospitalInformationColumn;

    @FXML
    TableColumn<BloodRequestHospitalDTO,String> distanceColumn;

    @FXML
    TableColumn<BloodRequestHospitalDTO,String> priorityColumn;

    @FXML
    TableColumn<BloodRequestHospitalDTO,Double> quantityColumn;

    @FXML
    TableColumn<BloodRequestHospitalDTO,String> patientNameColumn;

    @FXML
    TableColumn<BloodRequestHospitalDTO,String> actionColumn;

    @FXML
    TableView bloodRequestsTable;



    private TCPService service;
    private Stage stage;
    private BloodProduct currentBloodProduct;
    private ObservableList<BloodRequestHospitalDTO> model;
    private String bloodGroup;
    private String donationCenterAdress;
    private LatLng donationCenterCoords;
    private Stage currentInfoWindow;


    public void initialize(){
        hospitalNameColumn.setCellValueFactory(new PropertyValueFactory<>("hospitalName"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("receiverName"));
        hospitalInformationColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getIdBD().toString()));
        hospitalInformationColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                final Button infoButton = new Button("");
                super.updateItem(item, empty);
                infoButton.getStyleClass().add("button");
                infoButton.setGraphic(new ImageView("./images/info.png"));
                infoButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(SendBloodProductPanelController.class.getResource("/viewController/smallDonationCenterInfoWindow.fxml"));
                            AnchorPane root = (AnchorPane) loader.load();
                            Stage dialogStage = new Stage();
                            dialogStage.setTitle("");
                            dialogStage.initModality(Modality.WINDOW_MODAL);
                            Scene scene = new Scene(root);
                            dialogStage.setScene(scene);
                            column.getTableView().getSelectionModel().select(getIndex());
                            BloodRequestHospitalDTO bloodRequestHospitalDTO= (BloodRequestHospitalDTO)bloodRequestsTable.getSelectionModel().getSelectedItem();
                            dialogStage.initStyle(StageStyle.TRANSPARENT);
                            SmallDonationCenterInfoWindowController smallDonationCenterInfoWindowController = loader.getController();
                            smallDonationCenterInfoWindowController.setEntity(bloodRequestHospitalDTO);
                            Point mouse = java.awt.MouseInfo.getPointerInfo().getLocation();
                            dialogStage.setX(mouse.x);
                            dialogStage.setY(mouse.y);

                            currentInfoWindow = dialogStage;

                            dialogStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                infoButton.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        currentInfoWindow.close();
                    }
                });
                if (item != null) {
                    setGraphic(infoButton);
                }
            }
        });
        distanceColumn.setCellValueFactory(param -> {
            Double distance = Geocoding.getDistance(param.getValue().getHospitalAdress(),donationCenterAdress);
            distance/=1000;
            return new SimpleStringProperty( distance.toString() +"km"
            );});
        actionColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getIdBD().toString()));
        actionColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                final Button sendButton = new Button("");
                super.updateItem(item, empty);
                sendButton.getStyleClass().add("button");
                sendButton.setGraphic(new ImageView("./images/send.png"));
                sendButton.setTooltip(new Tooltip("Trimite"));
                sendButton.setOnAction(t -> {handleExit();});
                if (item != null) {
                    setGraphic(sendButton);
                }
            }
        });


    }

    private void loadDataInTable(ArrayList<BloodRequestHospitalDTO> requests){
        model = FXCollections.observableArrayList( requests);
        bloodRequestsTable.setItems(model);
        bloodRequestsTable.refresh();

    }

    public void handleExit() {
        stage.close();
    }

    public void setService(TCPService service, Stage dialogStage, BloodProduct selectedBloodProduct, String bloodGroup) {
        this.service = service;
        this.stage = dialogStage;
        this.currentBloodProduct = selectedBloodProduct;
        this.bloodGroup = bloodGroup;
        infoLabel.setText("Trimite "+selectedBloodProduct.getQuantity()+"ml "+selectedBloodProduct.getProductType().toString().toLowerCase()+" catre:");
        ArrayList<BloodRequestHospitalDTO> requests = service.getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup(currentBloodProduct.getProductType(),bloodGroup);
        donationCenterAdress = service.getDonationCenterAddress(selectedBloodProduct.getIdD());
        loadDataInTable(requests);
    }
}
