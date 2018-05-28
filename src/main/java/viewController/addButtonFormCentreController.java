package viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Adress;
import model.DonationCenter;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import utils.AbstractTableController;
import utils.CommonUtils;
import utils.ServerConnection;

import java.io.IOException;
import java.util.List;


public class addButtonFormCentreController {
    ApplicationContext context = CommonUtils.getFactory();
    ServerConnection serverConnection;
    CenterInfoService centerInfoService;

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
    TextField nameCentre;

    @FXML
    TextField phoneCentre;

    @FXML
    Button addButton;

    @FXML
    public void initialize(){
        serverConnection = context.getBean(ServerConnection.class);
        centerInfoService = new CenterInfoService(serverConnection);
    }

    public void addHandle(){
            centerInfoService.addAdress(streetText.getText(), nrStreetText.getText(), blockText.getText(), stairText.getText(), floorText.getText(), flatText.getText(), cityText.getText(), countyText.getText(), countryText.getText());
            String phoneNumber = phoneCentre.getText();
            String centerName = nameCentre.getText();
            List<Adress> adressList = centerInfoService.getAllAdress();
            Adress a = new Adress(0,streetText.getText(),Integer.parseInt(nrStreetText.getText()),Integer.parseInt(blockText.getText()),stairText.getText(),Integer.parseInt(floorText.getText()),Integer.parseInt(flatText.getText()),cityText.getText(),countyText.getText(),countryText.getText());
            Integer idA = 1;
            for (Adress adress : adressList) {
                if(adress.getFullAdress().equals(a.getFullAdress()))
                    idA=adress.getIdA();
                System.out.println(idA);
            }
            int response =centerInfoService.addCenter(idA,phoneNumber,centerName);
            if(response == 0){

            }
            else{
                nameCentre.setText("");
                phoneCentre.setText("");
            }
    }
}
