package viewController;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MenuController {

    private HamburgerSlideCloseTransition transition;

    @FXML
    private JFXDrawer menuDrawer;

    @FXML
    private AnchorPane centerMenuPane;

    @FXML
    private JFXHamburger menuButton;

    private Stage mainStage;


    @FXML
    void exitAction(MouseEvent event) {
        mainStage.close();
    }

    @FXML
    void iconAction(MouseEvent event) {
        mainStage.setIconified(true);
    }

    @FXML
    void openMenu(MouseEvent event) {
        transition.setRate(transition.getRate()*-1);
        transition.play();
        if(menuDrawer.isShown()){
           menuDrawer.setDisable(true);
           menuDrawer.close();
        }
        else{
            menuDrawer.setDisable(false);
            menuDrawer.open();
        }

    }

    @FXML
    private void initialize(){
        initMenuMedic();
        transition = new HamburgerSlideCloseTransition(this.menuButton);
        transition.setRate(-1);
        menuDrawer.setDisable(true);
    }

    private void initMenuMedic() {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane = null;
        loader.setLocation(getClass().getResource("/viewController/menuMedic.fxml"));
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuMedic medicController = loader.getController();
        medicController.setStage(this.mainStage);
        medicController.setMainPane(this.centerMenuPane);
        menuDrawer.setSidePane(mainPane);
    }

    public void setMainStage(Stage stage) {
        this.mainStage=stage;
    }
}
