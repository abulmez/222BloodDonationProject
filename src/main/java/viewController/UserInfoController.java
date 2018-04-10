package viewController;

import errorMessage.ErrorMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class UserInfoController {
    @FXML
    TextField fieldUsername,fieldNume,fieldCNP,fieldData,fieldSange,fieldGreutate,fieldMail,fieldTelefon;

    @FXML
    public void handleBoli(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserInfoController.class.getResource("/viewController/boli.fxml"));
        AnchorPane root1 = null;
        try {
            root1 = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root1);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    @FXML
    public void handleAdaugaDate(){
        if (fieldUsername.getText().equals("") || fieldCNP.getText().equals("") || fieldNume.getText().equals("") || fieldData.equals("") || fieldSange.equals("") || fieldGreutate.equals("") || fieldTelefon.equals("") || fieldMail.equals(""))
            ErrorMessage.showErrorMessage(null,"Trebuie sa completati toate campurile");
    }
}
