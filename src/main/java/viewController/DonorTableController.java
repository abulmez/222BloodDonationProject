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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import model.Admin;
import model.Donor;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;
import utils.CustomDonorDeserializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class DonorTableController extends AbstractTableController<Donor> {
    @FXML
    private TableColumn<Donor, String> columnCnp,columnNume,columnMail,columnTelefon,columnSange;
    @FXML
    private TableColumn<Donor,Float> columnGreutate;
    @FXML
    private TableColumn<Donor,LocalDate> columnData;
    @FXML
    private TableColumn<Donor,String> columnUsername,columnAddress;
    @FXML
    private TableView<Donor> table;

    private AdminMainPanelController ctr;
    private AdminService service;
    private ObservableList<Donor> obs;
    private ArrayList<String> users;
    private Donor d;
    private String cnp="";
    private String u="";

    public void setCtr(AdminMainPanelController ctr){
        this.ctr=ctr;
    }

    @Override
    public void delete(){
        try {
            service.deleteUser("Donor", d.getCnp());
            ErrorMessage.showMessage(null, Alert.AlertType.INFORMATION, "Succes", "Donatorul a fost sters cu succes");
        }catch (Exception e){
            ErrorMessage.showErrorMessage(null,"Trebuie sa selectati un donator!");
        }
    }

    @Override
    public void refresh(String text){
        ArrayList<Donor> donors=new ArrayList<>();
        ObservableList<Donor> obs2;
        for (Donor d:obs){
            if (d.getName().contains(text))
                donors.add(d);
        }
        obs2=FXCollections.observableArrayList(donors);
        table.setItems(obs2);
    }

    @FXML
    public void handle(){
        d=table.getSelectionModel().getSelectedItem();
    }


    public void initialize(){
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(AdminService.class);
        columnCnp.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        columnNume.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        columnTelefon.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnSange.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        columnGreutate.setCellValueFactory(new PropertyValueFactory<>("weight"));
        columnAddress.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue().getCnp()));
        table.setEditable(true);
        String response=service.getUsers("Donor");
        System.out.println(response);
        Gson gson=new GsonBuilder().registerTypeAdapter(Donor.class,new CustomDonorDeserializer()).create();
        Type collectionType = new TypeToken<ArrayList<Donor>>(){}.getType();
        ArrayList<Donor> donors = gson.fromJson(response, collectionType);
        this.obs = FXCollections.observableArrayList(donors);
        table.setItems(obs);
        table.refresh();
        String resp=service.getUsernames("Donor");
        Gson gson2=new Gson();
        Type collectionType2 = new TypeToken<ArrayList<String>>(){}.getType();
        users= gson2.fromJson(resp, collectionType2);

        columnAddress.setCellFactory(column -> {
            return new TableCell<Donor, String>() {
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



        columnUsername.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Donor, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Donor, String> param) {
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
        columnUsername.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, String> event) {
                service.updateUsername(d.getCnp(),event.getNewValue());
                ctr.change();
            }
        });

        columnCnp.setCellFactory(TextFieldTableCell.forTableColumn());
        columnCnp.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, String> event) {
                String oldCnp=d.getCnp();
                ((Donor) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setCnp(event.getNewValue());
                Donor donor=obs.get(event.getTablePosition().getRow());
                System.out.println(donor.getCnp());
                if(service.checkCnp(donor.getCnp()).equals("no")) {
                    ErrorMessage.showErrorMessage(null, "Acest CNP exista deja");
                    ((Donor) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setCnp(oldCnp);
                    table.setItems(obs);
                    table.refresh();
                }
                else
                service.updateDonor(oldCnp,donor.getCnp(),donor.getName(),donor.getBirthday().toString(),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight().toString());
            }
        });

        columnNume.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNume.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, String> event) {
                String oldCnp=d.getCnp();
                ((Donor) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setName(event.getNewValue());
                Donor donor=obs.get(event.getTablePosition().getRow());
                System.out.println(donor.getCnp());
                service.updateDonor(oldCnp,donor.getCnp(),donor.getName(),donor.getBirthday().toString(),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight().toString());
            }
        });

        columnData.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        columnData.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, LocalDate> event) {
                String oldCnp=d.getCnp();
                ((Donor) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setBirthday(event.getNewValue());
                Donor donor=obs.get(event.getTablePosition().getRow());
                System.out.println(donor.getCnp());
                service.updateDonor(oldCnp,donor.getCnp(),donor.getName(),donor.getBirthday().toString(),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight().toString());
            }
        });

        columnMail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, String> event) {
                String oldCnp=d.getCnp();
                ((Donor) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setMail(event.getNewValue());
                Donor donor=obs.get(event.getTablePosition().getRow());
                System.out.println(donor.getCnp());
                service.updateDonor(oldCnp,donor.getCnp(),donor.getName(),donor.getBirthday().toString(),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight().toString());
            }
        });

        columnTelefon.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTelefon.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, String> event) {
                String oldCnp=d.getCnp();
                ((Donor) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setPhone(event.getNewValue());
                Donor donor=obs.get(event.getTablePosition().getRow());
                System.out.println(donor.getCnp());
                service.updateDonor(oldCnp,donor.getCnp(),donor.getName(),donor.getBirthday().toString(),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight().toString());
            }
        });

        columnSange.setCellFactory(TextFieldTableCell.forTableColumn());
        columnSange.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, String> event) {
                String oldCnp=d.getCnp();
                ((Donor) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setBloodGroup(event.getNewValue());
                Donor donor=obs.get(event.getTablePosition().getRow());
                System.out.println(donor.getCnp());
                service.updateDonor(oldCnp,donor.getCnp(),donor.getName(),donor.getBirthday().toString(),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight().toString());
            }
        });

        columnGreutate.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        columnGreutate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Donor, Float>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Donor, Float> event) {
                String oldCnp=d.getCnp();
                ((Donor) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setWeight(event.getNewValue());
                Donor donor=obs.get(event.getTablePosition().getRow());
                System.out.println(donor.getCnp());
                service.updateDonor(oldCnp,donor.getCnp(),donor.getName(),donor.getBirthday().toString(),donor.getMail(),donor.getPhone(),donor.getBloodGroup(),donor.getWeight().toString());
            }
        });
    }

    public String getUser(){
        String u=users.get(0);
        users.remove(0);
        return u;
    }

    @Override
    public void add(){

    }



}
