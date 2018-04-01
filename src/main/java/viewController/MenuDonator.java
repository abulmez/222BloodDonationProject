package viewController;


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import static viewController.Utilitys.setPanel;

public class MenuDonator {
    private AnchorPane centerManeuPane;
    private MenuController mainStage;

    public void setMainPane(AnchorPane centerMenuPane) {
        this.centerManeuPane=centerMenuPane;
        //setPanel(getClass().getResource("/viewController/donationAppointment.fxml"),centerManeuPane);
    }
    @FXML
    void consultAction(MouseEvent event) {

    }

    @FXML
    void donationAction(MouseEvent event) {

    }

    @FXML
    void historyAction(MouseEvent event) {
        Utilitys.setPanel(getClass().getResource("/viewController/donationAppointment.fxml"), centerManeuPane);
    }


    @FXML
    void logoutAction(MouseEvent event) {
        mainStage.exitAction();
    }

    public void setStage(MenuController stage) {
        this.mainStage = stage;
    }
}





