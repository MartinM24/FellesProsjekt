package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;
import dbconnection.MeetingDB;
import dbconnection.RoomDB;
import model.Meeting;
import model.Room;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Anna on 09.03.15.
 */

public class MeetingOverviewController implements ControlledScreen, Initializable {
	MainController myController;
	private Meeting meeting;
	
	@FXML Button okButton;
	@FXML TableView<Meeting> meetingOverviewTableView;
	@FXML TableColumn<Meeting, String> dateColumn;
	@FXML TableColumn<Meeting, String> timeColumn;
    @FXML TableColumn<Meeting, String> titleColumn;
    @FXML TableColumn<Meeting, String> placeColumn;
    @FXML TableColumn<Meeting, String> statusColumn;
    @FXML Button seeMoreButton;
    @FXML Label warning;
    
//	public MeetingOverveiwController(Meeting meeting){
//		this.meeting = meeting;
//	}
	
	@FXML
    public void okButtonClick(ActionEvent e){
		//Move user back to calendar. 
		myController.setView(CalendarClient.CALENDAR_VIEW);
	}
	
	public void seeMoreButtonClick(ActionEvent e){
		//TODO Mye. 
		Meeting meeting = (Meeting)meetingOverviewTableView.getSelectionModel().getSelectedItem();
		myController.setView(CalendarClient.MEETING_INFO_VIEW);
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources) { 
		dateColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("Dato"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("Tid"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("Tittel"));
		placeColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("Sted"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("Status"));
    }
	
	@SuppressWarnings("unused")
	private void tableSetup(){
		ArrayList<Meeting> meetingDB = (ArrayList<Meeting>) MeetingDB.getAllMeetings(CalendarClient.getCurrentUser());
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		
		for (int i = 0; i<meetingDB.size(); i++){
//			meetings.add(new Meeting(meetingDB.get(i).getStartDB(), Integer.toString(meetingDB.get(i).getStartHour()), meetingDB.get(i).getDescription(), meetingDB.get(i).getRoom(getName)));
		}
		meetingOverviewTableView.setItems(FXCollections.observableArrayList(meetings));
	}
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}

	

}