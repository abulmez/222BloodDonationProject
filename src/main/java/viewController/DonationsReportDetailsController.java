package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Donation;
import model.DonationReport;

import java.time.LocalDate;

public class DonationsReportDetailsController {
    private DonationReport donationReport;
    @FXML
    Button buttonClose;
    @FXML
    Label labelDataProbei;

    @FXML
    Label labelValiditateaProbei;

    @FXML
    Label labelObservatii;

    @FXML
    void initialize(){
    }

    public void  setLabels(DonationReport donationReport){
        this.donationReport = donationReport;
        labelDataProbei.setText(this.donationReport.getDataProba().toString());
        labelValiditateaProbei.setText(this.donationReport.getValiditateProba().toString());
        labelObservatii.setText(this.donationReport.getObservatii());
    }
    public void handleClose(){
        Stage stage = (Stage) buttonClose.getScene().getWindow();
        stage.close();
    }
}
