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


public class MeetingOverviewController implements ControlledScreen, Initializable {
	private MainController myController;
	
	@FXML TableView<MeetingVeiw> meetingOverviewTableView;
	@FXML TableColumn<MeetingVeiw, String> dateColumn;
	@FXML TableColumn<MeetingVeiw, String> timeFromColumn;
	@FXML TableColumn<MeetingVeiw, String> timeTooColumn;
    @FXML TableColumn<MeetingVeiw, String> titleColumn;
    @FXML TableColumn<MeetingVeiw, String> placeColumn;
    @FXML TableColumn<MeetingVeiw, String> roomColumn;    
    @FXML TableColumn<MeetingVeiw, String> statusColumn;
    
    @FXML Button okButton;
    @FXML Button seeMoreButton;
    @FXML Button editButton;
    @FXML Button deleteButton;
    @FXML Label warning;
    
	private ObservableList<MeetingVeiw> data = FXCollections.observableArrayList();

    @FXML
    public void okButtonClick(ActionEvent e){
        //Move user back to calendar.
        myController.setView(CalendarClient.CALENDAR_VIEW);
    }
    @FXML
    private void editButtonClick(ActionEvent e){
        //Move user back to calendar.
        int meetingID = meetingOverviewTableView.getSelectionModel().getSelectedItem().getMeetingID();
        Meeting meeting = MeetingDB.getMeeting(meetingID);
        EditMeetingController editCtrl = (EditMeetingController) myController.getControllerForScreen(CalendarClient.EDIT_MEETING_SCREEN);
        editCtrl.setMeeting(meeting);
        myController.setView(CalendarClient.EDIT_MEETING_VIEW);
    }

    @FXML
    private void deleteButtonClick(ActionEvent e) {
        int meetingID = meetingOverviewTableView.getSelectionModel().getSelectedItem().getMeetingID();
        MeetingDB.deleteMeeting(meetingID, CalendarClient.getCurrentUser());
        viewRefresh();

    }

	@FXML
	public void seeMoreButtonClick(ActionEvent e){
		MeetingVeiw meeting = (MeetingVeiw)meetingOverviewTableView.getSelectionModel().getSelectedItem();
		myController.setView(null);
	}
	
    public MeetingVeiw fromMeetingToView(Meeting meeting){
    	String room;
    	if (meeting.getRoom() == null){
    		room = "ikke valgt";
    	} else {
    		room = meeting.getRoom().getName();
    	}
    	System.out.println(room);
    	return new MeetingVeiw(meeting.getMeetingID(), meeting.getTimeStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
    			meeting.getStartString(),
    			meeting.getEndString(),
    			meeting.getDescription(),
    			meeting.getPlace(),
    			room,
    			MeetingDB.getAttendence(CalendarClient.getCurrentUser(), meeting)+"");
    }
	
    private void tableSetup(){
		meetingOverviewTableView.getSelectionModel().clearSelection();
    	ArrayList<Meeting> meetingDB = (ArrayList<Meeting>) MeetingDB.getAllMeetings(CalendarClient.getCurrentUser());
    	MeetingVeiw veiw;
    	for (int i = 0; i<meetingDB.size(); i++){
    		System.out.println(i);
    		if(MeetingDB.getAttendence(CalendarClient.getCurrentUser(), meetingDB.get(i)) >= 0){
    			System.out.println("ja");
    			veiw = fromMeetingToView(meetingDB.get(i));
    			data.add(veiw);
    			System.out.println(veiw);
    		}
    	}
    	meetingOverviewTableView.setItems(data);
    }
    
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void viewRefresh() {
		data = FXCollections.observableArrayList();
		tableSetup();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) { 
		meetingOverviewTableView.setEditable(true);
		dateColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("date"));
		timeFromColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("timeFrom"));
		timeTooColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("timeToo"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("title"));
		placeColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("place"));
		roomColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("room"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<MeetingVeiw, String>("status"));
	}

    @Override
    public void clearView() {

    }
}
