package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DonationCenter;
import org.springframework.context.ApplicationContext;
import service.CenterInfoService;
import utils.AbstractTableController;
import utils.CommonUtils;

public class CentreTableAdmin extends AbstractTableController<DonationCenter> {

    CenterInfoService service;

    ApplicationContext context = CommonUtils.getFactory();
    @FXML
    private TableColumn<DonationCenter,String> idDCcolumn;

    @FXML
    private TableColumn<DonationCenter,String> idAcolumn;

    @FXML
    private TableColumn<DonationCenter,String> donationCentercolumn;

    @FXML
    private TableColumn<DonationCenter,String> phoneCentercolumn;

    @FXML
    public void initialize(){
        service = context.getBean(CenterInfoService.class);
        idDCcolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("IdDC"));
        idAcolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("IdA"));
        donationCentercolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("CenterName"));
        phoneCentercolumn.setCellValueFactory(new PropertyValueFactory<DonationCenter,String>("PhoneNumber"));
        super.loadData(service.getAllDonationCenter());
    }

}
