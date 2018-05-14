package viewController;

import errorMessage.ErrorMessage;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.BloodRequestDTO;
import service.MedicService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BloodRequestsController {
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
    private TableColumn<BloodRequestDTO,String> colStatus;
    @FXML
    private TableColumn<BloodRequestDTO,Double> colDelivered;
    @FXML
    private TableView<BloodRequestDTO> tableView;
    @FXML
    private TableColumn<BloodRequestDTO,String> updateColumn;
    @FXML
    private ComboBox comboFiltrare;
    public MedicService service;
    public Stage editStage;
    @FXML
    private ObservableList<BloodRequestDTO> model;
    public void setService(MedicService service, Stage stage) {
        this.service = service;
        this.editStage=stage;
        //this.service.addObserver(this);
        loadDataHandler();
    }

    public void initialize(){
        colIdBd.setCellValueFactory(new PropertyValueFactory<>("idBD"));
        colIdH.setCellValueFactory(new PropertyValueFactory<>("idH"));
        colNeededType.setCellValueFactory(new PropertyValueFactory<>("NeededType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colBloodType.setCellValueFactory(new PropertyValueFactory<>("BloodProductType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colDelivered.setCellValueFactory(new PropertyValueFactory<>("Delivered"));
        updateColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getStatus()));
        updateColumn.setCellFactory(column -> {
            return new TableCell<BloodRequestDTO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                        String lol;
                        final Button ucellButton=new Button("");
                        final Button rcellButton=new Button("");
                        final Button vcellButton=new Button("");
                        final HBox box=new HBox(ucellButton,rcellButton);
                        super.updateItem(item,empty);
                        ucellButton.getStyleClass().add("button");
                        rcellButton.getStyleClass().add("button");
                        vcellButton.getStyleClass().add("button");
                        vcellButton.setGraphic(new ImageView("./images/View.png"));
                        rcellButton.setGraphic(new ImageView("./images/Delete.png"));
                        ucellButton.setGraphic(new ImageView("./images/Modifica.png"));
                        rcellButton.setTooltip(new Tooltip("Sterge"));
                        ucellButton.setTooltip(new Tooltip("Modifica"));
                        vcellButton.setTooltip(new Tooltip("Afiseaza centrele din care au venit livrari"));


                    ucellButton.setOnAction(new EventHandler<ActionEvent>(){
                        public void handle(ActionEvent t){
                            final Stage dialog = new Stage();
                            dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                public void handle(WindowEvent we) {
                                    refreshTable();
                                }
                            });
                            dialog.initOwner(editStage);
                            dialog.initModality(Modality.APPLICATION_MODAL);

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/viewController/FormModifica.fxml"));
                            try {
                                Parent root = loader.load();
                                dialog.setTitle("Aplicatie");
                                Scene scene=new Scene(root);
                                scene.getStylesheets().add("donationsCSS.css");
                                dialog.setScene(scene);
                                dialog.show();
                                FormControllerModifica ctrl= loader.getController();
                                column.getTableView().getSelectionModel().select(getIndex());
                                BloodRequestDTO cell= tableView.getSelectionModel().getSelectedItem();
                                ctrl.setService(cell.getIdBD(),service,cell.getNeededType(),cell.getDescription(),cell.getPriority(),cell.getQuantity(),cell.getBloodProductType());
                            }catch (Exception e) {
                                System.out.println(e);
                            }}
                    });

                    rcellButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            column.getTableView().getSelectionModel().select(getIndex());
                            BloodRequestDTO cell= tableView.getSelectionModel().getSelectedItem();
                            String message=service.handleRemove(cell.getIdBD());

                            refreshTable();
                            ErrorMessage.showMessage(editStage, Alert.AlertType.INFORMATION,"Centre",message);
                        }
                    });

                    vcellButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            column.getTableView().getSelectionModel().select(getIndex());
                            BloodRequestDTO cell= tableView.getSelectionModel().getSelectedItem();
                            ErrorMessage.showMessage(editStage, Alert.AlertType.INFORMATION,"Centre",service.handleVizualizare(cell.getIdBD()));
                        }
                    });

                        if(item!=null && (item.equals("Livrata") || item.equals("Initiata"))){
                            setGraphic(vcellButton);
                        }
                        else if(item!=null)
                            setGraphic(box);
                        else
                        {

                            setGraphic(null);
                        }

                }
            };
        });
        //addUpdateButtons(updateColumn);
        ObservableList<String> listCombo1 = FXCollections.observableArrayList("Nefiltrat","Cereri livrate","Cereri plasate/initiate");
        comboFiltrare.setItems(listCombo1);
        comboFiltrare.getSelectionModel().selectFirst();
        comboFiltrare.getStyleClass().add("comboBox");
        comboFiltrare.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t1.equals("Nefiltrat")){
                    List<BloodRequestDTO> list = service.findAllDemands();
                    if(list!=null){
                        List<BloodRequestDTO> shallowCopy = list.subList(0, list.size());
                        Collections.reverse(shallowCopy);
                        model = FXCollections.observableArrayList(shallowCopy);
                        tableView.setItems(model);
                    }
                }
                else if(t1.equals("Cereri livrate")){
                    List<BloodRequestDTO> list = service.handleFiltrareLivrate();
                    if(list!=null){
                        List<BloodRequestDTO> shallowCopy = list.subList(0, list.size());
                        Collections.reverse(shallowCopy);
                        model = FXCollections.observableArrayList(shallowCopy);
                        tableView.setItems(model);
                    }
                }

                else if(t1.equals("Cereri plasate/initiate")){

                List<BloodRequestDTO> list = service.handleFiltrarePlasate();
                if(list!=null){
                    List<BloodRequestDTO> shallowCopy = list.subList(0, list.size());
                    Collections.reverse(shallowCopy);
                    model = FXCollections.observableArrayList(shallowCopy);
                    tableView.setItems(model);
                }
                }
            }
        });

        tableView.setItems(model);
    }
    @FXML
    private void loadDataHandler() {

        List<BloodRequestDTO> list = service.findAllDemands();
        List<BloodRequestDTO> shallowCopy = list.subList(0, list.size());
        Collections.reverse(shallowCopy);
        if(list!=null){

        model = FXCollections.observableArrayList(shallowCopy);
        tableView.setItems(model);}
    }




    @FXML
    public void handleAdauga() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FormController.class.getResource("/viewController/Form.fxml"));
        AnchorPane root1 = null;
        try {
            root1 = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root1);
        dialogStage.setScene(scene);
        dialogStage.show();
    };

    public void refreshTable(){

        if(comboFiltrare.getSelectionModel().getSelectedItem().equals("Cereri livrate")){
            List<BloodRequestDTO> list = service.handleFiltrareLivrate();

            if(list!=null){
                List<BloodRequestDTO> shallowCopy = list.subList(0, list.size());
                Collections.reverse(shallowCopy);
                model = FXCollections.observableArrayList(shallowCopy);
                tableView.setItems(model);
            }
        }

        else if(comboFiltrare.getSelectionModel().getSelectedItem().equals("Cereri plasate/initiate")){

            List<BloodRequestDTO> list = service.handleFiltrarePlasate();
            if(list!=null){
                List<BloodRequestDTO> shallowCopy = list.subList(0, list.size());
                Collections.reverse(shallowCopy);
                model = FXCollections.observableArrayList(shallowCopy);
                tableView.setItems(model);
            }
    }
        else{
            List<BloodRequestDTO> list = service.findAllDemands();
            if(list!=null){
                List<BloodRequestDTO> shallowCopy = list.subList(0, list.size());
                Collections.reverse(shallowCopy);
                model = FXCollections.observableArrayList(shallowCopy);
                tableView.setItems(model);
            }
        }
    }


}

