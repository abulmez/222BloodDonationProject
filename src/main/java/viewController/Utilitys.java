package viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class Utilitys {
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
