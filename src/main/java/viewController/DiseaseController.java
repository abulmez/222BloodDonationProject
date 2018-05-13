package viewController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import errorMessage.ErrorMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.Donor;
import org.springframework.context.ApplicationContext;
import service.DonorService;
import service.LoginService;
import utils.CommonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiseaseController {

    @FXML
    CheckBox check1,check2,check3,check4,check5,check6,check7,check8,check9,check10,check11,check12,check13,check14;
    Stage stage;
    DonorService service;
    //CheckBox[] array={check1,check2,check3,check4,check5,check6,check7,check8,check9,check10,check11,check12,check13,check14};
    List<CheckBox> checks=new ArrayList<>();
    List<Integer> list=new ArrayList<>();
    Gson gson = new Gson();
    int id= LoginService.getIdU();


    public void currentStage(Stage stage){
        this.stage=stage;
    }

    public void initialize(){
        ApplicationContext context = CommonUtils.getFactory();
        service = context.getBean(DonorService.class);
        list.add(id);
        checks.add(check1);
        checks.add(check2);
        checks.add(check3);
        checks.add(check4);
        checks.add(check5);
        checks.add(check6);
        checks.add(check7);
        checks.add(check8);
        checks.add(check9);
        checks.add(check10);
        checks.add(check11);
        checks.add(check12);
        checks.add(check13);
        checks.add(check14);
        checks();

    }

    public void checks(){
        String trans=service.handleDiseasesChecks(Integer.toString(id));
        Gson gson=new Gson();
        Type collectionType = new TypeToken<List<Integer>>(){}.getType();
        List<Integer> list2 = gson.fromJson(trans, collectionType);
        for (int i:list2){
            if (i<15)
                checks.get(i-1).setSelected(true);
        }
    }

    @FXML
    public void handleSend(){
        for (CheckBox check:checks)
            if (check.isSelected())
                list.add(1);
            else
                list.add(0);
        String transfer=gson.toJson(list);
        service.handleDiseases(transfer);
        ErrorMessage.showMessage(null, Alert.AlertType.INFORMATION,"Succes","Datele au fost salvate cu succes");
    }
}
