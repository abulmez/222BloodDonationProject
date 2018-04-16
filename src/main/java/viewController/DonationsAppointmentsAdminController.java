package viewController;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DonationCenter;
import model.DonationSchedule;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import service.DonationAppointmentsAdminService;
import utils.CommonUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonationsAppointmentsAdminController {
    public DonationsAppointmentsAdminController(){}

    private ObservableList<DonationSchedule> model=FXCollections.observableArrayList();

    ApplicationContext context = CommonUtils.getFactory();

    private DonationAppointmentsAdminService service;

    @FXML
    private TableView<DonationSchedule> paginationTableView;

    @FXML
    private TableColumn<DonationSchedule,Integer> NumarRezervare,NumarCentruDonatie,LocuriDisponibile;

    @FXML
    private TableColumn<DonationSchedule,String> Status;

    @FXML
    private TableColumn<DonationSchedule,DateCell> DataDonarii;

    @FXML
    private JFXComboBox<String> statusComboBox;

    /*public List<TableSetterGetter> getTableData(){
        List<TableSetterGetter> data =new ArrayList<>();

        return data;
    }*/

    public void setServices(DonationAppointmentsAdminService service) {
        this.service = service;
        service.getAllDonationSchedule().forEach(x->model.add(x));
        paginationTableView.setItems(model);
        //this.service=service;
    }

    @FXML
    public void handleModificaStatus(ActionEvent actionEvent){
        statusComboBox.setItems(null);
    }

    @FXML
    public void initialize(){
        int count=0;
        service = context.getBean(DonationAppointmentsAdminService.class);
        //System.out.println(service.getAllDonationSchedule().size());
        this.model=FXCollections.observableArrayList(service.getAllDonationSchedule());
        //service.getAllDonationSchedule().forEach(x->model.add(x));
        //paginationTableView.setItems(model);
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "WAITING",
                        "REFUSED",
                        "ACCEPTED"
                );

        statusComboBox.setItems(options);
        statusComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("WAITING")){

                }
                if(newValue.equals("REFUSED")){

                }
                if(newValue.equals("ACCEPTED")){

                }
            }
        });


        NumarRezervare.setCellValueFactory(new PropertyValueFactory<DonationSchedule,Integer>("idDS"));
        NumarCentruDonatie.setCellValueFactory(new PropertyValueFactory<DonationSchedule,Integer>("idDC"));
        LocuriDisponibile.setCellValueFactory(new PropertyValueFactory<DonationSchedule,Integer>("availableSpots"));
        DataDonarii.setCellValueFactory(new PropertyValueFactory<DonationSchedule,DateCell>("donationDateTime"));
        Status.setCellValueFactory(new PropertyValueFactory<DonationSchedule,String>("status"));

        paginationTableView.setItems(model);

        paginationTableView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<DonationSchedule>() {
                    @Override
                    public void changed(ObservableValue<? extends DonationSchedule> observable,
                                        DonationSchedule oldValue, DonationSchedule newValue) {
                    }
                });


        //paginationTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showStudenti(newValue));

        //OBSERVERLIST
        /*ObservableList lista=FXCollections.observableList(service.getAllRezervari());
        paginationTableView.setItems(lista);
        paginationTableView.refresh();*/

        /*int pageCount = (count / itemPerPage + 1);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);*/

    }
}
