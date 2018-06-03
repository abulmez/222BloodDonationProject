package viewController;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import model.*;
import org.springframework.context.ApplicationContext;
import service.DonorService;
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

import static java.time.temporal.ChronoUnit.DAYS;

public class DonationAppointmentController {
    Boolean alreadyExist = false;
    Integer howMuchDonationReports =0;
    Integer sizeOfSuffersOf = 0;
    Donor currentUser;
    String sexUser;
    DonationReport lastDonationReport = new DonationReport(null,LocalDate.now(),null,null);
    DonationSchedule selected = new DonationSchedule();
    Long nrOfWeeksLastDonationReport = Long.valueOf(0) ;
    Boolean lastDonationReportIsOk = true;
    @FXML
    DatePicker datePickerDataDonation;

    @FXML
    TableView<DonationSchedule> tableViewOreDisponibile;

    @FXML
    TableColumn<DonationSchedule,String> tableColumnOreLibere;

    @FXML
    TableColumn<DonationSchedule,String> tableColumnLocuriDisponibile;

    @FXML
    TableColumn<DonationSchedule,String> tableColumnAdresa;

    @FXML
    Button buttonTrimiteCerereDonatie;

    @FXML
    Button buttonAnulareCerereDonatie;

    @FXML
    Label statusRezervare;

    @FXML
    Label statusRezervareText;

    @FXML
    Label labelError;
    private String labelText;
    private DonorService donorService;
    private ObservableList<DonationSchedule> modelSchedule = FXCollections.observableArrayList();
    private void nrOfWeeks(LocalDate data){
        LocalDate now = LocalDate.now();
        Long daysBetween = DAYS.between(data,now);
        System.out.println(daysBetween + " Days");
        System.out.println(daysBetween/7 + " Weeks");
        nrOfWeeksLastDonationReport = daysBetween/7;
        if(sexUser.equals("M")) {
            if (nrOfWeeksLastDonationReport < 6){
                lastDonationReportIsOk = false;
                labelText = labelText + " - ati donat in urma cu " + nrOfWeeksLastDonationReport +" saptamani , mai puteti dona in " + (6-nrOfWeeksLastDonationReport) + " saptamani\n";
                labelError.setText(labelText);
            }
        }
        else {
            if(nrOfWeeksLastDonationReport < 8){
                lastDonationReportIsOk = false;
                labelText = labelText + " - ati donat in urma cu " + nrOfWeeksLastDonationReport +" saptamani , mai puteti dona in " + (8-nrOfWeeksLastDonationReport) + " satamani \n";
                labelError.setText(labelText);
            }
        }
    }
    public void setVariablesAndUtilities(){
        labelText = labelError.getText();
        ApplicationContext context = CommonUtils.getFactory();
        donorService = context.getBean(DonorService.class);
        List<DonationReport> donationReports = donorService.getAllDonationReportsByIdU(LoginService.getIdU());
        if(donationReports.size()!=0){
            lastDonationReport = donationReports.get(0);
        }

        for (DonationReport donationReport : donationReports) {
            howMuchDonationReports++;
        }
        if(howMuchDonationReports==3)
        {
            labelText = labelText + " - ati donat deja de 3 ori \n";
            labelError.setText(labelText);
        }
        List<Reservation> reservations = donorService.getAllReservation();
        currentUser = donorService.getUserByIdU(LoginService.getIdU());
        for (Reservation reservation : reservations) {
            if(reservation.getIdU() == LoginService.getIdU())
            {
//                if(reservation.getStatus().equals("ACCEPTED"))
//                    howMuchDonationReports++;
                statusRezervare.setDisable(false);
                statusRezervareText.setDisable(false);
                statusRezervareText.setText(reservation.getStatus());
                alreadyExist=true;
                labelText  = labelText + " - deja exista o rezervare al acestui user \n";
                labelError.setText(labelText);
            }
        }

    }

    public void initThings(){
        buttonTrimiteCerereDonatie.setDisable(true);
        buttonAnulareCerereDonatie.setDisable(true);
        statusRezervare.setDisable(true);
        statusRezervareText.setDisable(true);
    }
    @FXML
    public void initialize(){
        setVariablesAndUtilities();
        char[] cnp = currentUser.getCnp().toCharArray();
        if(cnp[0]=='1')
            sexUser="M";
        else
            sexUser="F";
        /*
        Calculate nr of weeks between now and last donation report
         */
        nrOfWeeks(lastDonationReport.getDataProba());

        initThings();

        datePickerDataDonation.setValue(LocalDate.now());
        datePickerDataDonation.valueProperty().addListener((ov,oldValue,newValue)->{
            buttonTrimiteCerereDonatie.setDisable(true);
            initTableSchedule(newValue);
        });


        initTableSchedule(LocalDate.now());

        List<SuffersOf> list =donorService.getSuffersOfByIdU(LoginService.getIdU());
        if(list.size()!=0){
            sizeOfSuffersOf = list.size();
            buttonTrimiteCerereDonatie.setDisable(true);
            labelText = labelText += " - nu respecti criteriile de sanatate \n";
            labelError.setText(labelText);
        }
        if(sizeOfSuffersOf==0 && howMuchDonationReports ==0 && alreadyExist == false)
        {
            labelError.setText("");
        }
    }

    void initTableSchedule(LocalDate localDate){
        tableColumnOreLibere.setCellValueFactory(new PropertyValueFactory<DonationSchedule,String>("Ora"));
        tableColumnLocuriDisponibile.setCellValueFactory(new PropertyValueFactory<DonationSchedule,String >("AvailableSpots"));
        tableColumnAdresa.setCellValueFactory(new PropertyValueFactory<DonationSchedule,String>("Adresa"));
        setModel(initList(localDate));
    }
    public List<DonationSchedule> initList(LocalDate localDate){
        List<DonationSchedule> fromRequest = donorService.getAllDonationSchedule();
        List<DonationSchedule> auxList = new ArrayList<>();
        for (DonationSchedule donationScheduleRares : fromRequest) {
            if (donationScheduleRares.getAn() == localDate.getYear())
            {
                if(donationScheduleRares.getLuna() == localDate.getMonthValue())
                    if(donationScheduleRares.getZi() == localDate.getDayOfMonth()){
                    DonationSchedule aux = donationScheduleRares;
                    aux.setAvailableSpots(donorService.settingAvailableSpots(aux.getIdDS()));
                    aux.setAdresa(donorService.getAdressByIdDc(aux.getIdDC()).getFullAdress());
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
                if(alreadyExist==false)
                buttonTrimiteCerereDonatie.setDisable(false);
                if(howMuchDonationReports >2)
                    buttonTrimiteCerereDonatie.setDisable(true);
                if(sizeOfSuffersOf!=0)
                    buttonTrimiteCerereDonatie.setDisable(true);
                if(lastDonationReportIsOk == false)
                    buttonTrimiteCerereDonatie.setDisable(true);
            });
        }
        else{
            this.modelSchedule.setAll(list);
            tableViewOreDisponibile.setItems(this.modelSchedule);
            tableViewOreDisponibile.getSelectionModel().selectedIndexProperty().addListener((obs,old,newS)->{
                if(alreadyExist==false)
                buttonTrimiteCerereDonatie.setDisable(false);
                if(howMuchDonationReports >2)
                    buttonTrimiteCerereDonatie.setDisable(true);
                if(sizeOfSuffersOf!=0)
                    buttonTrimiteCerereDonatie.setDisable(true);
                selected = tableViewOreDisponibile.getSelectionModel().getSelectedItem();
                if(lastDonationReportIsOk == false)
                    buttonTrimiteCerereDonatie.setDisable(true);
            });
        }
    }
    public void update(List<DonationSchedule> list){
        setModel(list);
    }

    @FXML
    public void onActionButtonTrimiteCerereDonatie(){
        if(howMuchDonationReports<3)
        {
            String response = donorService.addReservation(selected.getIdDS(),LoginService.getIdU());
            if(response.equals("Success")) {
                statusRezervareText.setText("ATTEMPTING");
            buttonAnulareCerereDonatie.setDisable(false);
            }
            showMessage(Alert.AlertType.CONFIRMATION,response,response);
        }
        else{
            showMessage(Alert.AlertType.WARNING,"Sunt deja 3 rezervari acceptate","3 rezervari acceptate");
        }


    }

    @FXML
    public void onActionButtonAnulareCerereDonatie(){
        Integer id = donorService.deleteReservation(LoginService.getIdU());
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