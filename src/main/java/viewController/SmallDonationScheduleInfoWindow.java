package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.dto.UserIllnessDto;

public class SmallDonationScheduleInfoWindow {

    @FXML
    private ListView listView;

    @FXML
    private TextField numeText,cnpText,sangeText,boalaText2;

    @FXML
    private TextArea boalaText;

   /* private List<Illness> list;

    private ObservableList<Illness> model;*/


    public void initialize(){


    }

    public void setEntity(UserIllnessDto entity){
        this.numeText.setText(entity.getNameUser());
        this.cnpText.setText(entity.getCnp());
        this.sangeText.setText(entity.getBloodGroup());
        //this.dataNasteriiLabel.setText(entity.getNameIllness());
        this.boalaText.setText(entity.getDescription());

        /*this.list = list;
        this.model = FXCollections.observableArrayList(this.list);
        listView.setItems(model);*/
    }
}
