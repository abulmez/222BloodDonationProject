package viewController;

import com.jfoenix.controls.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private JFXTextField usernameText;

    @FXML
    private JFXPasswordField passwordText;

    @FXML
    private JFXTextField nameText;

    @FXML
    private JFXTextField cnpText;

    @FXML
    private JFXTextField phoneText;

    @FXML
    private JFXTextField emailText;

    @FXML
    private JFXDatePicker birtdayText;

    @FXML
    private JFXTextField weightText;

    @FXML
    private Label warningLabel;
    private Stage stage;
    private Double progressIndex= Double.valueOf(0);
    @FXML
    private void initialize(){


        warningLabel.setText("Vă rugăm completați toate câmpurile");

        bindProgressBar(usernameText);
        bindProgressBar(passwordText);
        bindProgressBar(nameText);
        bindProgressBar(cnpText);
        bindProgressBar(phoneText);
        bindProgressBar(emailText);
        bindProgressBar(weightText);
        birtdayText.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!(newValue ==null) && oldValue==null)
            {
                progressIndex =progressIndex+0.125;
                progressBar.progressProperty().set(progressIndex);

            }
            else {
                if(newValue ==null && !(oldValue==null))
                    progressIndex =progressIndex+0.125;
                    progressBar.progressProperty().set(progressIndex);

            }
        });

    }

    private void bindProgressBar(TextInputControl fieldText) {

        fieldText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("") && oldValue.equals(""))
                {
                    progressIndex =progressIndex+0.125;
                    progressBar.progressProperty().set(progressIndex);
                }
            else {
                    if(newValue.equals("")&& !(oldValue.equals("")))
                        progressIndex =progressIndex-0.125;
                        progressBar.progressProperty().set(progressIndex);

            }
        });
    }

    @FXML
    void closeAction(MouseEvent event) {
        this.stage.close();
    }

    @FXML
    void registerAction(MouseEvent event) {
        if(!progressIndex.equals(1))
            warningLabel.setText("Nu ai completat toate câmpurile");
    }
    public void setMainStage(Stage stage){
        this.stage=stage;
    }
}
