package gui;

import java.net.URL;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class CalendarController implements ControlledScreen, Initializable {
	ScreensController myController; 
	@FXML Button newMeetingButton;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	public void newMeetingButtonClick(ActionEvent e){
		//Move user back to userLogin. 
		myController.setScreen(CalendarClient.ADD_MEETING_SCREEN);
	}
	

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage; 
	}

}
