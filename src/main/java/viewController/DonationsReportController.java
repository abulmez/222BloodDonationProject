package viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import org.springframework.context.ApplicationContext;
import service.DonationsReportService;
import utils.CommonUtils;

public class DonationsReportController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea textArea;

    private DonationsReportService service;
    ApplicationContext context = CommonUtils.getFactory();


    public void initialize(){
        service = context.getBean(DonationsReportService.class);

    }
/*
    public void handleAdd(ActionEvent event){
        String errors="";
        if (idDonatieTextField.getText().isEmpty()){
            errors+="Dati id-ul donatiei!\n";
        }
        if (numeUserTextField.getText().isEmpty()){
            errors+="Dati numele donatorului!\n";
        }
        if (idUserTextField.getText().isEmpty()){
            errors+="Dati id-ul donatorului!\n";
        }
        if(cantitateTextField.getText().isEmpty()){
            errors+="Dati cantitatea probei de sange!\n";
        }
        if (errors.equals("")){
            DonationDTO donation=new DonationDTO(Integer.parseInt(idDonatieTextField.getText()),numeUserTextField.getText(),
                    Integer.parseInt(idUserTextField.getText()),"In curs de validare",Double.parseDouble(cantitateTextField.getText()));
            String response=service.handleAdd(donation);
            if (!response.equals("Success")){
                Alert alert=new Alert(Alert.AlertType.ERROR,response);
                alert.show();
            }
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR,errors);
            alert.show();
        }
    }
    */
}
