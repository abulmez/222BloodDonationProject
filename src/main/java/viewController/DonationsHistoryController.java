package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.GestureEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Donation;
import model.DonationReport;
import org.springframework.context.ApplicationContext;
import service.DonorService;
import service.LoginService;
import utils.CommonUtils;

import java.io.IOException;

public class DonationsHistoryController {

    @FXML
    Button buttonShowBloodDetails;

    @FXML
    TableView<Donation> tableViewDonations;

    @FXML
    TableColumn<Donation,String> tableColumnIdU;

    @FXML
    TableColumn<Donation,String> tableColumnQuantity;

    @FXML
    TableColumn<Donation,String> tableColumnStatus;

    private DonorService donorService;
    private ObservableList<Donation> modelDonations;
    @FXML
    public void initialize(){
        buttonShowBloodDetails.setDisable(true);
        ApplicationContext context = CommonUtils.getFactory();
        donorService = context.getBean(DonorService.class);
        modelDonations= FXCollections.observableArrayList(donorService.getAllDonations(LoginService.getIdU()));
        initTable(modelDonations);
    }

    public void initTable(ObservableList<Donation> modelDonations){

        tableColumnIdU.setCellValueFactory(new PropertyValueFactory<Donation,String>("IdD"));
        tableColumnQuantity.setCellValueFactory(new PropertyValueFactory<Donation,String>("Quantity"));
        tableColumnStatus.setCellValueFactory(new PropertyValueFactory<Donation,String>("Status"));
        tableViewDonations.setItems(modelDonations);
        tableViewDonations.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
            buttonShowBloodDetails.setDisable(false);
            }
        });
    }

    @FXML
    public void handleShowBloodDetails(){
        Donation don;
        DonationReport result;
        if(!buttonShowBloodDetails.isDisabled()) {
            don = tableViewDonations.getSelectionModel().getSelectedItem();
            if(donorService.getDonationReport(don.getIdD()).getIdDR()!=null)
            result  = donorService.getDonationReport(don.getIdD());
            else
                result  = new DonationReport(1,"Nu exista raport de detalii",false,"Nu exista raport de detalii");


                        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/donationsReportDetails.fxml"));
            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DonationsReportDetails donationsReportDetails = loader.getController();
            donationsReportDetails.setLabels(result);
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Ehee");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);

            buttonShowBloodDetails.setDisable(true);
            stage.show();
        }
    }

}
