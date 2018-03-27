package viewController;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MenuMedic {
    private AnchorPane centerManeuPane;
    private MenuController mainStage;

    public void setMainPane(AnchorPane centerMenuPane) {
        this.centerManeuPane=centerMenuPane;
        setPanel(getClass().getResource("/viewController/bloodRequests.fxml"));
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
        setPanel(getClass().getResource("/viewController/Form.fxml"));
    }

    private void setPanel(URL resource) {
        centerManeuPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane = null;
        loader.setLocation(resource);
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        centerManeuPane.getChildren().add(mainPane);
    }

    @FXML
    void visualizeAction(MouseEvent event) {
        setPanel(getClass().getResource("/viewController/bloodRequests.fxml"));
    }

    public void setStage(MenuController stage) {
        this.mainStage = stage;
    }
}
