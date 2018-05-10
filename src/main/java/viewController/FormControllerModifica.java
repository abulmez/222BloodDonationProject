package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.BloodRequest;
import service.BloodDemandService;
import service.LoginService;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class FormControllerModifica {
    private Integer idBd;
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
    public TextField bloodType;
    private BloodDemandService service;
    private Stage editStage;
    @FXML
    public Button modifyButton;
    public void setService(Integer id, BloodDemandService service,String needTyp,String desc,String prior,Double quan,String BT){
        this.idBd=id;
        this.service=service;
        this.neededType.setText(needTyp);
        this.description.setText(desc);
        this.priority.setText(prior);
        this.quantity.setText(quan.toString());
        this.bloodType.setText(BT);
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

    public void updateButtonHandler(){
        try {
            Double.parseDouble(quantity.getText());
        if(neededType.getText().equals("")||description.getText().equals("")||priority.getText().equals("")||quantity.getText().equals("")||bloodType.getText().equals("")){

            showErrorMessage("Completati toate fieldurile");


        }
        else
        {
            service.handleModificare(idBd,LoginService.getIdU(),neededType.getText(),description.getText(),priority.getText(),Double.parseDouble(quantity.getText()),bloodType.getText());
            Stage stage = (Stage)bloodType.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
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
