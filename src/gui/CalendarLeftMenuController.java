package gui;

import calendarClient.CalendarClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;

/**
 * Created by Martin on 06.03.15.
 */
public class CalendarLeftMenuController implements Initializable, ControlledScreen {
    private MainController myController;
    @FXML Button chooseCalendarButton;
    @FXML Button myGroupsButton;
    @FXML Button meetingOverviewButton;
    @FXML Button newMeetingButton;
    
    public void chooseCalendarButtonClick(ActionEvent e){
		//Move user back to userLogin. 
		myController.setView(CalendarClient.CHOOSE_CALENDAR_VIEW);
	}
    
    public void myGroupsButtonClick(ActionEvent e){
		//Move user back to userLogin. 
		myController.setView(CalendarClient.MY_GROUPS_VIEW);
	}
    
    public void meetingOverviewButtonClick(ActionEvent e){
		//Move user back to userLogin. 
		myController.setView(CalendarClient.MEETING_OVERVIEW_VIEW);
	}
    
    public void newMeetingButtonClick(ActionEvent e){
		//Move user back to userLogin. 
		myController.setView(CalendarClient.ADD_MEETING_VIEW);
	}
    
    
    @Override
    public void setScreenParent(MainController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
