package viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import service.AddDonationService;
import utils.CommonUtils;

public class AddDonationsController{

    @FXML
    private TextField numeUserTextField;

    @FXML
    private TextField idUserTextField;

    @FXML
    private TextField cantitateTextField;

    private AddDonationService service;
    ApplicationContext context = CommonUtils.getFactory();
    public void initialize(){
        service = context.getBean(AddDonationService.class);

    }

    @FXML
    public void handleAdd(ActionEvent event){
        String errors="";
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
            String name=numeUserTextField.getText();
            String idU=idUserTextField.getText();
            String status="In curs de validare";
            String quantity=cantitateTextField.getText();
            String response=service.handleAdd(name,idU,status,quantity);
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
}