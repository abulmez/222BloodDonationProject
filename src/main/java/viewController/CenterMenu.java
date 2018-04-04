package viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class CenterMenu{
    public AnchorPane centerManeuPane;
    public MenuController mainStage;

    public void exitAction(){
        mainStage.exitAction();
        LoginCreator loginCreator = new LoginCreator(new Stage());
        loginCreator.show();
    }
    public void setStage(MenuController stage) {
        this.mainStage = stage;
    }

    public void setMainPane(AnchorPane centerMenuPane) {
        centerManeuPane=centerMenuPane;
    }
    public static void setPanel(URL resource, AnchorPane centerManeuPane) {
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
}
