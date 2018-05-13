package clientStart;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Donation;
import viewController.*;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Gson gson = new Gson();
        Donation donation = new Donation(1,1,1,14.1,"dsadsa","dsds");
        System.out.println(gson.toJson(donation));

        LoginCreator loginCreator = new LoginCreator(primaryStage);
        loginCreator.show();
    }
}