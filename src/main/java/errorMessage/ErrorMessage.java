package errorMessage;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

    public class ErrorMessage {
        public static void showErrorMessage(Stage owner, String text){
            Alert message=new Alert(Alert.AlertType.ERROR);
            message.initOwner(owner);
            message.setTitle("Mesaj eroare");
            message.setContentText(text);
            message.showAndWait();
        }

        public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
            Alert message=new Alert(type);
            message.setHeaderText(header);
            message.setContentText(text);
            message.initOwner(owner);
            message.showAndWait();
            message.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        }
    }

