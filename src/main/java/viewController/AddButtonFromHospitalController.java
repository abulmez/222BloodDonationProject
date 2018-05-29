package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Adress;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import service.HospitalService;
import utils.CommonUtils;
import utils.ServerConnection;

import java.util.List;

public class AddButtonFromHospitalController {

    ApplicationContext context = CommonUtils.getFactory();
    ServerConnection serverConnection;
    HospitalService hospitalService;
    CenterInfoService centerInfoService;
    @FXML
    TextField nameCentre;

    @FXML
    TextField phoneCentre;

    @FXML
    TextField idACentre;

    @FXML
    Button addButton;

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
    public void initialize(){
        serverConnection = context.getBean(ServerConnection.class);
        hospitalService = new HospitalService(serverConnection);
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
        }
        int response =hospitalService.addHospital(idA,centerName,phoneNumber);
        if(response == 0){

        }
        else{
            nameCentre.setText("");
            phoneCentre.setText("");
        }
    }

}
