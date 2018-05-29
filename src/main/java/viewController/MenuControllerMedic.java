package viewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.ApplicationContext;
import service.MedicService;
import utils.CommonUtils;

import java.io.IOException;
import java.net.URL;

public class MenuControllerMedic extends CenterMenuController {

    public void initFirstPanel() {

        setPan(getClass().getResource("/viewController/bloodRequests.fxml"),centerManeuPane);

    }

    public void setPan(URL resource, AnchorPane centerManeuPane) {
        centerManeuPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        ApplicationContext context= CommonUtils.getFactory();

        MedicService service=context.getBean(MedicService.class);
        AnchorPane mainPane = null;
        loader.setLocation(resource);
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        BloodRequestsController ctrl=loader.getController();
        ctrl.setService(service,mainStage.getMainStage());
        centerManeuPane.getChildren().add(mainPane);
    }

    public void setPan1(URL resource, AnchorPane centerManeuPane) {
        centerManeuPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        ApplicationContext context= CommonUtils.getFactory();

        MedicService service=context.getBean(MedicService.class);
        AnchorPane mainPane = null;
        loader.setLocation(resource);
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        FormController ctrl=loader.getController();
        ctrl.setService(service);
        centerManeuPane.getChildren().add(mainPane);
    }
    public void setPan2(URL resource, AnchorPane centerManeuPane) {
        centerManeuPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader();
        ApplicationContext context= CommonUtils.getFactory();

        MedicService service=context.getBean(MedicService.class);
        AnchorPane mainPane = null;
        loader.setLocation(resource);
        try {
            mainPane = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
        DonationCentersMedicController ctrl=loader.getController();
        ctrl.setService(service,mainStage.getMainStage());
        centerManeuPane.getChildren().add(mainPane);
    }

    @FXML
    private void requestAction(MouseEvent event) {
        setPan1(getClass().getResource("/viewController/Form.fxml"),centerManeuPane);
        mainStage.openMenu();
    }

    @FXML
    private void visualizeAction(MouseEvent event) {
        setPan(getClass().getResource("/viewController/bloodRequests.fxml"), centerManeuPane);
        mainStage.openMenu();
    }
    @FXML
    private void viewCenters(MouseEvent event) {
        setPan2(getClass().getResource("/viewController/donationCentersMedic.fxml"), centerManeuPane);
        mainStage.openMenu();
    }
    @FXML
    private void logoutAction(){
        exitAction();
    }

}
