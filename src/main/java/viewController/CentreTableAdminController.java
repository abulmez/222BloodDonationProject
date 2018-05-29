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
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import utils.AbstractTableController;
import utils.CommonUtils;

import java.util.List;

public class CentreTableAdminController extends AbstractTableController<DonationCenter> {

    CenterInfoService service;
    public static DonationCenter donation;
    ApplicationContext context = CommonUtils.getFactory();

    @FXML
    private TableColumn<DonationCenter,String> idDCcolumn;

    @FXML
    private TableColumn<DonationCenter,String> idAcolumn;

    @FXML
    private TableColumn<DonationCenter,String> donationCentercolumn;

    @FXML
    private TableColumn<DonationCenter,String> phoneCentercolumn;


    private ObservableList<DonationCenter> obs;
    @FXML
    public void initialize(){
        table.setEditable(true);
        service = context.getBean(CenterInfoService.class);
        List<Adress> adressList = service.getAllAdress();

        this.obs = FXCollections.observableArrayList(service.getAllDonationCenter());
        idDCcolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("IdDC"));

//        idAcolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("IdA"));
        idAcolumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DonationCenter,String>,ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DonationCenter,String> p){
                Adress aux = new Adress();
                for (Adress adress : adressList) {
                    if(adress.getIdA() == p.getValue().getIdA())
                        aux = adress;
                }
                return new SimpleStringProperty(aux.getFullAdress());
            }
        });

        donationCentercolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("CenterName"));
        phoneCentercolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("PhoneNumber"));

        donationCentercolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        donationCentercolumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DonationCenter, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DonationCenter, String> event) {
                ((DonationCenter) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setCenterName(event.getNewValue());
                DonationCenter ad=obs.get(event.getTablePosition().getRow());

                int a =service.updateCentre(ad.getIdDC(),ad.getIdA(),ad.getPhoneNumber(),ad.getCenterName());
                System.out.println(a);
            }
        });

        phoneCentercolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCentercolumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<DonationCenter, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<DonationCenter, String> event) {
                ((DonationCenter) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setPhoneNumber(event.getNewValue());
                DonationCenter ad=obs.get(event.getTablePosition().getRow());

                int a =service.updateCentre(ad.getIdDC(),ad.getIdA(),ad.getPhoneNumber(),ad.getCenterName());
                System.out.println(a);
            }
        });



        super.loadData(obs);
    }

    public void handleGet(){
        donation =table.getSelectionModel().getSelectedItem();
    }


}
