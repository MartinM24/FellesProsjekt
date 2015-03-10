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

public class MeetingRoomOverveiwController implements ControlledScreen, Initializable {
	MainController myController;
	private Meeting meeting;

	@FXML TableView<Room> roomTable;
	@FXML TableColumn<Room, String> nameColumn;
	@FXML TableColumn<Room, String> capacityColumn;
	@FXML TableColumn<Room, String> statusColumn;
	@FXML Label warning;
	@FXML Button chooseButton;
	
	public MeetingRoomOverveiwController(Meeting meeting){
		this.meeting = meeting;
	}
	
	public void initialize(URL location, ResourceBundle resources) {	
		nameColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("Navn"));   		
		capacityColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("Kapasitet"));   
		statusColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("Status"));
	}
	
	public void chooseButtonClick(ActionEvent e){
		//TODO Get info from make meeting class. 
		Room room = (Room)roomTable.getSelectionModel().getSelectedItem();
		if (room.getAvalibility().equals("Ledig")) {
			//TODO kommunikasjon mellom screens
			myController.setView(CalendarClient.ADD_MEETING_VIEW);			
		} else {
			if (warning.getText().equals("Venligst velg et ledig rom.")){
				warning.setText("Ledig rom er rom med status ledig.");
			}
			warning.setText("Venligst velg et ledig rom.");
		}
		
		
	}
	
	/** 
	 * Setter inn verdier i tabellen. 
	 */
	
	@SuppressWarnings("unused")
	private void tableSetup(){
		ArrayList<Room> roomsDB = (ArrayList<Room>) RoomDB.getAllRooms();
		ArrayList<ArrayList<ArrayList<LocalDateTime>>> availability = new ArrayList<ArrayList<ArrayList<LocalDateTime>>>();
		ArrayList<String> status = new ArrayList<String>();
		ArrayList<Room> rooms = new ArrayList<Room>();
		for (int i = 0; i<roomsDB.size(); i++){
			availability.add(RoomDB.getAvailability(roomsDB.get(i).getName()));
			status.add("Ledig");
		} 
		for (int i = 0; i<roomsDB.size(); i++){
			for (int j = 0; j<availability.get(i).size(); j++){
				if (checkAvailability(availability.get(i).get(j))){
					status.set(i, "Opptatt");
				}
			}
			rooms.add(new Room(roomsDB.get(i).getName(), Integer.toString(roomsDB.get(i).getCapacity()), status.get(i)));
		}		
		roomTable.setItems(FXCollections.observableArrayList(rooms));
	}
	
	private boolean checkAvailability(ArrayList<LocalDateTime> availability) {
		return  (availability.get(0).isAfter(meeting.getTimeStart()) && availability.get(0).isBefore(meeting.getTimeEnd())) || 
				(availability.get(1).isAfter(meeting.getTimeStart()) && availability.get(1).isBefore(meeting.getTimeEnd())) ||
				(availability.get(0).isBefore(meeting.getTimeStart()) && availability.get(1).isAfter(meeting.getTimeEnd())) ||
				(availability.get(0).isEqual(meeting.getTimeStart()) || availability.get(1).isEqual(meeting.getTimeEnd()));
	}

	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void viewRefresh() {
		// TODO Auto-generated method stub
		
	}

}
