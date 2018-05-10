package viewController;

import errorMessage.ErrorMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import service.BloodDemandService;
import service.LoginService;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class FormController {

    @FXML
    public TextField idH;
    @FXML
    public TextField neededType;
    @FXML
    public TextField description;
    @FXML
    public TextField priority;
    @FXML
    public TextField quantity;
    @FXML
    public TextField bloodDemandType;
    private BloodDemandService service;
    private Stage editStage;
    public void setService( BloodDemandService service){
        this.service=service;
        //blockQuantity();
    }

//    public void blockQuantity(){
//        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
//        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
//            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
//        });
//
//        quantity.setTextFormatter(formatter);
//    }

    public void addButtonHandler(){
        try {
            Double.parseDouble(quantity.getText());


        if(neededType.getText().equals("")||description.getText().equals("")||priority.getText().equals("")||quantity.getText().equals("") || bloodDemandType.getText().equals("")){

            showErrorMessage("Completati toate fieldurile");


        }
        else
        {
            String mesaj=service.handleAdd(LoginService.getIdU(),neededType.getText(),description.getText(),priority.getText(),Double.parseDouble(quantity.getText()),bloodDemandType.getText());
            ErrorMessage.showMessage(editStage, Alert.AlertType.INFORMATION,"Adaugare",mesaj);
        }
        } catch (NumberFormatException ignore) {
            showErrorMessage("Introduceti un numar real in cadrul campului 'cantitate'");
        }
    }


    private void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error Message");
        message.initOwner(editStage);
        message.setContentText(text);
        message.showAndWait();
    }
}
