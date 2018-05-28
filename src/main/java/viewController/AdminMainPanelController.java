package viewController;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;

import java.io.IOException;

public class AdminMainPanelController {
    @FXML
    TextField fieldSearch;
    @FXML
    AnchorPane tableAnchor;
    @FXML
    Button addButton;
    @FXML
    Label labelName;
    @FXML
    ComboBox combo;

    private AdminService service;
    private AbstractTableController ctr;

    public void initialize(){
        combo.getItems().addAll("Admini","Donatori","Medici","Personal");
        combo.setValue("Admini");
        try {
            labelName.setText("Admini");
            ApplicationContext context = CommonUtils.getFactory();
            service = context.getBean(AdminService.class);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewController/adminPanelTabels/adminTable.fxml"));
            AnchorPane newPane=loader.load();
            tableAnchor.getChildren().setAll(newPane);
            ctr= loader.getController();
            addButton.setDisable(false);
            ctr.setCtr(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleDelete(){
        ctr.delete();
        change();

    }

    public void handleAdd(){
        ctr.add();
        change();
    }

    public void handleSearch(){
        ctr.refresh(fieldSearch.getText());
    }

    public void change(){
        if (combo.getValue().equals("Donatori")) {
            try {
                labelName.setText("Donatori");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/viewController/adminPanelTabels/donorTable.fxml"));
                AnchorPane newPane=loader.load();
                tableAnchor.getChildren().setAll(newPane);
                ctr= loader.getController();
                addButton.setDisable(true);
                ctr.setCtr(this);


            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (combo.getValue().equals("Admini")){
            try {
                labelName.setText("Admini");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/viewController/adminPanelTabels/adminTable.fxml"));
                AnchorPane newPane=loader.load();
                tableAnchor.getChildren().setAll(newPane);
                ctr= loader.getController();
                addButton.setDisable(false);
                ctr.setCtr(this);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (combo.getValue().equals("Medici")) {
            try {
                labelName.setText("Medici");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/viewController/adminPanelTabels/medicTable.fxml"));
                AnchorPane newPane=loader.load();
                tableAnchor.getChildren().setAll(newPane);
                ctr= loader.getController();
                addButton.setDisable(false);
                ctr.setCtr(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (combo.getValue().equals("Personal")) {
            try {
                labelName.setText("Personal");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/viewController/adminPanelTabels/tcpTable.fxml"));
                AnchorPane newPane=loader.load();;
                tableAnchor.getChildren().setAll(newPane);
                ctr= loader.getController();
                addButton.setDisable(false);
                ctr.setCtr(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
