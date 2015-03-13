package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;
import dbconnection.RoomDB;
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

public class MeetingRoomOverviewController implements ControlledScreen, Initializable {
	MainController myController;
	private ControlledScreen addMeetingCtrl;
	
	private LocalDateTime start;
	private LocalDateTime end;
	
	@FXML TableView<RoomVeiw> roomTable;
	@FXML TableColumn<RoomVeiw, String> nameColumn;
	@FXML TableColumn<RoomVeiw, String> capacityColumn;
	@FXML TableColumn<RoomVeiw, String> statusColumn;
	
	@FXML Label warning;
	@FXML Button chooseButton;
	@FXML Button cancelButton;
	
	private RoomVeiw room;
	private ObservableList<RoomVeiw> data = FXCollections.observableArrayList();
	
	
	public void chooseButtonClick(ActionEvent e){
		this.room = (RoomVeiw)roomTable.getSelectionModel().getSelectedItem();
		if (room.getAvalibility().equals("Ledig")) {
			myController.setView(CalendarClient.ADD_MEETING_VIEW);			
		} else {
			if (warning.getText().equals("Venligst velg et ledig rom.")){
				warning.setText("Ledig rom er rom med status ledig.");
			}
			warning.setText("Venligst velg et ledig rom.");
		}		
	}
	
	public void cancelButtonClick(ActionEvent e){
		myController.setView(CalendarClient.ADD_MEETING_VIEW);				
	}
	
	/** 
	 * Setter inn verdier i tabellen. 
	 */
	
	
	private boolean checkAvailability(ArrayList<LocalDateTime> availability) {
		return  (availability.get(0).isAfter(start) && availability.get(0).isBefore(end)) || 
				(availability.get(1).isAfter(start) && availability.get(1).isBefore(end)) ||
				(availability.get(0).isBefore(start) && availability.get(1).isAfter(end)) ||
				(availability.get(0).isEqual(start) || availability.get(1).isEqual(end));
	}

	
	public Room getRoom(){
		if (room.getAvalibility().equals("Ledig")){
			return new Room(room.getName(), room.getCapacity());			
		} else {
			return null;
		}
	}
	
	public void tableSetup(){
		List<Room> roomsDB = RoomDB.getAllRooms();
		ArrayList<ArrayList<ArrayList<LocalDateTime>>> availability = new ArrayList<ArrayList<ArrayList<LocalDateTime>>>();
		ArrayList<String> status = new ArrayList<String>();
		System.out.println(roomsDB.size());
		for (int i = 0; i<roomsDB.size(); i++){
			availability.add(RoomDB.getAvailability(roomsDB.get(i).getName()));
			status.add(i, "Ledig");
			for (int j = 0; j<availability.get(i).size(); j++){
				if (checkAvailability(availability.get(i).get(j))){
					status.set(i, "Opptatt");
				}
			}
			data.add(new RoomVeiw(roomsDB.get(i).getName(), ""+roomsDB.get(i).getCapacity(), status.get(i)));
		}
		roomTable.setItems(data);
	}

	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}
	
	@Override
	public void viewRefresh() {
		data = FXCollections.observableArrayList();
		this.addMeetingCtrl = myController.getControllerForScreen(CalendarClient.ADD_MEETING_SCREEN);
		this.start = ((AddMeetingController)addMeetingCtrl).getStartTime();
		this.end = ((AddMeetingController)addMeetingCtrl).getEndTime();
		tableSetup();
	}

	public void initialize(URL location, ResourceBundle resources) {	
		roomTable.setEditable(true);
		nameColumn.setCellValueFactory(new PropertyValueFactory<RoomVeiw, String>("name"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<RoomVeiw, String>("avalibility"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<RoomVeiw, String>("capacityString"));
		
	}
}