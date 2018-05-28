package viewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.BloodProduct;
import model.DTO.BloodRequestDTO;
import model.DTO.DonationCenterDTO;
import model.DTO.DonationReceiverNameBloodGroupDTO;
import model.DonationCenter;
import model.ProductType;
import service.LoginService;
import service.MedicService;
import service.TCPService;
import utils.CommonUtils;
import utils.googleMaps.Geocoding;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DonationCentersMedic {
    @FXML
    private ObservableList<DonationCenterDTO> model;
    @FXML
    private TableColumn<DonationCenterDTO, String> colCenterName;
    @FXML
    private TableColumn<DonationCenterDTO, String> colPhoneNumber;
    @FXML
    private TableColumn<DonationCenterDTO, Integer> colIdProduct;
    @FXML
    private TableColumn<DonationCenterDTO, ProductType> colProductType;
    @FXML
    private TableColumn<DonationCenterDTO, String> colDonatedFor;
    @FXML
    private TableColumn<DonationCenterDTO, Double> colQuantity;
    @FXML
    private TableColumn<DonationCenterDTO, String> colBloodGroup;

    @FXML
    private TableColumn<DonationCenterDTO, String> colValidUntil;
    @FXML
    private TableColumn<DonationCenterDTO, String> colDistance;
    @FXML
    private TableView<DonationCenterDTO> tableView;
    public MedicService service;
    public TCPService serviceTCP;
    public Stage editStage;
    private ArrayList<DonationReceiverNameBloodGroupDTO> donationReceiverNameBloodGroupDTOS;

    public void setService(MedicService service, Stage stage) {
        this.service = service;
        this.editStage = stage;
        loadDataHandler();
    }

    public void initialize() {
        MedicService service1 = CommonUtils.getFactory().getBean(MedicService.class);
        colCenterName.setCellValueFactory(new PropertyValueFactory<DonationCenterDTO, String>("CenterName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<DonationCenterDTO, String>("PhoneNumber"));
        colIdProduct.setCellValueFactory(new PropertyValueFactory<DonationCenterDTO, Integer>("id"));
        colProductType.setCellValueFactory(new PropertyValueFactory<DonationCenterDTO, ProductType>("ProductType"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<DonationCenterDTO, Double>("Quantity"));
        colDonatedFor.setCellValueFactory(new PropertyValueFactory<DonationCenterDTO,String>("receivername"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<DonationCenterDTO,String>("bloodGroup"));
        String adress=service1.handleAdress();

        colValidUntil.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonationCenterDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonationCenterDTO, String> param) {

                LocalDate date = param.getValue().getValidUntil().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return new SimpleStringProperty(date.toString());
            }
        });

        colDistance.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonationCenterDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonationCenterDTO, String> param) {
                Double distanta=Geocoding.getDistance(adress,param.getValue().getAdress())/1000;
                return new SimpleStringProperty(distanta.toString()+"km");
            }
        });
    }
    @FXML
    private void loadDataHandler() {

        List<DonationCenterDTO> list = service.getAllProductsFromCenters();
        String adress=service.handleAdress();
        List<DonationCenterDTO> list2=new ArrayList<>();
        for(DonationCenterDTO d: list){
            if(Geocoding.getDistance(adress,d.getAdress())/1000<10){
                list2.add(d);
            }
        }



        model = FXCollections.observableArrayList(list2);
        tableView.setItems(model);
    }

}
