package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.BloodRequest;
import service.BloodDemandService;

import java.io.IOException;
import java.util.List;

public class BloodRequestsController {
    @FXML
    private TableColumn<BloodRequest, Integer> colIdBd;
    @FXML
    private TableColumn<BloodRequest, String> colNeededType;
    @FXML
    private TableColumn<BloodRequest,String> colDescription;
    @FXML
    private TableColumn<BloodRequest,String> colPriority;
    @FXML
    private TableColumn<BloodRequest,Integer> colQuantity;
    @FXML
    private TableView<BloodRequest> tableView;
    @FXML
    private TableColumn updateColumn;
    public BloodDemandService service;
    public Stage editStage;
    @FXML
    private ObservableList<BloodRequest> model;
    public void setService(BloodDemandService service,Stage stage) {
        this.service = service;
        this.editStage=stage;
        //this.service.addObserver(this);
        loadDataHandler();
    }

    public void initialize(){
        colIdBd.setCellValueFactory(new PropertyValueFactory<>("idBD"));
        colNeededType.setCellValueFactory(new PropertyValueFactory<>("NeededType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        addUpdateButtons(updateColumn);
        tableView.setItems(model);
    }
    @FXML
    private void loadDataHandler() {
        List<BloodRequest> list = service.findAllDemands();
        if(list!=null){
        System.out.println(list.get(0).getDescription()+"AICI FRAIERE MERGE");
        model = FXCollections.observableArrayList(list);
        tableView.setItems(model);}
    }

    private void addUpdateButtons(TableColumn col){
        col.setCellFactory(new Callback<TableColumn,TableCell>(){
            @Override
            public TableCell call(TableColumn param){
                DemandButtonCell btn=new DemandButtonCell(service,BloodRequestsController.this);
                return btn;
            }
        });
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

    @FXML
    public void handleModifica(Integer id) {
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FormControllerModifica.class.getResource("/FormModifica.fxml"));
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
        dialogStage.show();*/

    };


}

class DemandButtonCell extends TableCell<BloodRequest,Boolean>{
    final Button cellButton=new Button("Update");
    private BloodDemandService service;
    private BloodRequestsController demandController;

    DemandButtonCell(BloodDemandService service,BloodRequestsController ctrl){
        this.service=service;
        demandController=ctrl;
        cellButton.getStyleClass().add("button");
        cellButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                final Stage dialog = new Stage();
                dialog.initOwner(demandController.editStage);
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

                    ctrl.setService(DemandButtonCell.this.getTableView().getItems().get(DemandButtonCell.this.getIndex()).getIdBD(),service);
                }catch (Exception e) {
                    System.out.println(e);
                }}
        });
    }
    @Override
    protected void updateItem(Boolean t,boolean empty){
        super.updateItem(t,empty);
        if(!empty){
            setGraphic(cellButton);
        }
        else
        {

            setGraphic(null);
        }
    }
}
