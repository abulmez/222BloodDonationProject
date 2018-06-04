package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Adress;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;
import utils.ServerConnection;

import java.util.List;

public class AddButtonFromHospitalController {

    ApplicationContext context = CommonUtils.getFactory();
    ServerConnection serverConnection;
    AdminService adminService;
    DonationsCentreAndHospitalsController ctrl;
    Stage thisStage;
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
    public void initialize(){
        serverConnection = context.getBean(ServerConnection.class);
        adminService = new AdminService(serverConnection);
    }
    public void setCtrl(DonationsCentreAndHospitalsController ctrl,Stage stage){
        this.ctrl = ctrl;
        thisStage = stage;
    }
    public void addHandle(){
        adminService.addAdress(streetText.getText(), nrStreetText.getText(),null,null,null,null, cityText.getText(), countyText.getText(), countryText.getText());
        String phoneNumber = phoneCentre.getText();
        String centerName = nameCentre.getText();
        List<Adress> adressList = adminService.getAllAdress();
        Adress a = new Adress(0,streetText.getText(),Integer.parseInt(nrStreetText.getText()),null,null,null,null,cityText.getText(),countyText.getText(),countryText.getText());
        Integer idA = 1;
        for (Adress adress : adressList) {
            if(adress.getFullAdress().equals(a.getFullAdress()))
                idA=adress.getIdA();
        }
        int response =adminService.addHospital(idA,centerName,phoneNumber);
        if(response == 0){

        }
        else{
            nameCentre.setText("");
            phoneCentre.setText("");
            ctrl.displayTableHospital();
            thisStage.close();
        }
    }

}
