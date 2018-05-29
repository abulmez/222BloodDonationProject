package viewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import model.Adress;
import model.DonationCenter;
import model.Hospital;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import service.HospitalService;
import utils.AbstractTableController;
import utils.CommonUtils;

public class HospitalTableAdminController extends AbstractTableController<Hospital> {
    HospitalService service;
    private ObservableList<Hospital> obs;
    ApplicationContext context = CommonUtils.getFactory();
    public static Hospital hospital;
    @FXML
    private TableColumn<Hospital,String> idHcolumn;

    @FXML
    private TableColumn<Hospital,String> idAcolumn;

    @FXML
    private TableColumn<Hospital,String> donationHospitalcolumn;

    @FXML
    private TableColumn<Hospital,String> phoneHospitalcolumn;

    @FXML
    public void initialize(){
        table.setEditable(true);
        service = context.getBean(HospitalService.class);
        serviceAddresses = context.getBean(CenterInfoService.class);
        this.obs= FXCollections.observableArrayList(service.getAllHospitals());
        idHcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("IdH"));
        List<Adress> adressList = serviceAddresses.getAllAdress();

//        idAcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("IdA"));

        idAcolumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Hospital,String>,ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Hospital,String> p){
                Adress aux = new Adress();
                for (Adress adress : adressList) {
                    if(adress.getIdA().equals(p.getValue().getIdA()))
                        aux = adress;
                }
                return new SimpleStringProperty(aux.getFullAdress());
            }
        });

        donationHospitalcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("HospitalName"));
        phoneHospitalcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("PhoneNumber"));

        donationHospitalcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        donationHospitalcolumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Hospital, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Hospital, String> event) {
                ((Hospital) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setHospitalName(event.getNewValue());
                Hospital ad=obs.get(event.getTablePosition().getRow());

                int a =service.updateHospital(ad.getIdH(),ad.getIdA(),ad.getHospitalName(),ad.getPhoneNumber());
                System.out.println(a);
            }
        });

        super.loadData(this.obs);
    }

    public void handleGet(){

        hospital =table.getSelectionModel().getSelectedItem();

        System.out.println(hospital);
    }

}
