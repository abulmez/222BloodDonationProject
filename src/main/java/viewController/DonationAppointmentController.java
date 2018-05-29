package viewController;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.DonationSchedule;
import model.Reservation;
import model.Schedule;
import org.springframework.context.ApplicationContext;
import service.DonationScheduleService;
import service.LoginService;
import utils.CommonUtils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DonationAppointmentController {
    Boolean exist = false;
    Integer howMuchAccepted =0;
    DonationSchedule selected = new DonationSchedule();
    @FXML
    DatePicker datePickerDataDonation;

    @FXML
    TableView<DonationSchedule> tableViewOreDisponibile;

    @FXML
    TableColumn<DonationSchedule,String> tableColumnOreLibere;

    @FXML
    TableColumn<DonationSchedule,String> tableColumnLocuriDisponibile;

    @FXML
    Button buttonTrimiteCerereDonatie;

    @FXML
    Button buttonAnulareCerereDonatie;

    @FXML
    Label statusRezervare;

    @FXML
    Label statusRezervareText;

    private DonationScheduleService donationScheduleService;
    private ObservableList<DonationSchedule> modelSchedule = FXCollections.observableArrayList();
    public void initThings(){
        buttonTrimiteCerereDonatie.setDisable(true);
        statusRezervare.setDisable(true);
        statusRezervareText.setDisable(true);
    }
    @FXML
    public void initialize(){
        ApplicationContext context = CommonUtils.getFactory();
        donationScheduleService = context.getBean(DonationScheduleService.class);
        List<Reservation> reservations = donationScheduleService.getAllReservation();
        initThings();

        for (Reservation reservation : reservations) {
            if(reservation.getIdU() == LoginService.getIdU())
            {
                if(reservation.getStatus().equals("ACCEPTED"))
                    howMuchAccepted++;
                statusRezervare.setDisable(false);
                statusRezervareText.setDisable(false);
                statusRezervareText.setText(reservation.getStatus());
                exist=true;
            }
        }
        datePickerDataDonation.setValue(LocalDate.now());
        datePickerDataDonation.valueProperty().addListener((ov,oldValue,newValue)->{
            buttonTrimiteCerereDonatie.setDisable(true);
            initTableSchedule(newValue);
        });


        initTableSchedule(LocalDate.now());
    }

    void initTableSchedule(LocalDate localDate){
        tableColumnOreLibere.setCellValueFactory(new PropertyValueFactory<DonationSchedule,String>("Ora"));
        tableColumnLocuriDisponibile.setCellValueFactory(new PropertyValueFactory<DonationSchedule,String >("AvailableSpots"));
        setModel(initList(localDate));
    }
    public List<DonationSchedule> initList(LocalDate localDate){
        System.out.println(localDate.getYear());
        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getDayOfMonth());
        //                System.out.println(localDate);
        List<DonationSchedule> fromRequest = donationScheduleService.getAllDonationSchedule();
        List<DonationSchedule> auxList = new ArrayList<>();
        for (DonationSchedule donationScheduleRares : fromRequest) {
            if (donationScheduleRares.getAn() == localDate.getYear())
            {
                if(donationScheduleRares.getLuna() == localDate.getMonthValue())
                    if(donationScheduleRares.getZi() == localDate.getDayOfMonth()){
                    DonationSchedule aux = donationScheduleRares;
                    aux.setAvailableSpots(donationScheduleService.settingAvailableSpots(aux.getIdDS()));
                    auxList.add(aux);
                    }

            }
        }
        System.out.println(auxList);
        return auxList;
    }
    public void setModel(List<DonationSchedule> list)
    {
        Collections.sort(list,new Comparator<DonationSchedule>(){
            @Override
            public int compare(DonationSchedule r1,DonationSchedule r2)
            {
                return r1.getOra().compareTo(r2.getOra());
            }
        });
        if(list.isEmpty())
        {
            this.modelSchedule.setAll();
            tableViewOreDisponibile.setItems(this.modelSchedule);
            tableViewOreDisponibile.getSelectionModel().selectedIndexProperty().addListener((obs,old,newS)->{
                if(exist==false)
                buttonTrimiteCerereDonatie.setDisable(false);
            });
        }
        else{
            this.modelSchedule.setAll(list);
            tableViewOreDisponibile.setItems(this.modelSchedule);
            tableViewOreDisponibile.getSelectionModel().selectedIndexProperty().addListener((obs,old,newS)->{
                if(exist==false)
                buttonTrimiteCerereDonatie.setDisable(false);
                selected = tableViewOreDisponibile.getSelectionModel().getSelectedItem();
            });
        }
    }
    public void update(List<DonationSchedule> list){
        setModel(list);
    }

    @FXML
    public void onActionButtonTrimiteCerereDonatie(){
        if(howMuchAccepted<3)
        {
            String response = donationScheduleService.addReservation(selected.getIdDS(),LoginService.getIdU());
            if(response.equals("Success"))
                statusRezervareText.setText("ATTEMPTING");
            showMessage(Alert.AlertType.CONFIRMATION,response,response);
        }
        else{
            showMessage(Alert.AlertType.WARNING,"Sunt deja 3 rezervari acceptate","3 rezervari acceptate");
        }


    }

    @FXML
    public void onActionButtonAnulareCerereDonatie(){
        Integer id = donationScheduleService.deleteReservation(LoginService.getIdU());
        if(id==1)
        {
            showMessage(Alert.AlertType.CONFIRMATION,"Succes","Succes");
            statusRezervareText.setText("");
            statusRezervare.setDisable(true);
        }
        else{
            showMessage(Alert.AlertType.WARNING,"A avut loc o eroare","A avut loc o eroare");
        }
    }

    private void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    public class CustomDonationScheduleDeserialize implements JsonDeserializer<DonationSchedule> {
        @Override
        public DonationSchedule deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            JsonObject jobject = jsonElement.getAsJsonObject();
            String text = jobject.get("donationdatetime").getAsString();
            return new DonationSchedule(
                    jobject.get("idds").getAsInt(),
                    jobject.get("iddc").getAsInt(),
                    Timestamp.from(Instant.parse(text)),
                    jobject.get("availablespots").getAsInt()
            );
        }
    }

}