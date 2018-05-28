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
import model.TCP;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;
import utils.customDeserializer.CustomTCPDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class TCPTableController extends AbstractTableController<TCP> {
    @FXML
    private TableColumn<TCP, String> columnCnp,columnNume,columnMail,columnTelefon;
    @FXML
    private TableColumn<TCP,LocalDate> columnData;
    @FXML
    private TableColumn<TCP,Integer> columnCentru;
    @FXML
    private TableColumn<TCP,String> columnUsername,columnAddress;
    @FXML
    private TableView<TCP> table;

    private AdminMainPanelController ctr;
    private AdminService service;
    private ObservableList<TCP> obs;
    private ArrayList<String> users;
    private TCP tcp;

    @Override
    public void refresh(String text){
        ArrayList<TCP> tcps=new ArrayList<>();
        ObservableList<TCP> obs2;
        for (TCP tcp:obs){
            if (tcp.getName().contains(text))
                tcps.add(tcp);
        }
        obs2=FXCollections.observableArrayList(tcps);
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
        columnCentru.setCellValueFactory(new PropertyValueFactory<>("idDC"));
        columnAddress.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getCnp()));
        table.setEditable(true);
        String response = service.getUsers("TCP");
        System.out.println(response);
        Gson gson = new GsonBuilder().registerTypeAdapter(TCP.class, new CustomTCPDeserializer()).create();
        Type collectionType = new TypeToken<ArrayList<TCP>>() {}.getType();
        ArrayList<TCP> tcps = gson.fromJson(response, collectionType);
        this.obs = FXCollections.observableArrayList(tcps);
        table.setItems(obs);

        String resp=service.getUsernames("TCP");
        Gson gson2=new Gson();
        Type collectionType2 = new TypeToken<ArrayList<String>>(){}.getType();
        users= gson2.fromJson(resp, collectionType2);
        users.add(0,"s");

        columnAddress.setCellFactory(column -> {
            return new TableCell<TCP, String>() {
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


        columnUsername.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TCP, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TCP, String> param) {
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
        columnUsername.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TCP, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TCP, String> event) {
                service.updateUsername(tcp.getCnp(),event.getNewValue());
                ctr.change();
            }
        });

        columnCnp.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCnp.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TCP, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TCP, String> event) {
                String oldCnp=tcp.getCnp();
                ((TCP) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setCnp(event.getNewValue());
                TCP personal=obs.get(event.getTablePosition().getRow());
                if(service.checkCnp(personal.getCnp()).equals("no")) {
                    ErrorMessage.showErrorMessage(null, "Acest CNP exista deja");
                    ((TCP) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setCnp(oldCnp);
                    table.setItems(obs);
                    table.refresh();
                }
                else
                service.updateAdmin(oldCnp,personal.getCnp(),personal.getName(),personal.getBirthday().toString(),personal.getMail(),personal.getPhone());
            }
        });

        columnNume.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNume.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TCP, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TCP, String> event) {
                String oldCnp=tcp.getCnp();
                ((TCP) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setName(event.getNewValue());
                TCP personal=obs.get(event.getTablePosition().getRow());
                System.out.println(personal.getCnp());
                service.updateAdmin(oldCnp,personal.getCnp(),personal.getName(),personal.getBirthday().toString(),personal.getMail(),personal.getPhone());
            }
        });

        columnData.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        columnData.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TCP, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TCP, LocalDate> event) {
                String oldCnp=tcp.getCnp();
                ((TCP) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setBirthday(event.getNewValue());
                TCP personal=obs.get(event.getTablePosition().getRow());
                System.out.println(personal.getCnp());
                service.updateAdmin(oldCnp,personal.getCnp(),personal.getName(),personal.getBirthday().toString(),personal.getMail(),personal.getPhone());
            }
        });

        columnMail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TCP, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TCP, String> event) {
                String oldCnp=tcp.getCnp();
                ((TCP) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setMail(event.getNewValue());
                TCP personal=obs.get(event.getTablePosition().getRow());
                System.out.println(personal.getCnp());
                service.updateAdmin(oldCnp,personal.getCnp(),personal.getName(),personal.getBirthday().toString(),personal.getMail(),personal.getPhone());
            }
        });

        columnTelefon.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTelefon.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TCP, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TCP, String> event) {
                String oldCnp=tcp.getCnp();
                ((TCP) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setPhone(event.getNewValue());
                TCP personal=obs.get(event.getTablePosition().getRow());
                System.out.println(personal.getCnp());
                service.updateAdmin(oldCnp,personal.getCnp(),personal.getName(),personal.getBirthday().toString(),personal.getMail(),personal.getPhone());
            }
        });

        columnCentru.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnCentru.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TCP, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<TCP, Integer> event) {
                String response=service.updateTCPCenter(tcp.getCnp(),event.getNewValue().toString());
                if (response.equals("no")) {
                    ErrorMessage.showErrorMessage(null, "Nu exista acest centru de donație!");
                    ((TCP) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setIdDC(tcp.getIdDC());
                    table.setItems(obs);
                    table.refresh();
                }
                else{
                    ((TCP) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setIdDC(event.getNewValue());
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
            service.deleteUser("TCP", tcp.getCnp());
            ErrorMessage.showMessage(null, Alert.AlertType.INFORMATION, "Succes", "Angajatul a fost sters cu succes");
        }catch (Exception e){
            ErrorMessage.showErrorMessage(null,"Trebuie sa selectati un angajat!");
        }
    }

    @FXML
    public void handle(){
        tcp=table.getSelectionModel().getSelectedItem();
    }

    @Override
    public void add(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserInfoController.class.getResource("/viewController/adminPanelTabels/addTCP.fxml"));
        AnchorPane root1 = null;
        try {
            root1 = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddTCPController ctrTCP=loader.getController();
        ctrTCP.setCtr(ctr);
        Stage dialogStage = new Stage();
        ctrTCP.currentStage(dialogStage);
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
