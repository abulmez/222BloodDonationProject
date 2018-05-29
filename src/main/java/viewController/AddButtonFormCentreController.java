package viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.DonationCenter;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import utils.AbstractTableController;
import utils.CommonUtils;
import utils.ServerConnection;

import java.io.IOException;


public class AddButtonFormCentreController {
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
