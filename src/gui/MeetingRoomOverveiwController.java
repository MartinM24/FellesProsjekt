package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dbconnection.MeetingDB;
import dbconnection.RoomDB;
import model.Meeting;
import model.Room;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
		
		
	}
	
	
	
	
	/**
	 * Sikkelig stygg kode. Haaper den funker. 
	 * Setter inn verdier i tabellen. 
	 * @param start
	 * @param end
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
				if ((availability.get(i).get(j).get(0).isAfter(meeting.getTimeStart()) && availability.get(i).get(j).get(0).isBefore(meeting.getTimeEnd())) || 
					(availability.get(i).get(j).get(1).isAfter(meeting.getTimeStart()) && availability.get(i).get(j).get(1).isBefore(meeting.getTimeEnd())) ||
					(availability.get(i).get(j).get(0).isBefore(meeting.getTimeStart()) && availability.get(i).get(j).get(1).isAfter(meeting.getTimeEnd())) ||
					(availability.get(i).get(j).get(0).isEqual(meeting.getTimeStart()) || availability.get(i).get(j).get(1).isEqual(meeting.getTimeEnd())) 
					){
					status.set(i, "Opptatt");
				}
			}
			rooms.add(new Room(roomsDB.get(i).getName(),  Integer.toString(roomsDB.get(i).getCapacity()), status.get(i)));
		}		
		roomTable.setItems(FXCollections.observableArrayList(rooms));
	}
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}

}
