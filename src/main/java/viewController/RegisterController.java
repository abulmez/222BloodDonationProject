package viewController;

import com.jfoenix.controls.*;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Donor;
import model.UserLoginData;
import model.UserType;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.springframework.context.ApplicationContext;
import service.DonorService;
import utils.CommonUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private JFXDatePicker birthdayText;
    @FXML
    private JFXTextField weightText;
    @FXML
    private JFXComboBox<String> bloodText;
    @FXML
    private Label warningLabel;

    private Stage stage;
    private Integer progressIndex= 0;

    private MutableBoolean phoneSet = new MutableBoolean(false);
    private MutableBoolean emailSet = new MutableBoolean(false);
    private MutableBoolean passwordSet = new MutableBoolean(false);
    private MutableBoolean nameSet = new MutableBoolean(false);
    private MutableBoolean usernameSet = new MutableBoolean(false);
    private MutableBoolean cnpSet = new MutableBoolean(false);
    private MutableBoolean bloodSet = new MutableBoolean(false);
    private MutableBoolean weightSet = new MutableBoolean(false);
    private MutableBoolean dateSet=new MutableBoolean(false);

    private DonorService service;

    public RegisterController() {
    }

    @FXML
    private void initialize(){
        initService();
        setGeneralWarning();
        initProgressBar();
        initBloodCombo();
        CommonUtils.makeNumericTextField(phoneText);
        CommonUtils.makeNumericTextField(cnpText);
        CommonUtils.makeDoubleTextField(weightText);
    }

    private void initBloodCombo() {
        ObservableList<String> options =
                FXCollections.observableArrayList("0", "A", "B", "AB");
        bloodText.setItems(options);
    }



    private void setGeneralWarning() {
        warningLabel.setText("Vă rugăm completați toate câmpurile");
    }
    private void initService() {
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(DonorService.class);
    }
    private void initProgressBar() {
        progressBar.progressProperty().set(progressIndex);
        bindProgressBar(usernameText);
        bindProgressBar(passwordText);
        bindProgressBar(nameText);
        bindProgressBar(cnpText);
        bindProgressBar(phoneText);
        bindProgressBar(emailText);
        bindProgressBar(weightText);
        bindProgressBarForDate();
        bindProgressForBlood();
    }



    private void bindProgressBarForDate() {
        birthdayText.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!(newValue ==null) && oldValue==null)
                dateSet.setTrue();
            if(newValue==null || newValue.equals(""))
                dateSet.setFalse();
            setProgress();
        });
    }
    private void bindProgressForBlood(){
        bloodText.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                bloodSet.setTrue();
                setProgress();
            }
        });
    }

    private void bindProgressBar(TextInputControl fieldText) {
        fieldText.textProperty().addListener((observable, oldValue, newValue) -> {
            if(fieldText==phoneText)
                confirmProgressForPhone();
            else if(fieldText==emailText)
                confirmProgressForEmail();
            else if(fieldText==passwordText)
                confirmProgressForPassword();
            else if (fieldText==weightText)
                confirmProgressForWeight();
            else if (fieldText==usernameText)
                confirmProgressGeneral(usernameText,usernameSet);
            else if(fieldText==cnpText)
                confirmProgressForCnp();
            else if(fieldText==nameText)
                confirmProgressGeneral(nameText,nameSet);
        });
    }

    private void confirmProgressForCnp() {
        if(cnpText.getText().length()>13) {
            String s = cnpText.getText().substring(0, 13);
            cnpText.setText(s);
        }
        if (CommonUtils.validNumberLength(cnpText.getText(),13)) {
            cnpSet.setTrue();
            setProgress();
        }
        else {
            cnpSet.setFalse();
            setProgress();
        }
    }

    private void confirmProgressGeneral(TextField textField, MutableBoolean valid) {
        if(!textField.getText().equals(""))
            valid.setTrue();
        else valid.setFalse();
        setProgress();
    }

    private void confirmProgressForWeight() {
        if(weightText.getText().length()>10) {
            String s = weightText.getText().substring(0, 10);
            weightText.setText(s);
        }
        if (CommonUtils.validWeight(weightText.getText())) {
            weightSet.setTrue();
        }
        else weightSet.setFalse();
        setProgress();

    }

    private void setProgress(){
        List<MutableBoolean> list = getMutableBooleans();
        progressIndex = 1;
        for (MutableBoolean elem:list)
            if(elem.isTrue())
                progressIndex=progressIndex+1;
        double d = (progressIndex-1) * 1.0 / 9;
        progressBar.setProgress(d);
    }

    private List<MutableBoolean> getMutableBooleans() {
        List<MutableBoolean> list = new ArrayList<>();
        list.add(usernameSet);
        list.add(passwordSet);
        list.add(nameSet);
        list.add(phoneSet);
        list.add(emailSet);
        list.add(cnpSet);
        list.add(weightSet);
        list.add(dateSet);
        list.add(bloodSet);
        return list;
    }

    private void confirmProgressForPhone() {
        if(phoneText.getText().length()>10) {
            String s = phoneText.getText().substring(0, 10);
            phoneText.setText(s);
        }
        if (CommonUtils.validNumberLength(phoneText.getText(),10)) {
            phoneSet.setTrue();
            setProgress();
        }
        else {
            phoneSet.setFalse();
            setProgress();
        }

    }

    private void confirmProgressForPassword() {
        if (CommonUtils.validPassword(passwordText.getText())) {
            if (passwordSet.isFalse()) {
                passwordSet.setTrue();
                setProgress();
            }
        }
        else{
            timedWarning("Parola trebuie de minim 8 caractere conținând minim o cifră și o literă");
            if(passwordSet.isTrue())
            {
                passwordSet.setFalse();
                setProgress();
            }
        }
    }

    private void confirmProgressForEmail() {
        if (CommonUtils.validEmail(emailText.getText())) {
            emailSet.setTrue();
            setProgress();
        }
        else{
            emailSet.setFalse();
            setProgress();
        }
    }

    private void timedWarning(String text) {
        warningLabel.setText(text);
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(5)
        );
        visiblePause.setOnFinished(
                event -> setGeneralWarning()
        );
        visiblePause.play();
    }

    @FXML
    void closeAction(MouseEvent event) {
        this.stage.close();
    }

    @FXML
    void registerAction(MouseEvent event) {

        if(progressIndex!=10)
            timedWarning("Nu ai completat toate câmpurile");
        else {
            String cnp = cnpText.getText();
            String name = nameText.getText();
            LocalDate birthday = birthdayText.getValue();
            String email = emailText.getText();
            String phone = phoneText.getText();
            String bloodGroup = bloodText.valueProperty().get();
            Double weight = Double.parseDouble(weightText.getText());

            String username = usernameText.getText();
            String password = passwordText.getText();
            UserLoginData loginData = new UserLoginData(username,password, UserType.Donor);
            Donor donor = new Donor(-1,cnp,name,birthday,email,phone,bloodGroup,weight);
            int result = service.createLogin(loginData,donor);
            String resultString;
            if (result==409)
                resultString="Contul există deja!";
            else if(result==201)
                resultString="Contul a fost creat cu succes!";
            else
                resultString="Datele introduse sunt incorecte";
            CommonUtils.showMessage(Alert.AlertType.INFORMATION,"Rezultatul înregistrării",resultString);
            if(result==201)
                this.stage.close();
        }
    }
    public void setMainStage(Stage stage){
        this.stage=stage;
    }
}