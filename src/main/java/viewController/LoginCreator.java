package viewController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginCreator{
    private double xOffset = 0;
    private double yOffset = 0;
    private Stage primaryStage;
    public LoginCreator(Stage primaryStage){
        this.primaryStage=primaryStage;
        openLogin(primaryStage);
    }

    private void openLogin(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewController/login.fxml"));
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController controller = loader.getController();
        controller.setMainStage(primaryStage);
        makePaneMoveble(root,primaryStage);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root, 877, 557);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);

    }
    public void show(){
        primaryStage.show();
    }


    private void makePaneMoveble(AnchorPane rootLayout, Stage window){
        rootLayout.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        rootLayout.setOnMouseDragged(event -> {
            window.setX(event.getScreenX() - xOffset);
            window.setY(event.getScreenY() - yOffset);
        });
    }

}
