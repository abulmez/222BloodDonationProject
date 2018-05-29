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
import model.dto.AdressDTO;
import model.dto.DonationCenterDTO;
import model.dto.DonationReceiverNameBloodGroupDTO;
import model.ProductType;
import service.MedicService;
import service.TCPService;
import utils.CommonUtils;
import utils.googleMaps.Geocoding;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DonationCentersMedicController {
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
    private List<AdressDTO> listAdresses;
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
        listAdresses=new ArrayList<>();
        Integer ok=0;
        List<DonationCenterDTO> donations=service1.getAllProductsFromCenters();
        for(DonationCenterDTO donationDTO:donations){

            for(AdressDTO adressDTO:listAdresses){
                if(adressDTO.getAdress().equals(donationDTO.getAdress())){
                    ok=1;
                }
            }

            if(ok==0){
                listAdresses.add(new AdressDTO(donationDTO.getAdress(),Geocoding.getDistance(adress,donationDTO.getAdress())/1000));
            }
        }

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
                Double distance=0.0;
                for(AdressDTO adressDTO: listAdresses){
                    if(param.getValue().getAdress().equals(adressDTO.getAdress())){
                        distance=adressDTO.getDistance();
                    }
                }
                return new SimpleStringProperty(distance.toString()+"km");
            }
        });
    }
    @FXML
    private void loadDataHandler() {

        List<DonationCenterDTO> list = service.getAllProductsFromCenters();
        List<DonationCenterDTO> list2=new ArrayList<>();
        for(DonationCenterDTO d: list) {
            for (AdressDTO adressDTO : listAdresses) {
                if (adressDTO.getAdress().equals(d.getAdress()) && adressDTO.getDistance()<10) {
                    list2.add(d);
                }
            }
        }



        model = FXCollections.observableArrayList(list2);
        tableView.setItems(model);
    }

}
