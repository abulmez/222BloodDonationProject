package viewController;


import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuControllerTCP extends CenterMenuController {
    public void initFirstPanel() {
        setPanel(getClass().getResource("/viewController/donations.fxml"),centerManeuPane);
    }

    @FXML
    void confirmAction(MouseEvent event) {
        setPanel(getClass().getResource("/viewController/donationsAppointmentsAdmin.fxml"),centerManeuPane);
        mainStage.openMenu();
    }

    @FXML
    void visualizeAction(MouseEvent event) {
        setPanel(getClass().getResource("/viewController/donations.fxml"),centerManeuPane);
        mainStage.openMenu();
    }

    @FXML
    void inventoryAction(MouseEvent event) {
        setPanel(getClass().getResource("/viewController/bloodProductsInventory.fxml"),centerManeuPane);
        mainStage.openMenu();
    }




    @FXML
    void logoutAction(MouseEvent event) {
        exitAction();
    }

}
