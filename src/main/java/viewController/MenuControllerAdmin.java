package viewController;

import javafx.fxml.FXML;

public class MenuControllerAdmin extends CenterMenuController {


    public void initFirstPanel(){
        setPanel(getClass().getResource("/viewController/adminMainPanel.fxml"), centerManeuPane);
    }

    @FXML
    public void usersAction() {
        setPanel(getClass().getResource("/viewController/adminMainPanel.fxml"),centerManeuPane);
        mainStage.openMenu();
    }

    @FXML
    public void placesAction() {
        setPanel(getClass().getResource("/viewController/donationsCentreAndHospitals.fxml"),centerManeuPane);
        mainStage.openMenu();
    }

    @FXML
    void logoutAction() {
        exitAction();
    }
}
