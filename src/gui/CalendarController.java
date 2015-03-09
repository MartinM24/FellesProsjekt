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

public class CalendarController implements ControlledScreen, Initializable {
	MainController myController;
	@FXML Button newMeetingButton;
    @FXML GridPane calendarGrid;

	

@Override
	public void initialize(URL location, ResourceBundle resources) {
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: red");
        calendarGrid.add(vbox, 0, 11, 1, 2);

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
