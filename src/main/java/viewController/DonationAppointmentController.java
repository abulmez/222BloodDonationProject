package viewController;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DonationSchedule;
import model.DonationScheduleRares;
import model.Schedule;
import org.springframework.context.ApplicationContext;
import service.DonationScheduleService;
import utils.CommonUtils;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonationAppointmentController {
    @FXML
    DatePicker datePickerDataDonation;

    @FXML
    TableView<Schedule> tableViewOreDisponibile;

    @FXML
    TableColumn<Schedule,String> tableColumnOreLibere;

    @FXML
    TableColumn<Schedule,String> tableColumnLocuriDisponibile;

    @FXML
    Button buttonTrimiteCerereDonatie;

    @FXML
    Button buttonAnulareCerereDonatie;

    private DonationScheduleService donationScheduleService;
    private ObservableList<Schedule> modelSchedule = FXCollections.observableArrayList();
    @FXML
    public void initialize(){
        datePickerDataDonation.setValue(LocalDate.now());
        ApplicationContext context = CommonUtils.getFactory();
        donationScheduleService = context.getBean(DonationScheduleService.class);
        String response = donationScheduleService.requestForTableHandle(1,1);
        System.out.println("Am primit de la request " + " " + response);
        Gson gson = new GsonBuilder().registerTypeAdapter(DonationScheduleRares.class,new CustomDonationScheduleDeserialize()).create();
        Type collectionType = new TypeToken<ArrayList<DonationScheduleRares>>(){}.getType();
        ArrayList<DonationScheduleRares> donationSchedules = gson.fromJson(response,collectionType);
        donationSchedules.forEach(donationSchedule -> System.out.println(donationSchedule));
        initTableSchedule();
    }

    void initTableSchedule(){
        tableColumnOreLibere.setCellValueFactory(new PropertyValueFactory<Schedule,String>("Ora"));
        tableColumnLocuriDisponibile.setCellValueFactory(new PropertyValueFactory<Schedule,String >("Availablespots"));
        setModel(initList());
    }
    public List<Schedule> initList(){
        int availableSpots =4;
        List<Schedule> init = new ArrayList<>();
        init.add(new Schedule("08:00",availableSpots));
        init.add(new Schedule("09:00",availableSpots));
        init.add(new Schedule("10:00",availableSpots));
        init.add(new Schedule("11:00",availableSpots));
        init.add(new Schedule("12:00",availableSpots));
        init.add(new Schedule("13:00",availableSpots));
        init.add(new Schedule("14:00",availableSpots));
        init.add(new Schedule("15:00",availableSpots));
        init.add(new Schedule("16:00",availableSpots));
        init.add(new Schedule("17:00",availableSpots));
        init.add(new Schedule("18:00",availableSpots));
        init.add(new Schedule("19:00",availableSpots));
        init.add(new Schedule("20:00",availableSpots));
        init.add(new Schedule("21:00",availableSpots));
        init.add(new Schedule("22:00",availableSpots));
        return init;
    }
    public void setModel(List<Schedule> list)
    {
        this.modelSchedule.setAll(list);
        tableViewOreDisponibile.setItems(this.modelSchedule);
    }
    public void update(List<Schedule> list){
        setModel(list);
    }

    @FXML
    public void onActionButtonTrimiteCerereDonatie(){
        showMessage(Alert.AlertType.CONFIRMATION,"Merge btonul de trimite cerere ","Salutare");
    }

    @FXML
    public void onActionButtonAnulareCerereDonatie(){
        showMessage(Alert.AlertType.CONFIRMATION,"Merge butonul de anulare cerere ","Salutare");
    }

    private void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    public class CustomDonationScheduleDeserialize implements JsonDeserializer<DonationScheduleRares> {
        @Override
        public DonationScheduleRares deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            JsonObject jobject = jsonElement.getAsJsonObject();
            String text = jobject.get("donationdatetime").getAsString();
            return new DonationScheduleRares(
                    jobject.get("idds").getAsInt(),
                    jobject.get("iddc").getAsInt(),
                    Timestamp.from(Instant.parse(text)),
                    jobject.get("availablespots").getAsInt()
            );
        }
    }

}