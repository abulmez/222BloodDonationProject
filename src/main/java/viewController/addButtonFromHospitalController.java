package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import service.HospitalService;
import utils.CommonUtils;
import utils.ServerConnection;

public class addButtonFromHospitalController {

    ApplicationContext context = CommonUtils.getFactory();
    ServerConnection serverConnection;
    HospitalService hospitalService;
    @FXML
    TextField nameCentre;

    @FXML
    TextField phoneCentre;

    @FXML
    TextField idACentre;

    @FXML
    Button addButton;


    @FXML
    public void initialize(){
        serverConnection = context.getBean(ServerConnection.class);
        hospitalService = new HospitalService(serverConnection);
    }

    public void addHandle(){
        Integer idA = Integer.parseInt(idACentre.getText());
        String phoneNumber = phoneCentre.getText();
        String centerName = nameCentre.getText();
        int response =hospitalService.addHospital(idA,centerName,phoneNumber);
        if(response == 0){

        }
        else{
            nameCentre.setText("");
            phoneCentre.setText("");
            idACentre.setText("");
        }
    }

}
