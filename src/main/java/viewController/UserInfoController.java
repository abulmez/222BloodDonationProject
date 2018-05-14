package viewController;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.BreadCrumbBar;

import java.io.IOException;

public class UserInfoController {
    @FXML
    private Label data;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label data22;

    @FXML
    private Label bloodGroupLabel;


    @FXML
    private Label cnpLabel;

    @FXML
    private Label data1;

    @FXML
    private Label nameLabel;

    @FXML
    private Label data21;

    @FXML
    private Label birtdayLabel;

    @FXML
    private TextField weightText;

    @FXML
    private TextField phoneText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField countryText;

    @FXML
    private TextField countyText;

    @FXML
    private TextField cityText;

    @FXML
    private TextField streetText;

    @FXML
    private TextField nrStreetText;

    @FXML
    private TextField blockText;

    @FXML
    private TextField stairText;

    @FXML
    private TextField floorText;

    @FXML
    private TextField flatText;

    @FXML
    private Button diseaseButton;

    @FXML
    private TextArea supplyTextArea;

    @FXML
    private Button applayChanges;

    @FXML
    private void initialize(){


    }

    @FXML
    public void handleDiseases(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserInfoController.class.getResource("/viewController/boli.fxml"));
        AnchorPane root1 = null;
        try {
            root1 = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root1);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

 /*
    @FXML
    public void handleAdaugaDate(){
        if (fieldUsername.getText().equals("") || fieldCNP.getText().equals("") || fieldNume.getText().equals("") || fieldData.equals("") || fieldSange.equals("") || fieldGreutate.equals("") || fieldTelefon.equals("") || fieldMail.equals(""))
            ErrorMessage.showErrorMessage(null,"Trebuie sa completati toate campurile");
    }
    */
}
