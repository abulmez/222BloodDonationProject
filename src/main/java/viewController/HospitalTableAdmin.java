package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DonationCenter;
import model.Hospital;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import service.HospitalService;
import utils.AbstractTableController;
import utils.CommonUtils;

public class HospitalTableAdmin extends AbstractTableController<Hospital> {
    HospitalService service;

    ApplicationContext context = CommonUtils.getFactory();
    @FXML
    private TableColumn<Hospital,String> idHcolumn;

    @FXML
    private TableColumn<Hospital,String> idAcolumn;

    @FXML
    private TableColumn<Hospital,String> donationHospitalcolumn;

    @FXML
    private TableColumn<Hospital,String> phoneHospitalcolumn;

    @FXML
    public void initialize(){
        service = context.getBean(HospitalService.class);
        idHcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("IdH"));
        idAcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("IdA"));
        donationHospitalcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("HospitalName"));
        phoneHospitalcolumn.setCellValueFactory(new PropertyValueFactory<Hospital,String>("PhoneNumber"));
        super.loadData(service.getAllHospitals());
    }
}
