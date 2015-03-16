package gui;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;
import dbconnection.MeetingDB;
import model.Meeting;
import model.MeetingVeiw;
import model.Room;
import model.RoomVeiw;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class MeetingAttendence implements ControlledScreen, Initializable {
	MainController myController;
	
	@FXML TableView<MeetingVeiw> meetingAttendence;
    @FXML TableColumn<MeetingVeiw, String> userColumn;   
    @FXML TableColumn<MeetingVeiw, String> statusColumn;
    
    @FXML Button backButton;
  
    MeetingVeiw meeting;
    
	private ObservableList<MeetingVeiw> data = FXCollections.observableArrayList();

	private ControlledScreen meetingOverviewController;
	

    @FXML
    public void backButtonClick(ActionEvent e){
		myController.setView(CalendarClient.MEETING_OVERVIEW_VIEW);
	}
		
    private void tableSetup(){
		meetingAttendence.getSelectionModel().clearSelection();
        this.meeting = ((MeetingOverviewController) meetingOverviewController).getMeetingVeiw();
    	data.addAll((ArrayList<MeetingVeiw>) MeetingDB.getAttendenceForMeeting(MeetingDB.getMeeting(meeting.getMeetingID())));
    	meetingAttendence.setItems(data);
    }
    
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void viewRefresh() {
		data = FXCollections.observableArrayList();
		this.meetingOverviewController = myController.getControllerForScreen(CalendarClient.MEETING_OVERVIEW_SCREEN);
		tableSetup();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { 
		meetingAttendence.setEditable(true);
		userColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("user"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("status"));
	}

	@Override
	public void clearView() {
		
	}
}