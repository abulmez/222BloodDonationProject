package viewController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import errorMessage.ErrorMessage;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import model.Medic;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;
import utils.customDeserializer.CustomMedicDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class MedicTableController extends AbstractTableController<Medic> {
    @FXML
    private TableColumn<Medic, String> columnCnp,columnNume,columnMail,columnTelefon;
    @FXML
    private TableColumn<Medic,LocalDate> columnData;
    @FXML
    private TableColumn<Medic,Integer> columnSpital;
    @FXML
    private TableColumn<Medic,String> columnUsername,columnAddress;
    @FXML
    private TableView<Medic> table;

    private AdminMainPanelController ctr;
    private AdminService service;
    private ObservableList<Medic> obs;
    private ArrayList<String> users;
    private Medic m;

    @Override
    public void refresh(String text){
        ArrayList<Medic> medics=new ArrayList<>();
        ObservableList<Medic> obs2;
        for (Medic m:obs){
            if (m.getName().contains(text))
                medics.add(m);
        }
        obs2=FXCollections.observableArrayList(medics);
        table.setItems(obs2);
    }

    public void initialize() {
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(AdminService.class);
        columnCnp.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        columnNume.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        columnTelefon.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnSpital.setCellValueFactory(new PropertyValueFactory<>("idH"));
        columnAddress.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getCnp()));
        table.setEditable(true);
        String response = service.getUsers("Medic");
        System.out.println(response);
        Gson gson = new GsonBuilder().registerTypeAdapter(Medic.class, new CustomMedicDeserializer()).create();
        Type collectionType = new TypeToken<ArrayList<Medic>>() {}.getType();
        ArrayList<Medic> medics = gson.fromJson(response, collectionType);
        this.obs = FXCollections.observableArrayList(medics);
        table.setItems(obs);
        String resp=service.getUsernames("Medic");
        Gson gson2=new Gson();
        Type collectionType2 = new TypeToken<ArrayList<String>>(){}.getType();
        users= gson2.fromJson(resp, collectionType2);
        //users.add(0,"s");

        columnAddress.setCellFactory(column -> {
            return new TableCell<Medic, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    final Button button = new Button("");
                    super.updateItem(item,empty);
                    button.getStyleClass().add("button");
                    button.setGraphic(new ImageView("./images/View.png"));
                    button.setTooltip(new Tooltip("Vizualizeaza/Modifica adresa"));

                    button.setOnAction(new EventHandler<ActionEvent>(){
                        public void handle(ActionEvent t){
                            final Stage dialog = new Stage();
                                    /*dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                        public void handle(WindowEvent we) {
                                            refreshTable();
                                        }
                                    });*/
                            //dialog.initOwner(editStage);
                            dialog.initModality(Modality.APPLICATION_MODAL);

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/viewController/adminPanelTabels/address.fxml"));
                            try {
                                Parent root = loader.load();
                                dialog.setTitle("Adresa");
                                Scene scene=new Scene(root);
                                scene.getStylesheets().add("stylesheet/donationsCSS.css");
                                dialog.setScene(scene);
                                dialog.show();
                                AddressController ctrl=loader.getController();
                                ctrl.setCnp(item);
                                        /*
                                        FormControllerModifica ctrl= loader.getController();
                                        column.getTableView().getSelectionModel().select(getIndex());
                                        BloodRequestDTO cell= tableView.getSelectionModel().getSelectedItem();
                                        ctrl.setService(cell.getIdBD(),service,cell.getNeededType(),cell.getDescription(),cell.getPriority(),cell.getQuantity(),cell.getBloodProductType());
                                        */
                            }catch (Exception e) {
                                System.out.println(e);
                            }}
                    });

                    if(item!=null)
                        setGraphic(button);
                    else
                    {
                        setGraphic(null);
                    }
                }
            };
        });

        columnUsername.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Medic, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Medic, String> param) {
                String username="";
                boolean flag=false;
                for (String s:users) {
                    if (s.equals(param.getValue().getIdU().toString())) {
                        flag = true;
                        continue;
                    }
                    if (flag) {
                        username = s;
                        flag = false;
                    }
                }
                return new SimpleStringProperty(username);
            }
        });

        columnUsername.setCellFactory(TextFieldTableCell.forTableColumn());
        columnUsername.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Medic, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Medic, String> event) {
                service.updateUsername(m.getCnp(),event.getNewValue());
                ctr.change();
            }
        });

        columnCnp.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCnp.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Medic, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Medic, String> event) {
                String oldCnp=m.getCnp();
                ((Medic) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setCnp(event.getNewValue());
                Medic medic=obs.get(event.getTablePosition().getRow());
                if(service.checkCnp(medic.getCnp()).equals("no")) {
                    ErrorMessage.showErrorMessage(null, "Acest CNP exista deja");
                    ((Medic) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setCnp(oldCnp);
                    table.setItems(obs);
                    table.refresh();
                }
                else
                service.updateAdmin(oldCnp,medic.getCnp(),medic.getName(),medic.getBirthday().toString(),medic.getMail(),medic.getPhone());
            }
        });

        columnNume.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNume.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Medic, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Medic, String> event) {
                String oldCnp=m.getCnp();
                ((Medic) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setName(event.getNewValue());
                Medic medic=obs.get(event.getTablePosition().getRow());
                System.out.println(medic.getCnp());
                service.updateAdmin(oldCnp,medic.getCnp(),medic.getName(),medic.getBirthday().toString(),medic.getMail(),medic.getPhone());
            }
        });

        columnData.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        columnData.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Medic, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Medic, LocalDate> event) {
                String oldCnp=m.getCnp();
                ((Medic) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setBirthday(event.getNewValue());
                Medic medic=obs.get(event.getTablePosition().getRow());
                System.out.println(medic.getCnp());
                service.updateAdmin(oldCnp,medic.getCnp(),medic.getName(),medic.getBirthday().toString(),medic.getMail(),medic.getPhone());
            }
        });

        columnMail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Medic, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Medic, String> event) {
                String oldCnp=m.getCnp();
                ((Medic) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setMail(event.getNewValue());
                Medic medic=obs.get(event.getTablePosition().getRow());
                System.out.println(medic.getCnp());
                service.updateAdmin(oldCnp,medic.getCnp(),medic.getName(),medic.getBirthday().toString(),medic.getMail(),medic.getPhone());
            }
        });

        columnTelefon.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTelefon.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Medic, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Medic, String> event) {
                String oldCnp=m.getCnp();
                ((Medic) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setPhone(event.getNewValue());
                Medic medic=obs.get(event.getTablePosition().getRow());
                System.out.println(medic.getCnp());
                service.updateAdmin(oldCnp,medic.getCnp(),medic.getName(),medic.getBirthday().toString(),medic.getMail(),medic.getPhone());
            }
        });

        columnSpital.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnSpital.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Medic, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Medic, Integer> event) {
                Medic medic=obs.get(event.getTablePosition().getRow());
                String response=service.updateMedicHospital(medic.getCnp(),event.getNewValue().toString());
                if (response.equals("no")) {
                    ErrorMessage.showErrorMessage(null, "Nu exista acest spital!");
                    ((Medic) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setIdH(medic.getIdH());
                    table.setItems(obs);
                    table.refresh();
                }
                else{
                    ((Medic) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setIdH(event.getNewValue());
                }
            }
        });
    }

    public String getUser(){
        String u=users.get(0);
        users.remove(0);
        return u;
    }

    @Override
    public void delete(){
        try {
            service.deleteUser("Medic", m.getCnp());
            ErrorMessage.showMessage(null, Alert.AlertType.INFORMATION, "Succes", "Medicul a fost sters cu succes");
        }catch (Exception e){
            ErrorMessage.showErrorMessage(null,"Trebuie sa selectati un medic!");
        }
    }

    @FXML
    public void handle(){
        m=table.getSelectionModel().getSelectedItem();
    }

    @Override
    public void add(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserInfoController.class.getResource("/viewController/adminPanelTabels/addMedic.fxml"));
        AnchorPane root1 = null;
        try {
            root1 = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddMedicController ctrMedic=loader.getController();
        ctrMedic.setCtr(ctr);
        Stage dialogStage = new Stage();
        ctrMedic.currentStage(dialogStage);
        dialogStage.setTitle("");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root1);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    public void setCtr(AdminMainPanelController ctr){
        this.ctr=ctr;
    }
}
