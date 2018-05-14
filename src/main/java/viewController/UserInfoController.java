package viewController;

import errorMessage.ErrorMessage;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.BreadCrumbBar;
import org.springframework.context.ApplicationContext;
import service.DonorService;
import service.LoginService;
import utils.CommonUtils;

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

    private DonorService service;
    int id=LoginService.getIdU();

    @FXML
    private void initialize(){
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(DonorService.class);
        String response=service.handleFields(Integer.toString(id));
        System.out.println(2);
        String[] data = response.split("&");
        cnpLabel.setText(data[0].split("=")[1]);
        nameLabel.setText(data[1].split("=")[1]);
        birtdayLabel.setText(data[2].split("=")[1]);
        bloodGroupLabel.setText(data[3].split("=")[1]);
        usernameLabel.setText(data[4].split("=")[1]);
        weightText.setText(data[5].split("=")[1]);
        phoneText.setText(data[6].split("=")[1]);
        emailText.setText(data[7].split("=")[1]);
        if (data[8].split("=")[1].equals("1")){
            streetText.setText(data[9].split("=")[1]);
            nrStreetText.setText(data[10].split("=")[1]);
            blockText.setText(data[11].split("=")[1]);
            stairText.setText(data[12].split("=")[1]);
            floorText.setText(data[13].split("=")[1]);
            flatText.setText(data[14].split("=")[1]);
            cityText.setText(data[15].split("=")[1]);
            countyText.setText(data[16].split("=")[1]);
            countryText.setText(data[17].split("=")[1]);
            if(data[18].split("=")[1].equals("2"))
                supplyTextArea.setText(data[19].split("=")[1]);
        }


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
        DiseaseController diseaseCtr=loader.getController();
        Stage dialogStage = new Stage();
        diseaseCtr.currentStage(dialogStage);
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
    @FXML
    public void handleAdd(){

        if (streetText.getText().equals("") || nrStreetText.getText().equals("") || blockText.getText().equals("") || stairText.getText().equals("") || floorText.getText().equals("") || flatText.getText().equals("") || cityText.getText().equals("") || countyText.getText().equals("") || weightText.getText().equals("") || phoneText.getText().equals("") || emailText.getText().equals(""))
            ErrorMessage.showErrorMessage(null,"Trebuie sa completati toate campurile");
        else {
            service.handleAdress(streetText.getText(), nrStreetText.getText(), blockText.getText(), stairText.getText(), floorText.getText(), flatText.getText(), cityText.getText(), countyText.getText(), countryText.getText(), Integer.toString(id));
            service.handleUserUpdate(Integer.toString(id), weightText.getText(), phoneText.getText(), emailText.getText());
            if (supplyTextArea.getText().equals("")){
            }
            else {
                service.handleAdditional(supplyTextArea.getText(), Integer.toString(id));
            }
            ErrorMessage.showMessage(null, Alert.AlertType.INFORMATION,"Succes","Schimbarile au fost efectuate cu succes");
        }
    }
}