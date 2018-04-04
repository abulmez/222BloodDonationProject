package viewController;

import javafx.fxml.FXML;

public class MenuDonor extends CenterMenu{

    public void initFirstPanel(){
        setPanel(getClass().getResource("/viewController/userInfo.fxml"), centerManeuPane);
    }
    @FXML
    void infoAction() {
        setPanel(getClass().getResource("/viewController/userInfo.fxml"), centerManeuPane);
        mainStage.openMenu();
    }
    @FXML
    void donationAction() {
        setPanel(getClass().getResource("/viewController/donationAppointment.fxml"), centerManeuPane);
        mainStage.openMenu();
    }
    @FXML
    void historyAction() {
        setPanel(getClass().getResource("/viewController/donationsHistory.fxml"), centerManeuPane);
        mainStage.openMenu();
    }

    @FXML
    void logoutAction() {
        exitAction();
    }


}





