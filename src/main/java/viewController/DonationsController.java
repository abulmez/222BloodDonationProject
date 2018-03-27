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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DonationsController {

    @FXML
    private Button addButton;

    @FXML
    private ComboBox modifyComboBox;


    public void  initialize(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "In curs de validare",
                        "In proces de descompunere",
                        "Pregatit",
                        "Trimis spre donare"
                );
       modifyComboBox.setItems(options);
       modifyComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue observable, String oldValue, String newValue) {
               if(newValue.equals("In proces de descompunere")){
                   Stage secondaryStage=new Stage();
                   try {
                       Parent root = FXMLLoader.load(getClass().getResource("/donationsReport.fxml"));
                       secondaryStage.setTitle("Adauga raport");
                       secondaryStage.setScene(new Scene(root, 300, 400));
                       secondaryStage.show();
                   }
                   catch (IOException e){
                       e.printStackTrace();
                   }
               }
           }
       });

    }

    @FXML
    public void handleAdd(ActionEvent event){
        Stage secondaryStage=new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/donationsAdd.fxml"));
            secondaryStage.setTitle("Adauga donatie");
            secondaryStage.setScene(new Scene(root, 300, 400));
            secondaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


}
