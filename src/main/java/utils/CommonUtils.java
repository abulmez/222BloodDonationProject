package utils;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Objects;

public class CommonUtils {
    private static ApplicationContext factory;

    private CommonUtils(){}

    public static ApplicationContext getFactory() {
        if(factory == null){
            factory = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        }
        return factory;
    }

    public static boolean validWeight(String text) {
        try {
            Float.parseFloat(text);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }
    public static boolean validEmail(String text){
        return text.matches("^[\\w!#$%&'*+/=?`{|" +
                "}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }
    public static boolean validNumberLength(String text,Integer length){
        return text.matches("\\d{"+length+"}");
    }
    public static boolean validPassword(String text){
        return text.matches("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}");
    }
    public static void makeDoubleTextField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]{1,3}(\\.[0-9]*)?")) {
                    textField.setText(newValue.replaceAll("[^\\d.]", ""));
                    StringBuilder aus = new StringBuilder(newValue);
                    boolean firstPointFound = false;
                    for (int i = 0; i < aus.length(); i++){
                        if(aus.charAt(i) == '.') {
                            if(!firstPointFound)
                                firstPointFound = true;
                            else
                                aus.deleteCharAt(i);
                        }
                    }
                    newValue = aus.toString();
                }
            }
        });
    }
    public static void makeNumericTextField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    public void bindAutoComplete(TextField textField, List<String> sugestion) {

        new AutoCompletionTextFieldBinding<>(textField, SuggestionProvider.create(sugestion));
    }

    public enum NumericConstrain{CONSTRAIN,FREE,BIG_CONSTRAIN;}
    public enum Comparators{Lower,Bigger,Equal;}

    public void setNumericTextField(TextField textField, NumericConstrain constrain){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equalsIgnoreCase("")&&!newValue.equals(oldValue)){
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                else {
                    if(!Objects.equals(newValue, "")) {
                        Integer value = Integer.parseInt(newValue);
                        if (constrain == NumericConstrain.CONSTRAIN)
                            if (value > 10)
                                textField.setText("10");

                        if (constrain == NumericConstrain.BIG_CONSTRAIN)
                            if (value > 1000)
                                textField.setText(""+value/10+"");
                    }
                }
            }});
    }
    public static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }
    public static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initStyle(StageStyle.DECORATED);
        message.setAlertType(Alert.AlertType.WARNING);

        message.setContentText(text);
        message.showAndWait();
    }
}
