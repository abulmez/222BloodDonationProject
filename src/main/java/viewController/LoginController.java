package viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.UserType;
import service.LoginService;
import viewController.MedicController;
import viewController.MenuController;


import java.io.IOException;

public class LoginController {

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordPswField;

    @FXML
    Label failedLoginLabel;

    private Stage stage = new Stage();
    private double xOffset=0;
    private double yOffset=0;
    private Stage mainStage;
    private LoginService service;

    public void initialize(){
        service = new LoginService();
    }

    public void setMainStage(Stage stage){
        this.mainStage=stage;
    }
    @FXML
    void validateAction(MouseEvent event) {
        if(service.handleLogin(usernameTextField.getText(),passwordPswField.getText())) {

            openMenu(service.getUserType());
        }
        else{
            failedLoginLabel.setVisible(true);
        }

    }

    private void openMenu(UserType userType) {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane ;
        loader.setLocation(getClass().getResource("/viewController/menu.fxml"));
        try {

            mainPane = loader.load();
            Scene scene=new Scene(mainPane,1023,604);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            makePaneMoveble(mainPane,stage);
            MenuController mainPanel=loader.getController();
            mainPanel.setMainStage(stage);
            mainPanel.setUserType(userType);
            stage.show();
            mainStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    public void exitAction(MouseEvent event){
        mainStage.close();
    }

}
