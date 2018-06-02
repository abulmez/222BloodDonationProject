package viewController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Illness;
import model.dto.UserIllnessDto;

import java.util.List;

public class SmallDonationScheduleInfoWindow {

    @FXML
    private ListView listView;

    @FXML
    private Label numeLabel,cnpLabel,sangeLabel,dataNasteriiLabel,boalaLabel;

   /* private List<Illness> list;

    private ObservableList<Illness> model;*/


    public void initialize(){


    }

    public void setEntity(UserIllnessDto entity){
        this.numeLabel.setText(entity.getNameUser());
        this.cnpLabel.setText(entity.getCnp());
        this.sangeLabel.setText(entity.getBloodGroup());
        //this.dataNasteriiLabel.setText(entity.getNameIllness());
        this.boalaLabel.setText(entity.getDescription());

        /*this.list = list;
        this.model = FXCollections.observableArrayList(this.list);
        listView.setItems(model);*/
    }
}
