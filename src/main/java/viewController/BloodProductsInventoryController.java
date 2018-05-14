package viewController;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.BloodProduct;
import model.DTO.BloodProductShipmentAddressDTO;
import model.DTO.BloodRequestHospitalDTO;
import model.DTO.DonationReceiverNameBloodGroupDTO;
import model.ProductType;
import service.LoginService;
import service.TCPService;
import utils.CommonUtils;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class BloodProductsInventoryController {

    @FXML
    ComboBox productTypeComboBox;

    @FXML
    TableColumn<BloodProduct,Integer> productIdColumn;

    @FXML
    TableColumn<BloodProduct, ProductType> productTypeColumn;

    @FXML
    TableColumn<BloodProduct, LocalDate> validityColumn;

    @FXML
    TableColumn<BloodProduct, Double> quantityColumn;

    @FXML
    TableColumn<BloodProduct, String> donatedForColumn;

    @FXML
    TableColumn<BloodProduct, String> actionColumn;

    @FXML
    TableColumn<BloodProduct, String> bloodGroupColumn;

    @FXML
    TableView bloodProductsTable;


    private double xOffset = 0;
    private double yOffset = 0;
    private ArrayList<DonationReceiverNameBloodGroupDTO> donationReceiverNameBloodGroupDTOS;
    private ArrayList<BloodProductShipmentAddressDTO> bloodProductShipmentAddressDTOs;
    private ArrayList<BloodProduct> allBloodProducts;
    private ObservableList<BloodProduct> model;
    private TCPService service;
    private Integer idDC;
    private Stage currentInfoWindow;
    private BloodProductsInventoryController controller;

    public void initialize(){
        service = CommonUtils.getFactory().getBean(TCPService.class);

        productIdColumn.setCellValueFactory(new PropertyValueFactory<BloodProduct, Integer>("idBP"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<BloodProduct, ProductType>("productType"));
        validityColumn.setCellValueFactory(new PropertyValueFactory<BloodProduct, LocalDate>("validUntil"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<BloodProduct, Double>("quantity"));
        controller = this;

        idDC = service.getDonationCenterIdForUser(LoginService.getIdU());
        donationReceiverNameBloodGroupDTOS = service.getAllDonationReceiverNames();
        allBloodProducts = service.getAllBloodProducts();
        bloodProductShipmentAddressDTOs = service.getAllBloodProductShipmentForDonationCenter(idDC);

        donatedForColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BloodProduct, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BloodProduct, String> param) {
                String name = donationReceiverNameBloodGroupDTOS.get(donationReceiverNameBloodGroupDTOS.indexOf(new DonationReceiverNameBloodGroupDTO(param.getValue().getIdD(),null,null))).getReceiverName();
                if(name!=null)
                    return new SimpleStringProperty( name);
                return new SimpleStringProperty("---");
            }
        });

        bloodGroupColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BloodProduct, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BloodProduct, String> param) {
                return new SimpleStringProperty(donationReceiverNameBloodGroupDTOS.get(donationReceiverNameBloodGroupDTOS.indexOf(new DonationReceiverNameBloodGroupDTO(param.getValue().getIdD(),null,null))).getBloodGroup());
            }
        });

        actionColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getValidUntil().toString()));
        actionColumn.setCellFactory(column -> {
            return new TableCell<BloodProduct, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    column.getTableView().getSelectionModel().select(getIndex());
                    BloodProduct selectedBloodProduct= (BloodProduct)bloodProductsTable.getSelectionModel().getSelectedItem();
                    if(bloodProductShipmentAddressDTOs.contains(new BloodProductShipmentAddressDTO(selectedBloodProduct.getIdBP(),null,null,null,null,null,null))){
                        Integer index = bloodProductShipmentAddressDTOs.indexOf(new BloodProductShipmentAddressDTO(selectedBloodProduct.getIdBP(),null,null,null,null,null,null));
                        BloodProductShipmentAddressDTO bloodProductShipmentAddressDTO= bloodProductShipmentAddressDTOs.get(index);
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
                                    dialogStage.initStyle(StageStyle.TRANSPARENT);
                                    SmallDonationCenterInfoWindowController smallDonationCenterInfoWindowController = loader.getController();
                                    String address = bloodProductShipmentAddressDTO.getStreet()+" "+bloodProductShipmentAddressDTO.getStreetNumber()+" "+bloodProductShipmentAddressDTO.getCity()+" "+bloodProductShipmentAddressDTO.getCountry();
                                    smallDonationCenterInfoWindowController.setEntity(new BloodRequestHospitalDTO(null,null,null,null,"Trimis la "+bloodProductShipmentAddressDTO.getHospitalName(),bloodProductShipmentAddressDTO.getHospitalPhone(),address));
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
                    else {
                        final Button shipButton = new Button("");
                        final Button deleteButton = new Button("");
                        super.updateItem(item, empty);
                        final HBox box = new HBox(shipButton, deleteButton);
                        box.setSpacing(5.0);
                        //box.set
                        shipButton.getStyleClass().add("button");
                        deleteButton.getStyleClass().add("button");
                        shipButton.setGraphic(new ImageView("./images/ship.png"));
                        deleteButton.setGraphic(new ImageView("./images/trash.png"));
                        shipButton.setTooltip(new Tooltip("Trimite"));
                        deleteButton.setTooltip(new Tooltip("Sterge"));
                        shipButton.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent t) {
                                try {


                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(SendBloodProductPanelController.class.getResource("/viewController/sendBloodProductPanel.fxml"));
                                    AnchorPane root = (AnchorPane) loader.load();

                                    Stage dialogStage = new Stage();
                                    dialogStage.setTitle("");
                                    dialogStage.initModality(Modality.WINDOW_MODAL);
                                    //dialogStage.initOwner(primaryStage);
                                    Scene scene = new Scene(root);
                                    dialogStage.setScene(scene);


                                    dialogStage.initStyle(StageStyle.TRANSPARENT);
                                    makePaneMoveble(root, dialogStage);
                                    SendBloodProductPanelController sendBloodProductPanelController = loader.getController();
                                    sendBloodProductPanelController.setService(service, dialogStage, selectedBloodProduct, donationReceiverNameBloodGroupDTOS.get(donationReceiverNameBloodGroupDTOS.indexOf(new DonationReceiverNameBloodGroupDTO(selectedBloodProduct.getIdD(), null, null))).getBloodGroup(),controller);
                                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                                    dialogStage.showAndWait();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent t) {
                                column.getTableView().getSelectionModel().select(getIndex());
                                BloodProduct selectedBloodProduct = (BloodProduct) bloodProductsTable.getSelectionModel().getSelectedItem();
                                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                                confirmation.setHeaderText("Confirmare");
                                confirmation.setContentText("Sunteti sigur ca doriti sa eliminati produsul?");
                                Optional<ButtonType> result = confirmation.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    Boolean ok = service.deleteBloodProduct(selectedBloodProduct.getIdBP());
                                    if (ok) {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setHeaderText("Succes");
                                        alert.setContentText("Produsul a fost eliminat cu succes!");
                                        alert.showAndWait();
                                        loadDataInTable(service.getAllBloodProducts());
                                    } else {
                                        Alert error = new Alert(Alert.AlertType.ERROR);
                                        error.setHeaderText("Esec");
                                        error.setContentText("Eroare la stergerea produsului.Reincercati mai tarziu!");
                                        error.showAndWait();
                                    }
                                }
                            }
                        });

                        if (item != null) {
                            setGraphic(box);
                        }
                    }
                }
            };
        });



        initProductTypeComboBox();

    }

    private void makePaneMoveble(AnchorPane rootLayout, Stage window){
        rootLayout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        rootLayout.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
        });
    }

    public void initProductTypeComboBox(){
        ArrayList<String> productType = new ArrayList<>();
        productType.add("Toate");
        productType.add("Trombocite");
        productType.add("GlobuleRosii");
        productType.add("Plasma");
        productType.add("Sange");
        productTypeComboBox.getItems().addAll(productType);
        productTypeComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                    if(newValue.equals("Toate")){
                        loadDataInTable(allBloodProducts);
                    }
                    else {
                        ArrayList<BloodProduct> filteredList = allBloodProducts.stream().filter(x->x.getProductType().equals(ProductType.valueOf((String) newValue))).collect(Collectors.toCollection(ArrayList::new));
                        loadDataInTable(filteredList);
                    }
                }
        );
        productTypeComboBox.getSelectionModel().select(0);

    }

    public void loadDataInTable(ArrayList<BloodProduct> products){
        model = FXCollections.observableArrayList( products);
        bloodProductsTable.setItems(model);
        bloodProductsTable.refresh();
    }

    public ArrayList<BloodProductShipmentAddressDTO> getBloodProductShipmentAddressDTOs() {
        return bloodProductShipmentAddressDTOs;
    }

    public void setBloodProductShipmentAddressDTOs(ArrayList<BloodProductShipmentAddressDTO> bloodProductShipmentAddressDTOs) {
        this.bloodProductShipmentAddressDTOs = bloodProductShipmentAddressDTOs;
    }

    public ArrayList<BloodProduct> getAllBloodProducts() {
        return allBloodProducts;
    }

    public void setAllBloodProducts(ArrayList<BloodProduct> allBloodProducts) {
        this.allBloodProducts = allBloodProducts;
    }

    public TCPService getService() {
        return service;
    }

    public void setService(TCPService service) {
        this.service = service;
    }
}
