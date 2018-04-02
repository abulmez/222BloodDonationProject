package viewController;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DonationsAppointmentsAdminController {
    public DonationsAppointmentsAdminController(){}

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<TableSetterGetter> paginationTableView;

    @FXML
    private TableColumn<TableSetterGetter,Integer> NumarRezervare,NumarCentruDonatie,LocuriDisponibile;

    @FXML
    private TableColumn<TableSetterGetter,DateCell> DataDonarii;

    int from=0, to=0;
    int itemPerPage = 10;

    private Node createPage(int pageIndex){
        from = pageIndex*itemPerPage;
        to=itemPerPage;
        paginationTableView.setItems(FXCollections.observableArrayList(getTableData()));
        return paginationTableView;
    }

    public List<TableSetterGetter> getTableData(){
        List<TableSetterGetter> data =new ArrayList<>();

        return data;
    }

    @FXML
    public void initialize(){
        int count=0;

        /*NumarRezervare.setCellValueFactory(new PropertyValueFactory<>("NumarRezervare"));
        NumarCentruDonatie.setCellValueFactory(new PropertyValueFactory<>("NumarCentruDonatie"));
        LocuriDisponibile.setCellValueFactory(new PropertyValueFactory<>("LocuriDisponibile"));
        DataDonarii.setCellValueFactory(new PropertyValueFactory<>("DataDonarii"));

        int pageCount = (count / itemPerPage + 1);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);*/

    }
}
