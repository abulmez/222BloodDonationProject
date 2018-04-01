import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viewController.*;

import java.io.IOException;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        openLogin(primaryStage);
        /*
        StackPane root = new StackPane();


        initInitalPanel(root);


        primaryStage.setScene(new Scene(root, 800, 250));
        primaryStage.show();
        */
    }

    private void openLogin(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController controller = loader.getController();
        ;
        controller.setMainStage(primaryStage);
        makePaneMoveble(root,primaryStage);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root, 624, 375);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
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

    private void initInitalPanel(StackPane root) {
        Button userInfoButton = new Button("Informatii utilizator");
        Button donationAppointmentButton = new Button("Programare donatie");
        Button donationsHistoryButton = new Button("Istoric donatii");
        Button bloodRequestsButton = new Button("Cereri sange");
        Button donationsButton = new Button("Donatii");
        Button donationsAppointmentAdminButton = new Button("Programari donatii PCT");


        HBox hbox = new HBox();
        hbox.getChildren().add(userInfoButton);
        hbox.getChildren().add(donationAppointmentButton);
        hbox.getChildren().add(donationsHistoryButton);
        hbox.getChildren().add(bloodRequestsButton);
        hbox.getChildren().add(donationsButton);
        hbox.getChildren().add(donationsAppointmentAdminButton);
        root.getChildren().add(hbox);

        userInfo(userInfoButton);

        donationAppointment(donationAppointmentButton);

        donationHistory(donationsHistoryButton);

        bloodRequests(bloodRequestsButton);

        donations(donationsButton);

        donationsAppointment(donationsAppointmentAdminButton);
    }

    private void donationsAppointment(Button donationsAppointmentAdminButton) {
        donationsAppointmentAdminButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DonationsAppointmentsAdminController.class.getResource("/donationsAppointmentsAdmin.fxml"));
            AnchorPane root16 = null;
            try {
                root16 = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root16);
            dialogStage.setScene(scene);
            dialogStage.show();
        });
    }

    private void donations(Button donationsButton) {
        donationsButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DonationsController.class.getResource("/donations.fxml"));
            AnchorPane root15 = null;
            try {
                root15 = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root15);
            dialogStage.setScene(scene);
            dialogStage.show();
        });
    }

    private void bloodRequests(Button bloodRequestsButton) {
        bloodRequestsButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BloodRequestsController.class.getResource("/viewController/bloodRequests.fxml"));
            AnchorPane root14 = null;
            try {
                root14 = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root14);
            dialogStage.setScene(scene);
            dialogStage.show();
        });
    }

    private void donationHistory(Button donationsHistoryButton) {
        donationsHistoryButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DonationsHistoryController.class.getResource("/viewController/donationsHistory.fxml"));
            AnchorPane root13 = null;
            try {
                root13 = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root13);
            dialogStage.setScene(scene);
            dialogStage.show();
        });
    }

    private void donationAppointment(Button donationAppointmentButton) {
        donationAppointmentButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DonationAppointmentController.class.getResource("/viewController/donationAppointment.fxml"));
            AnchorPane root12 = null;
            try {
                root12 = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root12);
            dialogStage.setScene(scene);
            dialogStage.show();
        });
    }

    private void userInfo(Button userInfoButton) {
        userInfoButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UserInfoController.class.getResource("/userInfo.fxml"));
            AnchorPane root1 = null;
            try {
                root1 = (AnchorPane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root1);
            dialogStage.setScene(scene);
            dialogStage.show();
        });
    }
}