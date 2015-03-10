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
 * Created by Anna on 09.03.15.
 */

public class MeetingInfoController implements ControlledScreen, Initializable {
	MainController myController;
	
	@FXML Button okButton;	
	
	@FXML
    public void okButtonClick(ActionEvent e){
		//Move user back to calendar. 
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