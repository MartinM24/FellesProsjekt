package gui;

import calendarClient.CalendarClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Martin on 06.03.15.
 */

public class GlobalMenuController implements ControlledScreen, Initializable {
    @FXML Button logOutButton;
    @FXML Label usernameLabel;
    private MainController myController;
    

    @FXML
    private void logOutButtonClick(ActionEvent event) {
        myController.setView(CalendarClient.LOG_IN_VIEW);
    }

    @Override
    public void viewRefresh() {
    	usernameLabel.setText("User: "+calendarClient.CalendarClient.getCurrentUser().getFirstname() +" "+calendarClient.CalendarClient.getCurrentUser().getLastname());
    }

    @Override
    public void setScreenParent(MainController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
