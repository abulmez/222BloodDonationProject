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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.dto.TableSetterGetterDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DonationCentersInfoController {
    @FXML
    private Pagination pagination;

    @FXML
    private TableView<TableSetterGetterDTO> paginationTableView;

    @FXML
    private TableColumn<TableSetterGetterDTO,Integer> NumarRezervare,NumarCentruDonatie,LocuriDisponibile;

    @FXML
    private TableColumn<TableSetterGetterDTO,DateCell> DataDonarii;

    int from=0, to=0;
    int itemPerPage = 10;

    private Node createPage(int pageIndex){
        from = pageIndex*itemPerPage;
        to=itemPerPage;
        paginationTableView.setItems(FXCollections.observableArrayList(getTableData()));
        return paginationTableView;
    }

    public List<TableSetterGetterDTO> getTableData(){
        List<TableSetterGetterDTO> data =new ArrayList<>();

        return data;
    }

    @FXML
    public void showAddressDonationCenter(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AddressDonationCenterController.class.getResource("/addressDonationCenterInfo.fxml"));
        AnchorPane root16 = null;
        try {
            root16 = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Adresa Centru de Donatii");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root16);
        dialogStage.setScene(scene);
        dialogStage.show();
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
