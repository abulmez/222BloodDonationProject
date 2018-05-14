package viewController;

import errorMessage.ErrorMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import service.LoginService;
import utils.CommonUtils;
import utils.ServerConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class addButtonFormCentreController {
    ApplicationContext context = CommonUtils.getFactory();
    ServerConnection serverConnection;
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
    public void initialize(){
        serverConnection = context.getBean(ServerConnection.class);
        centerInfoService = new CenterInfoService(serverConnection);
    }

    public void addHandle(){
            Integer idA = Integer.parseInt(idACentre.getText());
            String phoneNumber = phoneCentre.getText();
            String centerName = nameCentre.getText();
            int response =centerInfoService.addCenter(idA,phoneNumber,centerName);
            if(response == 0){

            }
            else{
                nameCentre.setText("");
                phoneCentre.setText("");
                idACentre.setText("");
            }
    }
}
