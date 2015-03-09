package gui;

import java.net.URL;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class CalendarController implements ControlledScreen, Initializable {
	MainController myController;
	@FXML Button newMeetingButton;
	

@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	public void newMeetingButtonClick(ActionEvent e){
		//Move user back to userLogin. 
		myController.setView(CalendarClient.NEW_MEETING_VIEW);
	}
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage; 
	}

}
