package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Adress;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;
import utils.ServerConnection;

import java.util.List;


public class AddButtonFormCentreController {
    ApplicationContext context = CommonUtils.getFactory();
    ServerConnection serverConnection;
    AdminService adminService;

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
        adminService = new AdminService(serverConnection);
    }

    public void addHandle(){
        adminService.addAdress(streetText.getText(), nrStreetText.getText(), blockText.getText(), stairText.getText(), floorText.getText(), flatText.getText(), cityText.getText(), countyText.getText(), countryText.getText());
            String phoneNumber = phoneCentre.getText();
            String centerName = nameCentre.getText();
            List<Adress> adressList = adminService.getAllAdress();
            Adress a = new Adress(0,streetText.getText(),Integer.parseInt(nrStreetText.getText()),Integer.parseInt(blockText.getText()),stairText.getText(),Integer.parseInt(floorText.getText()),Integer.parseInt(flatText.getText()),cityText.getText(),countyText.getText(),countryText.getText());
            Integer idA = 1;
            for (Adress adress : adressList) {
                if(adress.getFullAdress().equals(a.getFullAdress()))
                    idA=adress.getIdA();
                System.out.println(idA);
            }
            int response =adminService.addCenter(idA,phoneNumber,centerName);
            if(response == 0){

            }
            else{
                nameCentre.setText("");
                phoneCentre.setText("");
            }
    }
}
