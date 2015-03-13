package gui;

import java.net.URL;
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

/**
 * Created by Anna on 09.03.15.
 */

public class MeetingOverviewController implements ControlledScreen, Initializable {
	MainController myController;
	
	@FXML Button okButton;
	@FXML TableView<MeetingVeiw> meetingOverviewTableView;
	@FXML TableColumn<MeetingVeiw, String> dateColumn;
	@FXML TableColumn<MeetingVeiw, String> timeFromColumn;
	@FXML TableColumn<MeetingVeiw, String> timeTooColumn;
    @FXML TableColumn<MeetingVeiw, String> titleColumn;
    @FXML TableColumn<MeetingVeiw, String> placeColumn;
    @FXML TableColumn<MeetingVeiw, String> roomColumn;    
    @FXML TableColumn<MeetingVeiw, String> statusColumn;
    @FXML Button seeMoreButton;
    @FXML Label warning;
    

    private MeetingVeiw meeting;
	private ObservableList<MeetingVeiw> data = FXCollections.observableArrayList();

    


    @Override
    public void viewRefresh() {
    	try{
    		data.removeAll(meetingOverviewTableView.getSelectionModel().getSelectedItems());
            meetingOverviewTableView.getSelectionModel().clearSelection();
    	} catch (Exception e){
    		System.out.println("Kunne ikke oppdatere");
    	}
    	tableSetup();
    }

    @FXML
    public void okButtonClick(ActionEvent e){
		//Move user back to calendar. 
		myController.setView(CalendarClient.CALENDAR_VIEW);
	}
	
	public void seeMoreButtonClick(ActionEvent e){
		//TODO Mye. 
		MeetingVeiw meeting = (MeetingVeiw)meetingOverviewTableView.getSelectionModel().getSelectedItem();
		myController.setView(null);
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
	
	@SuppressWarnings("unused")
	private void tableSetup(){
		ArrayList<Meeting> meetingDB = (ArrayList<Meeting>) MeetingDB.getAllMeetings(CalendarClient.getCurrentUser());
		boolean temp;
		for (int i = 0; i<meetingDB.size(); i++){
			if(Integer.parseInt(MeetingDB.getAttendence(CalendarClient.getCurrentUser(), meetingDB.get(i)))>= 0){
				data.add(fromMeetingToView(meetingDB.get(i)));				
			}
		}
		meetingOverviewTableView.setItems(data);
	}
	
    public MeetingVeiw fromMeetingToView(Meeting meeting){
    	return new MeetingVeiw(meeting.getTimeStart().getDayOfMonth() +"."+meeting.getTimeStart().getMonthValue(), meeting.getTimeStart().getHour()+":"+meeting.getTimeStart().getMinute(), meeting.getTimeEnd().getHour()+":"+meeting.getTimeEnd().getMinute(), meeting.getDescription(), meeting.getPlace(), meeting.getRoom().getName(), MeetingDB.getAttendence(CalendarClient.getCurrentUser(), meeting));
    }
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}

	

}
