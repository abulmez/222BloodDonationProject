package viewController;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static viewController.Utilitys.*;

public class MenuMedic {
    private AnchorPane centerManeuPane;
    private MenuController mainStage;

    public void setMainPane(AnchorPane centerMenuPane) {
        this.centerManeuPane=centerMenuPane;
        Utilitys.setPanel(getClass().getResource("/viewController/bloodRequests.fxml"),centerManeuPane);
    }
    @FXML
    public void initialize(){

    }
    @FXML
    void logoutAction(MouseEvent event) {
        mainStage.exitAction();
    }

    @FXML
    void requestAction(MouseEvent event) {
        Utilitys.setPanel(getClass().getResource("/viewController/Form.fxml"),centerManeuPane);
    }


    @FXML
    void visualizeAction(MouseEvent event) {
        Utilitys.setPanel(getClass().getResource("/viewController/bloodRequests.fxml"), centerManeuPane);
    }

    public void setStage(MenuController stage) {
        this.mainStage = stage;
    }
}
