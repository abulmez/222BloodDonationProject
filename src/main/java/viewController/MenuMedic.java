package viewController;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuMedic extends CenterMenu {

    public void initFirstPanel() {
        setPanel(getClass().getResource("/viewController/bloodRequests.fxml"),centerManeuPane);
    }

    @FXML
    private void requestAction(MouseEvent event) {
        setPanel(getClass().getResource("/viewController/Form.fxml"),centerManeuPane);
        mainStage.openMenu();
    }

    @FXML
    private void visualizeAction(MouseEvent event) {
        setPanel(getClass().getResource("/viewController/bloodRequests.fxml"), centerManeuPane);
        mainStage.openMenu();
    }
    @FXML
    private void logoutAction(){
        mainStage.exitAction();
    }

}
