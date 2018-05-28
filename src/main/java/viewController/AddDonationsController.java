package viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ApplicationContext;
import service.TCPService;
import utils.CommonUtils;
import model.dto.IdentifierDTO;

import java.util.List;

public class AddDonationsController{

    @FXML
    private TextField numeUserTextField;

    @FXML
    private TextField cnpUserTextField;

    @FXML
    private TextField cantitateTextField;

    @FXML
    private TextField receiverTextField;

    private TCPService service;
    ApplicationContext context = CommonUtils.getFactory();
    public void initialize(){
        service = context.getBean(TCPService.class);
        List<IdentifierDTO> identifiers=service.getNamesCNP(service.handleGetDonors());
        AutoCompletionBinding<IdentifierDTO> autoCompletionBinding=TextFields.bindAutoCompletion(numeUserTextField,identifiers);
        autoCompletionBinding.setPrefWidth(159.2);
        autoCompletionBinding.setOnAutoCompleted(
                (AutoCompletionBinding.AutoCompletionEvent<IdentifierDTO> autoCompletionEvent) -> {
                    IdentifierDTO identifier=autoCompletionEvent.getCompletion();
                    cnpUserTextField.setText(identifier.getCnp());
                    numeUserTextField.setText(identifier.getName());
                });

    }

    @FXML
    public void handleAdd(ActionEvent event){

        String errors="";
        if (numeUserTextField.getText().isEmpty()){
            errors+="Dati numele donatorului!\n";
        }
        if (cnpUserTextField.getText().isEmpty()){
            errors+="Dati id-ul donatorului!\n";
        }
        if(cantitateTextField.getText().isEmpty()){
            errors+="Dati cantitatea probei de sange!\n";
        }
        try {
            if (Double.parseDouble(cantitateTextField.getText()) > 500) {
                errors += "Cantitatea de sange nu trebuie sa depaseasca 500 ml!\n";
            }
        }catch(NumberFormatException exception){
            errors+="Cantitatea trebuie sa fie un numar real!";
        }
        if (errors.equals("")){
            String cnp=cnpUserTextField.getText();
            String status="In curs de validare";
            String quantity=cantitateTextField.getText();
            String receiver=receiverTextField.getText();
            String response=service.handleAddDonation(cnp,status,quantity,receiver);
            if (!response.equals("Success")){
                Alert alert=new Alert(Alert.AlertType.ERROR,response);
                alert.showAndWait();
            }
            Stage stage = (Stage) numeUserTextField.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR,errors);
            alert.showAndWait();
        }
    }

}