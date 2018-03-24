package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DonationAppointmentController {
    @FXML
    DatePicker datePickerDataDonation;

    @FXML
    TableView tableViewOreDisponibile;

    @FXML
    TableColumn tableColumnOreLibere;

    @FXML
    TableColumn tableColumnLocuriDisponibile;

    @FXML
    TableView tableViewDonatii;

    @FXML
    TableColumn tableColumnDonator;

    @FXML
    TableColumn tableColumnDataProgramareDonator;

    @FXML
    TableColumn tableColumnStatusCerereDonatie;

    @FXML
    Button buttonTrimiteCerereDonatie;

    @FXML
    Button buttonAnulareCerereDonatie;

    @FXML
    public void initialize(){

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


}
