package gui;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin on 06.03.15.
 */
public class CalendarLeftMenuController implements Initializable, ControlledScreen {
    private MainController myController;
    @Override
    public void setScreenParent(MainController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
