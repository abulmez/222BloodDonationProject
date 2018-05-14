package viewController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import errorMessage.ErrorMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import service.AdminService;
import utils.CommonUtils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddMedicController {
    @FXML
    TextField fieldNume,fieldCnp,fieldData,fieldMail,fieldTelefon,fieldUser,fieldParola;
    @FXML
    private ComboBox combo;

    AdminMainPanelController ctr;
    Stage stage;
    private AdminService service;
    public void currentStage(Stage stage){
        this.stage=stage;
    }
    public void setCtr(AdminMainPanelController ctr){
        this.ctr=ctr;
    }

    public void initialize(){
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(AdminService.class);
        String trans=service.getHospitals("Medic");
        Gson gson=new Gson();
        Type collectionType = new TypeToken<List<Integer>>(){}.getType();
        List<Integer> list = gson.fromJson(trans, collectionType);
        ObservableList<Integer> obs= FXCollections.observableList(list);
        combo.setItems(obs);
    }

    public boolean validDate(String data){
        boolean yes=true;
        try{
            String[] bday = data.split("-");
            int[] bdayAsInt = Arrays.stream(bday).mapToInt(Integer::parseInt).toArray();
            LocalDate date=LocalDate.of( bdayAsInt[0],bdayAsInt[1],bdayAsInt[2]);
            yes=false;
        }catch (Exception e){}
        return yes;
    }

    @FXML
    public void handleAdd(){
        String name=fieldNume.getText();
        String cnp=fieldCnp.getText();
        String mail=fieldMail.getText();
        String telefon=fieldTelefon.getText();
        String user=fieldUser.getText();
        String pass=fieldParola.getText();
        String data=fieldData.getText();
        String cnpText="";
        String passText="";
        String mailText="";
        String telefonText="";
        String dataText="";
        String numeText="";
        String userText="";
        boolean go=true;

        //String[] bday = data.split("-");
        //int[] bdayAsInt = Arrays.stream(bday).mapToInt(Integer::parseInt).toArray();
        //String date=LocalDate.of( bdayAsInt[0],bdayAsInt[1],bdayAsInt[2]);
        if (!CommonUtils.validNumberLength(cnp,13)) {
            cnpText = "\n CNP incorect";
            go=false;
        }
        if (!CommonUtils.validNumberLength(telefon,10)) {
            telefonText = "\n Numar de telefon incorect";
            go=false;
        }
        if (!CommonUtils.validPassword(pass)) {
            passText = "\n Parola incorecta";
            go=false;
        }
        if (!CommonUtils.validEmail(mail)) {
            mailText = "\n Mail incorect";
            go=false;
        }
        if (validDate(data)){
            dataText="\n Data este de forma yyyy-mm-dd";
            go=false;
        }
        if (name.equals("")){
            numeText="\n Numele nu poate fi vid";
        }
        if (user.equals("")){
            userText="\n Username-ul nu poate fi vid";
        }
        if (!go)
            ErrorMessage.showErrorMessage(null,numeText+cnpText+dataText+mailText+telefonText+userText+passText);
        else {
            service.addMedic(cnp, name, data, mail, telefon, user, pass, combo.getValue().toString());
            ctr.change();
        }
    }
}
