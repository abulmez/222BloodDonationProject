package viewController;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.dto.BloodQuantity;
import model.dto.BloodRequestDTO;
import org.springframework.context.ApplicationContext;
import service.DonorService;
import service.MedicService;
import service.TCPService;
import utils.CommonUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class tcpBloodRequestsController {
    @FXML
    private TableColumn<BloodRequestDTO, Integer> colIdBd;
    @FXML
    private TableColumn<BloodRequestDTO, Integer> colIdH;
    @FXML
    private TableColumn<BloodRequestDTO, String> colNeededType;
    @FXML
    private TableColumn<BloodRequestDTO,String> colDescription;
    @FXML
    private TableColumn<BloodRequestDTO,String> colPriority;
    @FXML
    private TableColumn<BloodRequestDTO,Double> colQuantity;
    @FXML
    private TableColumn<BloodRequestDTO,String> colBloodType;
    @FXML
    private TableColumn<BloodRequestDTO,Double> colDelivered;
    @FXML
    private TableView<BloodRequestDTO> tableView;
    @FXML
    private BarChart<String, Number> bloodChart;
    public TCPService service;
    @FXML
    private ObservableList<BloodRequestDTO> model;
    private Map<String,Map<String,Double>> quantits;
    private String[] bloodGroups={"Toate","0+", "A+", "B+", "AB+","0-", "A-", "B-", "AB-"};

    @FXML
    private JFXComboBox<String> bloodCombo;

    @FXML
    void sendMailsAction(MouseEvent event) {
        if(bloodCombo.getValue()!=null&&!bloodCombo.getValue().equalsIgnoreCase("Toate")){
            service.sendEmailForNeededType(bloodCombo.getValue());
        }
        else{
            CommonUtils.showMessage(Alert.AlertType.WARNING,"Grupa sangvină","Selectați o grupă sangvină");
        }
    }
    private void initService() {
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(TCPService.class);
    }
    public void initialize(){
        initService();
        initBloodCombo();
        initColumns();
        loadDataHandler();
        tableListener();
        comboListener();
        initBloodChart(bloodGroups[0]);
        bloodCombo.getSelectionModel().select(bloodGroups[0]);
    }
    private void initBloodCombo() {
        ObservableList<String> options =
                FXCollections.observableArrayList(bloodGroups);
        bloodCombo.setItems(options);
    }

    private void initColumns() {
        colIdBd.setCellValueFactory(new PropertyValueFactory<>("idBD"));
        colIdH.setCellValueFactory(new PropertyValueFactory<>("idH"));
        colNeededType.setCellValueFactory(new PropertyValueFactory<>("NeededType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colBloodType.setCellValueFactory(new PropertyValueFactory<>("BloodProductType"));
        colDelivered.setCellValueFactory(new PropertyValueFactory<>("Delivered"));
    }
    private void tableListener(){
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                bloodCombo.getSelectionModel().select(newSelection.getNeededType());
            }
        });
    }
    private void comboListener(){
        bloodCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            initBloodChart(newValue);
        });
    }

    @FXML
    private void loadDataHandler() {
        initBloodQuantities();
        List<BloodRequestDTO> list = service.findForAllDemands();
        if(list!=null){
            model = FXCollections.observableArrayList(list);
            tableView.setItems(model);}
    }

    private void initBloodChart(String blood) {
        clearChart();
        bloodChart.getYAxis().setLabel("Cantitate (ml)");
        bloodChart.getXAxis().setLabel("Componente");
        if(!blood.equalsIgnoreCase("Toate")) {
            Map<String, Double> component = quantits.get(blood);
            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName(blood);
            for (Map.Entry<String, Double> entry : component.entrySet()) {
                dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            bloodChart.getData().add(dataSeries);
        }
        else {
            for(int i=1;i<bloodGroups.length;i++){
                blood=bloodGroups[i];
                Map<String, Double> component = quantits.get(blood);
                XYChart.Series dataSeries = new XYChart.Series();
                dataSeries.setName(blood);
                for (Map.Entry<String, Double> entry : component.entrySet()) {
                    dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
                }
                bloodChart.getData().add(dataSeries);
            }
        }
    }

    private void clearChart() {
        bloodChart.getData().clear();
        bloodChart.layout();
    }

    private void initBloodQuantities() {
        List<BloodQuantity> bloodQuantities = service.findBloodQuantitys();
        quantits = new HashMap<>();
        initMap();
        for(BloodQuantity bloodQuantity:bloodQuantities){
            String bloodGroup=bloodQuantity.getBloodGroup();
            String productType=bloodQuantity.getProductType();
            Double quantity = bloodQuantity.getTotalQuantity();
            if(quantits.containsKey(bloodGroup)) {
                if (quantits.get(bloodGroup).containsKey(productType)) {
                    Double auxQuantity = quantits.get(bloodGroup).get(productType);
                    auxQuantity = auxQuantity + quantity;
                    quantits.get(bloodGroup).remove(productType);
                    quantits.get(bloodGroup).put(productType, auxQuantity);
                }
            }
        }
    }

    private void initMap() {
        for(String blood:bloodGroups){
            Map<String,Double> component = new HashMap<>();
            component.put("GlobuleRosii",0d);
            component.put("Plasma",0d);
            component.put("Trombocite",0d);
            quantits.put(blood,component);
        }
    }

}
