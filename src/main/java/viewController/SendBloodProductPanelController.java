package viewController;

import com.google.maps.model.LatLng;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.BloodProduct;
import model.dto.BloodProductShipmentAddressDTO;
import model.dto.BloodRequestHospitalDTO;
import service.TCPService;
import utils.googleMaps.Geocoding;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private BloodProductsInventoryController mainWindowsController;


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
                sendButton.setOnAction(t -> {
                    Alert confirmation=new Alert(Alert.AlertType.CONFIRMATION);
                    confirmation.setHeaderText("Confirmare");
                    confirmation.setContentText("Sunteti sigur ca doriti sa trimiteti produsul?");
                    Optional<ButtonType> result = confirmation.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Boolean sent = false;
                        Boolean ok = false;
                        column.getTableView().getSelectionModel().select(getIndex());
                        BloodRequestHospitalDTO bloodRequestHospitalDTO= (BloodRequestHospitalDTO)bloodRequestsTable.getSelectionModel().getSelectedItem();
                        Integer bpID = currentBloodProduct.getIdBP();
                        if(currentBloodProduct.getQuantity()>bloodRequestHospitalDTO.getQuantity() && Math.abs(currentBloodProduct.getQuantity()-bloodRequestHospitalDTO.getQuantity())>50.0){
                            ButtonType yesButton = new ButtonType("Da", ButtonBar.ButtonData.YES);
                            ButtonType noButton = new ButtonType("Nu", ButtonBar.ButtonData.NO);
                            Alert splitAlert=new Alert(Alert.AlertType.CONFIRMATION,"Cantitatea din inventar este mai mare decat cantitatea ceruta. Doriti sa trimiteti cantitatea intreaga?",yesButton,noButton);
                            splitAlert.setHeaderText("Decizie");
                            splitAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            Optional<ButtonType> toSplit = splitAlert.showAndWait();
                            if(toSplit.get() == yesButton){
                                Integer newIdBP = service.splitBloodProduct(currentBloodProduct.getIdBP(),bloodRequestHospitalDTO.getQuantity());
                                ok = service.sendBloodProduct(newIdBP,bloodRequestHospitalDTO.getIdBD());
                                bpID = newIdBP;
                                sent = true;
                            }
                            else{
                                ok = service.sendBloodProduct(currentBloodProduct.getIdBP(),bloodRequestHospitalDTO.getIdBD());
                            }
                        }
                        else{
                            ok = service.sendBloodProduct(currentBloodProduct.getIdBP(),bloodRequestHospitalDTO.getIdBD());
                        }
                        if (ok) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Succes");
                            alert.setContentText("Produsul a fost trimis cu succes!");
                            alert.showAndWait();
                            sent = true;

                        } else {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setHeaderText("Esec");
                            error.setContentText("Eroare la trimiterea produsului. Reincercati mai tarziu!");
                            error.showAndWait();
                        }
                        if (sent){
                            String[] splitAddress = bloodRequestHospitalDTO.getHospitalAdress().split(" ");
                            String number = splitAddress[splitAddress.length-3];
                            String city =  splitAddress[splitAddress.length-2];
                            String country = splitAddress[splitAddress.length-1];
                            StringBuilder street = new StringBuilder();
                            for(int i=0;i<splitAddress.length-3;i++)
                                street.append(splitAddress[i]).append(" ");
                            ArrayList<BloodProductShipmentAddressDTO> bloodProductShipmentAddressDTOs = mainWindowsController.getBloodProductShipmentAddressDTOs();
                            bloodProductShipmentAddressDTOs.add(new BloodProductShipmentAddressDTO(bpID,street.toString(),Integer.parseInt(number),city,country,bloodRequestHospitalDTO.getHospitalName(),bloodRequestHospitalDTO.getHospitalPhone()));
                            mainWindowsController.setBloodProductShipmentAddressDTOs(bloodProductShipmentAddressDTOs);
                            mainWindowsController.setAllBloodProducts(mainWindowsController.getService().getAllBloodProducts());
                            mainWindowsController.loadDataInTable(mainWindowsController.getAllBloodProducts());
                        }
                        handleExit();
                    }



                });
                if (item != null) {
                    setGraphic(sendButton);
                }
            }
        });

    }

    private void loadDataInTable(List<BloodRequestHospitalDTO> requests){
        requests = requests.stream().sorted(Comparator.comparing(BloodRequestHospitalDTO::getPriority)).collect(Collectors.toList());
        model = FXCollections.observableArrayList( requests);
        bloodRequestsTable.setItems(model);
        bloodRequestsTable.refresh();

    }

    public void handleExit() {
        stage.close();
    }

    public void setService(TCPService service, Stage dialogStage, BloodProduct selectedBloodProduct, String bloodGroup, BloodProductsInventoryController controller) {
        this.service = service;
        this.stage = dialogStage;
        this.currentBloodProduct = selectedBloodProduct;
        this.bloodGroup = bloodGroup;
        this.mainWindowsController = controller;
        infoLabel.setText("Trimite "+selectedBloodProduct.getQuantity()+"ml "+selectedBloodProduct.getProductType().toString().toLowerCase()+" catre:");
        ArrayList<BloodRequestHospitalDTO> requests = service.getAllBloodRequestsAndHospitalInfoForProductTypeAndGroup(currentBloodProduct.getProductType(),bloodGroup);
        donationCenterAdress = service.getDonationCenterAddress(selectedBloodProduct.getIdD());
        loadDataInTable(requests);
    }
}
