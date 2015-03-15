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
    
    @FXML Button chooseCalendarsButton;
    @FXML Button myGroupsButton;
    @FXML Button meetingOverviewButton;
    @FXML Button addMeetingButton;
    @FXML Button myInvitation;
    @FXML Button notificationButton;
    @FXML Button changeAttendence;
 

    @FXML
    public void chooseCalendarsButtonClick(ActionEvent e){
		//Move user back to chooseCalendars. 
		myController.setView(CalendarClient.CHOOSE_CALENDARS_VIEW);
	}
    
    @FXML
    public void changeAttendenceButtonClick(ActionEvent e){
		//Move user back to chooseCalendars. 
		myController.setView(CalendarClient.CHANGE_ATTENDENCE_VIEW);
	}
    
    @FXML
    public void myInvitationButtonClick(ActionEvent e){
		//Move user to invitationoverview. 
		myController.setView(CalendarClient.INVITATION_MEETING_VIEW);
	}
    
    @FXML
    public void notificationButtonClick(ActionEvent e){
		//Move user to invitationoverview. 
		myController.setView(CalendarClient.NOTIFICATION_VIEW);
	}
   
    @FXML
    public void myGroupsButtonClick(ActionEvent e){
		//Move user to myGroups. 
		myController.setView(CalendarClient.MY_GROUPS_VIEW);
	}
    
    @FXML
    public void meetingOverviewButtonClick(ActionEvent e){
		//Move user back to meetingOverview. 
		myController.setView(CalendarClient.MEETING_OVERVIEW_VIEW);
	}
   
    
    @FXML
    private void addMeetingButtonClick(ActionEvent e){
		//Move user back to addMeetign. 
		myController.setView(CalendarClient.ADD_MEETING_VIEW);
	}
   
    
    @Override
    public void setScreenParent(MainController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void viewRefresh() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void clearView() {

    }
}
