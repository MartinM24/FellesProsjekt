package gui;

import calendarClient.CalendarClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin on 06.03.15.
 */



public class GlobalMenuController implements ControlledScreen, Initializable {
    @FXML Button logOutButton;
    @FXML Button calendarButton;
    private MainController myController;

    @FXML
    private void logOutButtonClick(ActionEvent event) {
        myController.setView(CalendarClient.LOG_IN_VIEW);
    }
    
    @FXML
    private void calendarButtonClick(ActionEvent event) {
    	myController.setView(CalendarClient.CALENDAR_VIEW);
    }

    @Override
    public void setScreenParent(MainController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
