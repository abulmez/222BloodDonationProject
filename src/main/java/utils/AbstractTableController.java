package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import service.IService;

import java.util.ArrayList;
import java.util.Collection;

public class AbstractTableController <E> {

    @FXML
    protected TableView<E> table;

    protected E selectedEntity;

    protected ObservableList<E> model;

    public void initialize(){

    }

    public void setEntity(Collection<? extends E> entityList){
        loadData(entityList);
    }

    public void loadData(Collection<? extends E> entityList){
        this.model = FXCollections.observableArrayList(new ArrayList<E>(entityList));
        table.setItems(model);
    }

    public void reloadList(Collection<? extends E> entityList){
        table.setItems(null);
        loadData(entityList);
    }

    public E getSelectedEntity(){
        return selectedEntity;
    }
}
