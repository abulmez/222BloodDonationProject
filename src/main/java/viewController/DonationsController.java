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
import utils.CommonUtils;
import utils.DonationDTO;

import java.io.IOException;

public class DonationsController {
    private DonationService service;
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

    ApplicationContext context = CommonUtils.getFactory();


    public void  initialize() {
        service = context.getBean(DonationService.class);
        this.model = FXCollections.observableArrayList(service.handlePopulate());

        nrDonatieColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, Integer>("idD"));
        numeUserColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, String>("name"));
        cnpDonatorColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, Integer>("cnp"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, String>("status"));
        cantitateColumn.setCellValueFactory(new PropertyValueFactory<DonationDTO, Double>("quantity"));

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

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "In curs de validare",
                        "Proba invalida",
                        "In proces de descompunere",
                        "Pregatit",
                        "Trimis spre donare"
                );
        modifyComboBox.setItems(options);
        modifyComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                DonationDTO donation = (DonationDTO) tableView.getSelectionModel().getSelectedItem();
                if (donation==null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Selectati un rand din tabel apoi modificati statusul!");
                    alert.show();
                } else {
                    //String status=modifyComboBox.getValue().toString();
                    if (!newValue.equals(donation.getStatus())) {
                        String response = service.handleModify(newValue, donation.getIdD());
                        if (!response.equals("Success")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, response);
                            alert.show();
                        }
                        refresh();
                        if (newValue.equals("In proces de descompunere") || newValue.equals("Proba invalida")) {
                            Stage secondaryStage = new Stage();
                            FXMLLoader loader=new FXMLLoader();
                            loader.setLocation(getClass().getResource("/donationsReport.fxml"));
                            Parent root=null;
                            try {
                                root=loader.load();
                                secondaryStage.setTitle("Adauga raport");
                                DonationsReportController ctrl=loader.getController();
                                ctrl.initData(newValue,donation.getIdD());
                                secondaryStage.setScene(new Scene(root, 300, 400));
                                secondaryStage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });


    }
/*
        tableView.getSelectionModel().selectedItemProperty().
                addListener(new ChangeListener<DonationDTO>() {
                    @Override
                    public void changed(ObservableValue<? extends DonationDTO> observable,
                                        DonationDTO oldValue, DonationDTO newValue) {
                    }
                });*/


    public void refresh(){
        this.model=FXCollections.observableArrayList(service.handlePopulate());
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



}
