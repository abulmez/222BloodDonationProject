package viewController;

import errorMessage.ErrorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public ComboBox neededType;
    @FXML
    public TextField description;
    @FXML
    public ComboBox priority;
    @FXML
    public TextField quantity;
    @FXML
    public ComboBox bloodType;
    private BloodDemandService service;
    private Stage editStage;
    @FXML
    public Button modifyButton;
    public void setService(Integer id, BloodDemandService service,String needTyp,String desc,String prior,Double quan,String BT){
        this.idBd=id;
        this.service=service;

        this.description.setText(desc);

        this.quantity.setText(quan.toString());
        ObservableList<String> listCombo1 = FXCollections.observableArrayList("Mare","Medie","Mica");
        priority.setItems(listCombo1);
        priority.setVisibleRowCount(2);

        ObservableList<String> listCombo2 = FXCollections.observableArrayList("Trombocite","Globule rosii","Plasma","Sange");
        bloodType.setItems(listCombo2);
        bloodType.setVisibleRowCount(2);

        ObservableList<String> listCombo3 = FXCollections.observableArrayList("O+","O-","AB+","AB-","A-","A+","B-","B+");
        neededType.setItems(listCombo3);
        neededType.setVisibleRowCount(2);

        bloodType.getSelectionModel().select(BT);
        neededType.getSelectionModel().select(needTyp);
        priority.getSelectionModel().select(prior);
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

        if(neededType.getSelectionModel().getSelectedItem()==null||
                description.getText().equals("")||priority.getSelectionModel().getSelectedItem()==null||quantity.getText().equals("") ||
                bloodType.getSelectionModel().getSelectedItem()==null){

            showErrorMessage("Completati/selectati toate datele formuralului");


        }
        else
        {
            try {
                Double.parseDouble(quantity.getText());

                service.handleModificare(idBd,LoginService.getIdU(),neededType.getSelectionModel().getSelectedItem().toString(),description.getText(),
                        priority.getSelectionModel().getSelectedItem().toString(),Double.parseDouble(quantity.getText()),bloodType.getSelectionModel().getSelectedItem().toString());
                Stage stage = (Stage)bloodType.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            } catch (NumberFormatException ignore) {
                showErrorMessage("Introduceti un numar real in cadrul campului 'cantitate'");
            }
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
