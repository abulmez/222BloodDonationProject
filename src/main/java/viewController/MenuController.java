package viewController;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.UserType;

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
    private UserType userType;

    public Stage getMainStage() {
        return mainStage;
    }

    public void exitAction(){mainStage.close();}
    @FXML
    public void exitAction(MouseEvent event) {
        mainStage.close();
    }

    @FXML
    public void iconAction(MouseEvent event) {
        mainStage.setIconified(true);
    }

    @FXML
    public void openMenu() {
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
        medicController.setStage(this);
        medicController.setMainPane(this.centerMenuPane);
        medicController.initFirstPanel();
        menuDrawer.setSidePane(mainPane);
    }

    private void initMenuDonator(){
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane = null;
        loader.setLocation(getClass().getResource("/viewController/menuDonator.fxml"));
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }


        MenuDonor menuDonator = loader.getController();
        menuDonator.setStage(this);
        menuDonator.setMainPane(this.centerMenuPane);
        menuDonator.initFirstPanel();
        menuDrawer.setSidePane(mainPane);

    }
    private void initMenuPct(){
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane = null;
        loader.setLocation(getClass().getResource("/viewController/menuTCP.fxml"));
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        MenuTCP menuPct = loader.getController();
        menuPct.setStage(this);
        menuPct.setMainPane(this.centerMenuPane);
        menuPct.initFirstPanel();
        menuDrawer.setSidePane(mainPane);

    }

    private void initMenuAdmin(){
        FXMLLoader loader = new FXMLLoader();
        AnchorPane mainPane = null;
        loader.setLocation(getClass().getResource("/viewController/menuAdmin.fxml"));
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        MenuAdmin menuAdmin = loader.getController();
        menuAdmin.setStage(this);
        menuAdmin.setMainPane(this.centerMenuPane);
        menuAdmin.initFirstPanel();
        menuDrawer.setSidePane(mainPane);
    }


    public void setMainStage(Stage stage) {
        this.mainStage=stage;
    }

    public void setUserType(UserType userType) {
        this.userType=userType;
        if(userType==UserType.Donor)
            initMenuDonator();
        if(userType==UserType.Medic)
            initMenuMedic();
        if(userType==UserType.TCP)
            initMenuPct();
        if(userType==UserType.Admin)
            initMenuAdmin();
    }
}
