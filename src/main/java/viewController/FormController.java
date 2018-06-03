package viewController;

import errorMessage.ErrorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.MedicService;
import service.LoginService;

public class FormController {

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
    public ComboBox bloodDemandType;
    private MedicService service;
    private Stage editStage;
    public void setService( MedicService service){
        this.service=service;
        //blockQuantity();
        ObservableList<String> listCombo1 = FXCollections.observableArrayList("Mare","Medie","Mica");
        priority.setItems(listCombo1);
        priority.setVisibleRowCount(2);

        ObservableList<String> listCombo2 = FXCollections.observableArrayList("Trombocite","Globule Rosii","Plasma","Sange");
        bloodDemandType.setItems(listCombo2);
        bloodDemandType.setVisibleRowCount(2);

        ObservableList<String> listCombo3 = FXCollections.observableArrayList("O+","O-","AB+","AB-","A-","A+","B-","B+");
        neededType.setItems(listCombo3);
        neededType.setVisibleRowCount(2);
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


        if(neededType.getSelectionModel().getSelectedItem()==null||
                description.getText().equals("")||priority.getSelectionModel().getSelectedItem()==null||quantity.getText().equals("") ||
                bloodDemandType.getSelectionModel().getSelectedItem()==null){

            showErrorMessage("Completati/selectati toate datele formularului");


        }
        else
        {
            try {
                Double.parseDouble(quantity.getText());

                String mesaj=service.handleAdd(LoginService.getIdU(),neededType.getSelectionModel().getSelectedItem().toString(),description.getText(),
                    priority.getSelectionModel().getSelectedItem().toString(),Double.parseDouble(quantity.getText()),bloodDemandType.getSelectionModel().getSelectedItem().toString());
                ErrorMessage.showMessage(editStage, Alert.AlertType.INFORMATION,"Adaugare",mesaj);
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
