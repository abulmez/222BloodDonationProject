package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.BloodDemandService;

public class FormControllerModifica {
    private Integer idBd;

    @FXML
    public TextField neededType;
    @FXML
    public TextField description;
    @FXML
    public TextField priority;
    @FXML
    public TextField quantity;
    private BloodDemandService service;
    private Stage editStage;
    public void setService(Integer id, BloodDemandService service){
        this.idBd=id;
        this.service=service;
    }

    public void updateButtonHandler(){
        if(neededType.getText().equals("")||description.getText().equals("")||priority.getText().equals("")||quantity.getText().equals("")){

            showErrorMessage("Completati toate fieldurile");


        }
        else
        {
            service.handleModificare(idBd,neededType.getText(),description.getText(),priority.getText(),Integer.parseInt(quantity.getText()));
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
