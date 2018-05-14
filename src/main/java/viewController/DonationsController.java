package viewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Donation;
import model.Donor;
import org.springframework.context.ApplicationContext;
import service.DonationService;
import service.DonationsReportService;
import service.TCPService;
import utils.CommonUtils;
import utils.DonationDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

public class DonationsController {
    private TCPService service;
    private ObservableList<DonationDTO> model;

    @FXML
    private Button addButton;

    @FXML
    private ComboBox modifyComboBox;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn nrDonatieColumn;

    @FXML
    private TableColumn numeUserColumn;

    @FXML
    private TableColumn cnpDonatorColumn;

    @FXML
    private TableColumn statusColumn;

    @FXML
    private TableColumn cantitateColumn;

    @FXML
    private TableColumn receiverColumn;


    ApplicationContext context = CommonUtils.getFactory();


    private final ObservableList<String> allOptions =
            FXCollections.observableArrayList(
                    "In curs de validare",
                    "Proba invalida",
                    "In proces de descompunere",
                    "Pregatita"
            );


    public void  initialize() {
        service = context.getBean(TCPService.class);
        this.model = FXCollections.observableArrayList(service.handleGetDonations());

        nrDonatieColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, Integer>("idD"));
        numeUserColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, String>("name"));
        cnpDonatorColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, Integer>("cnp"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, String>("status"));
        cantitateColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, Double>("quantity"));
        receiverColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, String>("receiverName"));

        tableView.setItems(model);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DonationDTO>() {
            @Override
            public void changed(ObservableValue observable, DonationDTO oldValue, DonationDTO newValue) {
                if (newValue!=null) {
                    System.out.println(newValue);
                    modifyComboBox.setValue(newValue.getStatus());
                }
            }
        });

        modifyComboBox.setItems(allOptions);

        modifyComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                if (newValue != null) {
                    DonationDTO donation = (DonationDTO) tableView.getSelectionModel().getSelectedItem();
                    if (donation == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Selectati un rand din tabel apoi modificati statusul!");
                        alert.showAndWait();
                    } else {
                        //String status=modifyComboBox.getValue().toString();
                        if (!newValue.equals(donation.getStatus())) {
                            String response = service.handleModifyDonation(newValue, donation.getIdD());
                            if (!response.equals("Success")) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, response);
                                alert.showAndWait();
                            }
                            else {
                                refresh();
                                if(newValue.equals("Pregatita")) {
                                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Sigur doriti sa continuati?",ButtonType.YES,ButtonType.NO );
                                    alert.showAndWait().ifPresent(dialogResponse -> {
                                        if (dialogResponse == ButtonType.YES) { System.out.println("aici");
                                            if (donation.getStatus().equals("In proces de descompunere")) {
                                                System.out.println("aici");
                                                String errors="";
                                                String response1=service.handleAddBloodProduct(donation.getIdD(), "GlobuleRosii", LocalDate.now().plusDays(42), donation.getQuantity() / 3);
                                                String response2=service.handleAddBloodProduct(donation.getIdD(), "Trombocite", LocalDate.now().plusDays(5), donation.getQuantity() / 3);
                                                String response3=service.handleAddBloodProduct(donation.getIdD(), "Plasma", LocalDate.now().plusYears(1), donation.getQuantity() / 3);
                                                if (!response1.equals("Success"))
                                                    errors+=response1+"\n";
                                                if (!response2.equals("Success"))
                                                    errors+=response2+"\n";
                                                if (!response3.equals("Success"))
                                                    errors+=response3+"\n";
                                                if (errors.equals("")){
                                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Operatie reusita!");
                                                    alert1.showAndWait();
                                                }
                                                else{
                                                    Alert alert2 = new Alert(Alert.AlertType.ERROR, errors);
                                                    alert2.showAndWait();
                                                }
                                            } else {
                                                System.out.println("aici");
                                                String response1=service.handleAddBloodProduct(donation.getIdD(), "Proba de sange", LocalDate.now().plusDays(42), donation.getQuantity());
                                                if(!response1.equals("Success")){
                                                    Alert alert1 = new Alert(Alert.AlertType.ERROR, response1);
                                                    alert1.showAndWait();
                                                }
                                                else {
                                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Operatie reusita!");
                                                    alert1.showAndWait();
                                                }
                                                showAddReportWindow(newValue,donation);
                                            }
                                        }
                                    });
                                }
                                if (newValue.equals("In proces de descompunere") || (newValue.equals("Proba invalida")))
                                    showAddReportWindow(newValue,donation);
                            }
                        }
                    }
                }
            }
        });


        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DonationDTO>() {
                    @Override
                    public void changed(ObservableValue<? extends DonationDTO> observable,
                                        DonationDTO oldValue, DonationDTO newValue) {
                        if (newValue!=null){
                            if (newValue.getStatus().equals("Proba invalida"))
                                modifyComboBox.setValue("Proba invalida");
                            else
                            modifyComboBox.setItems(getSubList(allOptions.indexOf(newValue.getStatus()),allOptions.size()));
                        }
                    }
                });

    }

    private ObservableList<String> getSubList(int start,int end) {
        final ObservableList<String> toBeDisplayedList = FXCollections.<String> observableArrayList();
        toBeDisplayedList.addAll(allOptions.subList(start, end));
        return toBeDisplayedList;
    }

    public void refresh(){
        this.model=FXCollections.observableArrayList(service.handleGetDonations());
        Collections.reverse(model);
        tableView.setItems(model);
    }

    @FXML
    public void handleAdd(ActionEvent event){
        Stage secondaryStage=new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/viewController/donationsAdd.fxml"));
            secondaryStage.setTitle("Adauga donatie");
            secondaryStage.setScene(new Scene(root, 300, 400));
            secondaryStage.showAndWait();
            refresh();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showAddReportWindow(String newValue,DonationDTO donation){
        Stage secondaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/donationsReport.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            secondaryStage.setTitle("Adauga raport");
            DonationsReportController ctrl = loader.getController();
            ctrl.initData(newValue, donation.getIdD());
            secondaryStage.setScene(new Scene(root, 300, 400));
            secondaryStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
