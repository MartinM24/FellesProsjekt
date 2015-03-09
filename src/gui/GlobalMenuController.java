package gui;

import calendarClient.CalendarClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sun.tools.tree.Node;


import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin on 06.03.15.
 */



public class GlobalMenuController implements ControlledScreen, Initializable {
    @FXML Button logOut;
    private MainController myController;

    @FXML
    private void logOutClick(ActionEvent event) {
        System.out.println("Her er jeg");
        myController.setView(CalendarClient.LOG_IN_VIEW);
    }

    @Override
    public void setScreenParent(MainController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
