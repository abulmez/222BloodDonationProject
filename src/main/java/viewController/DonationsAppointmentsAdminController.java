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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DonationCenter;
import model.DonationSchedule;
import model.DonationScheduleStatus;
import model.Reservation;
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

    private ObservableList<DonationScheduleStatus> model=FXCollections.observableArrayList();

    ApplicationContext context = CommonUtils.getFactory();

    private DonationAppointmentsAdminService service;

    @FXML
    private TableView<DonationScheduleStatus> paginationTableView;

    @FXML
    private TableColumn<DonationScheduleStatus,Integer> NumarRezervare,NumarCentruDonatie,LocuriDisponibile;

    @FXML
    private TableColumn<DonationScheduleStatus,String> Status;

    @FXML
    private TableColumn<DonationScheduleStatus,DateCell> DataDonarii;

    @FXML
    private JFXComboBox<String> statusComboBox;

    /*public List<TableSetterGetter> getTableData(){
        List<TableSetterGetter> data =new ArrayList<>();

        return data;
    }*/

    /*public void setServices(DonationAppointmentsAdminService service) {
        this.service = service;
        service.getAllDonationSchedule().forEach(x->model.add(x));
        paginationTableView.setItems(model);
        //this.service=service;
    }*/

    /*public void handleFilt(ActionEvent event){
        try {
            String tipFilt = statusComboBox.getValue().toString();
            if (tipFilt == "WAITING") {

            }
            if (tipFilt == "REFUSED") {

            }
            if (tipFilt == "ACCEPTED") {

            }
        }catch(RuntimeException e){
            showErrorMessage(e.getMessage());
        }
    }*/



    @FXML
    public void handleModificaStatus(ActionEvent actionEvent){
        DonationScheduleStatus ds = paginationTableView.getSelectionModel().getSelectedItem();
        try {
            String tipFilt = statusComboBox.getValue().toString();

            String response= service.handleStatusUpdate(ds.getIdDS(),tipFilt);
            if (!response.equals("Success")){
                Alert alert = new Alert(Alert.AlertType.ERROR,response);
                alert.show();
            }
            this.model=FXCollections.observableArrayList(service.getAllDonationStatus());
            paginationTableView.setItems(model);
        }catch(RuntimeException e){
            showErrorMessage("Nu este selectat nici o programare la donatie");
            //showErrorMessage(e.getMessage());
        }
    }

    @FXML
    public void initialize(){
        int count=0;
        service = context.getBean(DonationAppointmentsAdminService.class);
        //System.out.println(service.getAllDonationSchedule().size());
        //this.model=FXCollections.observableArrayList(service.getAllDonationSchedule());

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "WAITING",
                        "REFUSED",
                        "ACCEPTED"
                );

        List<DonationSchedule> centers=service.getAllDonationSchedule();
        List<Reservation> reservations = service.getAllReservation();
        for(DonationSchedule dc : centers){
            System.out.println(dc.getIdDS());
            System.out.println(dc.getIdDC());
            System.out.println(dc.getDonationDateTime());
            System.out.println(dc.getAvailableSpots());
            //System.out.println(dc.getStatus());
        }

        for (Reservation res : reservations){
            System.out.println(res.getStatus());
        }
        List<DonationScheduleStatus> bun = new ArrayList<>();

        /*for(DonationSchedule donationSchedule : centers){
            for (Reservation reservation : reservations){
                if(donationSchedule.getIdDS() == reservation.getIdDS()){
                    DonationScheduleStatus status = new DonationScheduleStatus(donationSchedule.getIdDS(),donationSchedule.getIdDC(),donationSchedule.getDonationDateTime(),donationSchedule.getAvailableSpots(),reservation.getStatus());
                    bun.add(status);
                }
            }
        }*/
        bun = service.getAllDonationStatus();
        this.model=FXCollections.observableArrayList(bun);

        statusComboBox.setItems(options);
        statusComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("WAITING")){
                    //DonationSchedule ds = paginationTableView.getSelectionModel().getSelectedItem();
                }
                if(newValue.equals("REFUSED")){

                }
                if(newValue.equals("ACCEPTED")){

                }
            }
        });


        NumarRezervare.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatus,Integer>("idDS"));
        NumarCentruDonatie.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatus,Integer>("idDC"));
        LocuriDisponibile.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatus,Integer>("availableSpots"));
        DataDonarii.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatus,DateCell>("donationDateTime"));
        Status.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatus,String>("status"));

        paginationTableView.setItems(model);

        paginationTableView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<DonationScheduleStatus>() {
                    @Override
                    public void changed(ObservableValue<? extends DonationScheduleStatus> observable,
                                        DonationScheduleStatus oldValue, DonationScheduleStatus newValue) {
                        if (newValue!=null){
                            statusComboBox.setValue(newValue.getStatus());
                        }
                    }
                });

        /*int count=0;
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

        List<DonationSchedule> centers=service.getAllDonationSchedule();
        for(DonationSchedule dc : centers){
            System.out.println(dc.getIdDS());
            System.out.println(dc.getIdDC());
            System.out.println(dc.getDonationDateTime());
            System.out.println(dc.getAvailableSpots());
            System.out.println(dc.getStatus());
        }

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
                        if (newValue!=null){
                            statusComboBox.setValue(newValue.getStatus());
                        }
                    }
                });*/

    }

    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
