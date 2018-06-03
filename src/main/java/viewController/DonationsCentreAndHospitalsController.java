package viewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;
import utils.TableType;

import java.io.IOException;
import java.util.Objects;

public class DonationsCentreAndHospitalsController {
    ApplicationContext context = CommonUtils.getFactory();
    AdminService adminService;
    @FXML
    AnchorPane tableAnchorPane;

    public TableType currentTable;

    @FXML
    Button buttonAdd;

    @FXML
    Button buttonDelete;

    @FXML
    ComboBox<String> dropdownlistType;

    @FXML
    public void initialize(){
        adminService = context.getBean(AdminService.class);
        currentTable = TableType.Centru;
        initDropDownList();
        displayTableCentre();
    }


    public void displayTableCentre() {

        try {
            tableAnchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewController/admin-CentreAndHospitalsTables/centreTable.fxml"));
            tableAnchorPane.getChildren().add((AnchorPane) loader.load());

        }catch(IOException e){e.printStackTrace();}
    }

    public void displayTableHospital(){

        try {
            tableAnchorPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewController/admin-CentreAndHospitalsTables/hospitalTable.fxml"));
            tableAnchorPane.getChildren().add((AnchorPane) loader.load());
        }catch(IOException e){e.printStackTrace();}
    }


    public void initDropDownList(){
        dropdownlistType.getItems().add(TableType.Centru.toString());
        dropdownlistType.getItems().add(TableType.Spital.toString());
        dropdownlistType.getSelectionModel().selectFirst();

        dropdownlistType.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(Objects.equals(t1, TableType.Spital.toString()))
                {
                    currentTable = TableType.Spital;
                    displayTableHospital();
                }
                else
                {
                    currentTable = TableType.Centru;
                    displayTableCentre();
                }
            }
        });
    }

    public void buttonAddHandle(){
        if(currentTable == TableType.Centru)
        {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/viewController/admin-CentreAndHospitalsTables/addButtonFormCentre.fxml"));

                AnchorPane root1 = null;
                try {
                    root1 = (AnchorPane) loader.load();
                    AddButtonFormCentreController a = (AddButtonFormCentreController)loader.getController();
                    a.setCtrl(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage dialogStage = new Stage();
                dialogStage.setTitle("");
                Scene scene = new Scene(root1);
                dialogStage.setScene(scene);
                dialogStage.show();
        }
        else{
            System.out.println(currentTable.toString());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewController/admin-CentreAndHospitalsTables/addButtonFormHospital.fxml"));
            AnchorPane root1 = null;
            try {
                root1 = (AnchorPane) loader.load();
                AddButtonFromHospitalController a = (AddButtonFromHospitalController)loader.getController();
                a.setCtrl(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage dialogStage = new Stage();
            dialogStage.setTitle("");
            Scene scene = new Scene(root1);
            dialogStage.setScene(scene);
            dialogStage.show();

        }
    }

    public void handleDelete(){
        if(currentTable == TableType.Centru){
           System.out.println(CentreTableAdminController.donation);
           adminService.deleteCentre(CentreTableAdminController.donation.getIdDC());
           displayTableCentre();
        }
        else if(currentTable==TableType.Spital){
            System.out.println(HospitalTableAdminController.hospital);
            adminService.deleteHospital(HospitalTableAdminController.hospital.getIdH());
            displayTableHospital();
        }
    }
}
