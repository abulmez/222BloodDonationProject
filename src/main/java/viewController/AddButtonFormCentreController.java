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
    DonationsCentreAndHospitalsController ctrl;
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

    public void setCtrl(DonationsCentreAndHospitalsController ctrl){
        this.ctrl = ctrl;
    }
    public void addHandle(){
        adminService.addAdress(streetText.getText(), nrStreetText.getText(), null, null, null, null, cityText.getText(), countyText.getText(), countryText.getText());
            String phoneNumber = phoneCentre.getText();
            String centerName = nameCentre.getText();
            List<Adress> adressList = adminService.getAllAdress();
            Adress a = new Adress(0,streetText.getText(),Integer.parseInt(nrStreetText.getText()),null,null,null,null,cityText.getText(),countyText.getText(),countryText.getText());
            Integer idA = 1;
        for (Adress adress : adressList) {
            if(adress.getIdA()>idA){
                idA = adress.getIdA();
            }
        }

            for (Adress adress : adressList) {
                if(adress.toStringSmall().equals(a.toStringSmall()))
                    idA=adress.getIdA();
                System.out.println(idA);
            }
            int response =adminService.addCenter(idA,phoneNumber,centerName);
            if(response == 0){

            }
            else{
                nameCentre.setText("");
                phoneCentre.setText("");
                ctrl.displayTableCentre();
            }
    }
}
