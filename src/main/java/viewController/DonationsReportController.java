package viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.DonationReport;
import org.springframework.context.ApplicationContext;
import service.DonationsReportService;
import service.TCPService;
import utils.CommonUtils;

import java.time.LocalDate;

public class DonationsReportController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea textArea;

    private TCPService service;
    ApplicationContext context = CommonUtils.getFactory();
    private String status;
    private Integer idDR;


    public void initialize(){
        service = context.getBean(TCPService.class);
    }

    public void initData(String status,Integer id){
        this.status=status;
        this.idDR=id;
    }

    public void handleAdd(ActionEvent event){
        String errors="";
        if (datePicker.getValue()==null){
            errors+="Selectati data!\n";
        }
        if (textArea.getText().isEmpty()){
            errors+="Nu observati nimic?\n";
        }
        if (errors.equals("")){
            Boolean validitate;
            if (status.equals("In curs de validare") || status.equals("Proba invalida"))
                validitate=false;
            else
                validitate=true;
            DonationReport report=new DonationReport(idDR,datePicker.getValue(),validitate,textArea.getText());
            String response=service.handleAddDonationReport(report);
            if (!response.equals("Success")){
                Alert alert=new Alert(Alert.AlertType.ERROR,response);
                alert.showAndWait();
            }
            else{
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"Raportul a fost adaugat cu succes!");
                alert.showAndWait();
            }
            Stage thisStage=(Stage)textArea.getScene().getWindow();
            thisStage.close();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR,errors);
            alert.showAndWait();
        }

    }

}
