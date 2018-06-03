package viewController;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;


import java.util.ArrayList;

public abstract class AbstractTableController<E> {
    @FXML
    private TableView<E> table;

    abstract void delete();

    abstract void add();

    abstract void setCtr(AdminMainPanelController ctr);

    abstract void refresh(String text);


}
