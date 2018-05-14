package viewController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import errorMessage.ErrorMessage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import model.Admin;
import model.Donor;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;
import utils.CustomAdminDeserializer;
import utils.CustomDonorDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AdminTableController extends AbstractTableController<Admin> {
    @FXML
    private TableColumn<Admin,String> columnCnp,columnNume,columnMail,columnTelefon;
    @FXML
    private TableColumn<Admin,LocalDate> columnData;

    @FXML
    private TableColumn<Admin,String> columnUsername;
    @FXML
    private TableView<Admin> table;

    private AdminMainPanelController ctr;
    private AdminService service;
    private ObservableList<Admin> obs;
    private ArrayList<String> users;
    private Admin a;

    public void setCtr(AdminMainPanelController ctr){
        this.ctr=ctr;
    }

    public void refresh(String text){
        ArrayList<Admin> admins=new ArrayList<>();
        ObservableList<Admin> obs2;
        for (Admin a:obs){
            if (a.getName().contains(text))
                admins.add(a);
        }
        obs2=FXCollections.observableArrayList(admins);
        table.setItems(obs2);

    }

    public void initialize(){
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(AdminService.class);
        table.setEditable(true);
        columnCnp.setCellValueFactory(new PropertyValueFactory<Admin,String>("cnp"));
        columnNume.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        columnTelefon.setCellValueFactory(new PropertyValueFactory<>("phone"));
        String response=service.getUsers("Admin");
        System.out.println(response);
        Gson gson=new GsonBuilder().registerTypeAdapter(Admin.class,new CustomAdminDeserializer()).create();
        Type collectionType = new TypeToken<ArrayList<Admin>>(){}.getType();
        ArrayList<Admin> admins = gson.fromJson(response, collectionType);
        this.obs = FXCollections.observableArrayList(admins);
        table.setItems(obs);
        String resp=service.getUsernames("Admin");
        Gson gson2=new Gson();
        Type collectionType2 = new TypeToken<ArrayList<String>>(){}.getType();
        users= gson2.fromJson(resp, collectionType2);
        //users.add(0,"s");

        columnUsername.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Admin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Admin, String> param) {
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

        columnCnp.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCnp.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Admin, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Admin, String> event) {
                 String oldCnp=a.getCnp();
                ((Admin) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setCnp(event.getNewValue());
                Admin ad=obs.get(event.getTablePosition().getRow());
                System.out.println(a.getCnp());
                service.updateAdmin(oldCnp,ad.getCnp(),ad.getName(),ad.getBirthday().toString(),ad.getMail(),ad.getPhone());
            }
        });

        columnNume.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNume.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Admin, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Admin, String> event) {
                String oldCnp=a.getCnp();
                ((Admin) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setName(event.getNewValue());
                Admin ad=obs.get(event.getTablePosition().getRow());
                System.out.println(a.getCnp());
                service.updateAdmin(oldCnp,ad.getCnp(),ad.getName(),ad.getBirthday().toString(),ad.getMail(),ad.getPhone());
            }
        });

        columnData.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        columnData.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Admin, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Admin, LocalDate> event) {
                String oldCnp=a.getCnp();
                LocalDate date=event.getNewValue();
                ((Admin) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setBirthday(date);
                Admin ad=obs.get(event.getTablePosition().getRow());
                System.out.println(a.getCnp());
                service.updateAdmin(oldCnp,ad.getCnp(),ad.getName(),ad.getBirthday().toString(),ad.getMail(),ad.getPhone());
            }
        });

        columnMail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Admin, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Admin, String> event) {
                String oldCnp=a.getCnp();
                ((Admin) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setMail(event.getNewValue());
                Admin ad=obs.get(event.getTablePosition().getRow());
                System.out.println(a.getCnp());
                service.updateAdmin(oldCnp,ad.getCnp(),ad.getName(),ad.getBirthday().toString(),ad.getMail(),ad.getPhone());
            }
        });

        columnTelefon.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTelefon.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Admin, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Admin, String> event) {
                String oldCnp=a.getCnp();
                ((Admin) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setPhone(event.getNewValue());
                Admin ad=obs.get(event.getTablePosition().getRow());
                System.out.println(a.getCnp());
                service.updateAdmin(oldCnp,ad.getCnp(),ad.getName(),ad.getBirthday().toString(),ad.getMail(),ad.getPhone());
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
            service.deleteUser("Admin", a.getCnp());
            ErrorMessage.showMessage(null, Alert.AlertType.INFORMATION,"Succes","Adminul a fost sters cu succes");
        }catch (Exception e){
            ErrorMessage.showErrorMessage(null,"Trebuie sa selectati un admin!");
        }

    }


    @FXML
    public void handleGet(){
        a=table.getSelectionModel().getSelectedItem();
        System.out.println(a.getIdU());
    }

    @Override
    public void add(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserInfoController.class.getResource("/viewController/adminPanelTabels/addAdmin.fxml"));
        AnchorPane root1 = null;
        try {
            root1 = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddAdminController ctrAdmin=loader.getController();
        ctrAdmin.setCtr(ctr);
        Stage dialogStage = new Stage();
        ctrAdmin.currentStage(dialogStage);
        dialogStage.setTitle("");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root1);
        dialogStage.setScene(scene);
        dialogStage.show();
    }
}
