package clientStart;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Donation;
import viewController.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginCreatorController loginCreatorController = new LoginCreatorController(primaryStage);
        loginCreatorController.show();
    }
}