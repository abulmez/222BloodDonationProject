package viewController;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuMedic {
    private AnchorPane centerManeuPane;
    private Stage mainStage;

    public void setMainPane(AnchorPane centerMenuPane) {
        this.centerManeuPane=centerMenuPane;

    }
    @FXML
    public void initialize(){

    }
    @FXML
    void logoutAction(MouseEvent event) {
        mainStage.close();
    }

    @FXML
    void requestAction(MouseEvent event) {
        centerManeuPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane = null;
        loader.setLocation(getClass().getResource("/viewController/Form.fxml"));
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        centerManeuPane.getChildren().add(mainPane);
    }

    @FXML
    void visualizeAction(MouseEvent event) {
        centerManeuPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane = null;
        loader.setLocation(getClass().getResource("/viewController/bloodRequests.fxml"));
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        centerManeuPane.getChildren().add(mainPane);
    }

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }
}
