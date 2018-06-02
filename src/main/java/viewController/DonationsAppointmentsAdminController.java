package viewController;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DonationSchedule;
import model.Illness;
import model.dto.DonationScheduleStatusDTO;
import model.Reservation;
import model.dto.UserIllnessDto;
import org.springframework.context.ApplicationContext;
import service.DonationAppointmentsAdminService;
import service.TCPService;
import utils.CommonUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DonationsAppointmentsAdminController {
    public DonationsAppointmentsAdminController(){}

    private ObservableList<DonationScheduleStatusDTO> model=FXCollections.observableArrayList();

    ApplicationContext context = CommonUtils.getFactory();

    private TCPService service;

    @FXML
    private TableView<DonationScheduleStatusDTO> paginationTableView;

    @FXML
    private TableColumn<DonationScheduleStatusDTO,Integer> NumarRezervare;

    @FXML
    private TableColumn<DonationScheduleStatusDTO,String> Status;

    @FXML
    private TableColumn<DonationScheduleStatusDTO,String> NumePacient;

    @FXML
    private TableColumn<DonationScheduleStatusDTO,DateCell> DataDonarii;

    @FXML
    TableColumn<DonationScheduleStatusDTO, String> actionColumn;

    @FXML
    private JFXComboBox<String> statusComboBox;

    private Stage currentInfoWindow;

    @FXML
    public void handleModificaStatus(ActionEvent actionEvent){
        DonationScheduleStatusDTO ds = paginationTableView.getSelectionModel().getSelectedItem();
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
        service = context.getBean(TCPService.class);
        service = context.getBean(TCPService.class);
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
        List<DonationScheduleStatusDTO> bun = new ArrayList<>();

        bun = service.getAllDonationStatus();
        this.model=FXCollections.observableArrayList(bun);

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


        NumarRezervare.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatusDTO,Integer>("idDS"));
        NumePacient.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatusDTO,String>("name"));
        //LocuriDisponibile.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatusDTO,Integer>("availableSpots"));
        DataDonarii.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatusDTO,DateCell>("donationDateTime"));
        Status.setCellValueFactory(new PropertyValueFactory<DonationScheduleStatusDTO,String>("status"));
        actionColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getIdDS().toString()));
        actionColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                final Button infoButton = new Button("");
                super.updateItem(item, empty);
                infoButton.getStyleClass().add("button");
                //System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUU");
                infoButton.setGraphic(new ImageView("./images/info.png"));
                //System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYY");
                infoButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        /*FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/viewController/smallDonationScheduleInfoWindow.fxml"));*/
                       // AnchorPane root = null;
                        try {
                            //System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                            //--AnchorPane root = FXMLLoader.load(DonationsController.class.getResource("/viewController/boli.fxml"));
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(DonationsAppointmentsAdminController.class.getResource("/viewController/smallDonationScheduleInfoWindow.fxml"));
                            AnchorPane root = (AnchorPane) loader.load();
                            Stage dialogStage = new Stage();
                            dialogStage.setTitle("");
                            dialogStage.initModality(Modality.WINDOW_MODAL);
                            Scene scene = new Scene(root);
                            dialogStage.setScene(scene);
                            column.getTableView().getSelectionModel().select(getIndex());
                            DonationScheduleStatusDTO donationScheduleStatusDTO= (DonationScheduleStatusDTO)paginationTableView.getSelectionModel().getSelectedItem();
                            dialogStage.initStyle(StageStyle.TRANSPARENT);
                            //System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

                            List<Illness> illnessArrayList = service.getIllnessPacient(donationScheduleStatusDTO.getIdU());
                            System.out.println("ARE ILLNESS");
                            System.out.println(illnessArrayList.size());
                            UserIllnessDto userIllnessDto = service.getUserIllness(donationScheduleStatusDTO);
                            //Illness illness = illnessArrayList.get(0);
                            //System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

                            SmallDonationScheduleInfoWindow smallDonationScheduleInfoWindow = loader.getController();
                            smallDonationScheduleInfoWindow.setEntity(userIllnessDto);
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

        paginationTableView.setItems(model);

        paginationTableView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<DonationScheduleStatusDTO>() {
                    @Override
                    public void changed(ObservableValue<? extends DonationScheduleStatusDTO> observable,
                                        DonationScheduleStatusDTO oldValue, DonationScheduleStatusDTO newValue) {
                        if (newValue!=null){
                            statusComboBox.setValue(newValue.getStatus());
                        }
                    }
                });

    }

    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
