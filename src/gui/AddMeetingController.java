package gui;

import calendarClient.CalendarClient;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import model.Group;
import model.LoginUser;
import model.Meeting;
import model.Room;
import model.User;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dbconnection.GroupDB;
import dbconnection.MeetingDB;
import dbconnection.RoomDB;
import dbconnection.UserDB;

public class AddMeetingController implements ControlledScreen, Initializable {
	MainController myController;
	public Room room;
	private List<String> users = new ArrayList<String>();
	private List<String> groups = new ArrayList<String>();
	private List<String> participantNames = new ArrayList<String>();
	public boolean cameFromRoomOverview;
	public static final String TIME_REGEX = "([0-2])(\\d\\:)([0-5])\\d";
	@FXML TextField subjectField;
	@FXML TextField fromtimeField; 
	@FXML TextField totimeField;
	@FXML TextField placeField;
	@FXML TextField nOfParticipantTextField;
	@FXML Button findroomButton;
	@FXML ComboBox<String> participantComboBox;
	@FXML ListView<String> participantListView;
	
	private ControlledScreen meetingRoomOverview;


    @FXML DatePicker fromDatePicker;
	@FXML Button cancelButton;
	@FXML Button okButton;
	@FXML Label label;
	@FXML Label chosenroomLabel;
	
	@FXML
	public void initialize() {	
	}

	
	private void gotoView(){
		cameFromRoomOverview = false;
	}

	
	@Override
	public void viewRefresh() {
		this.meetingRoomOverview = myController.getControllerForScreen(CalendarClient.MEETING_ROOM_OVERVIEW_SCREEN);
		if(!cameFromRoomOverview){
			refreshLists();
		}
	}
	
	@FXML
	public void findroomButtonClick(ActionEvent e){
		if (fromDatePicker.getValue().toString().isEmpty() ||
				fromtimeField.getText().isEmpty() || totimeField.getText().isEmpty() || subjectField.getText().isEmpty()){
			label.setText("Ikke alle verdier er fyllt inn");
		} else {
			myController.setView(CalendarClient.MEETING_ROOM_OVERVIEW_SCREEN);
		}
		
	}
	
	public void fromtimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			try{
				validateText(fromtimeField.getText(), TIME_REGEX, fromtimeField);				
			} catch (Exception e) {
				System.out.println("Check is not able to be made");
			}
		}
	}
	
	/**
	 * Sjekker om verdiene skrevet inn i 
	 * fromtimeField og totimeField er skrevet
	 * p� riktig m�te
	 * @param o
	 * @param oldValue
	 * @param newValue
	 */
	
	public void totimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			try{
				if(validateText(totimeField.getText(), TIME_REGEX, totimeField)){	
					String[] tid1 = fromtimeField.getText().split("\\:");
					String[] tid2 = totimeField.getText().split("\\:");
					if (Integer.parseInt(tid1[0]) > Integer.parseInt(tid2[0]) || 
							(Integer.parseInt(tid1[0]) == Integer.parseInt(tid2[0]) &&
							Integer.parseInt(tid1[1]) > Integer.parseInt(tid2[1]))){
						totimeField.setStyle("-fx-border-color: red");
						fromtimeField.setStyle("-fx-border-color: red");
					} else {
						fromtimeField.setStyle("");
					}
					
				}
			} catch (Exception e) {
				System.out.println("Check is not able to be made");
			}
		}

	}
	
	
	/**
	 * Gj�r om fra localdate og string (HH:MM) til localdatetime
	 * @param localDate
	 * @param thyme
	 * @return
	 */
	public LocalDateTime toLocalDateTime(LocalDate localDate, String thyme){
		String time = localDate +  " " + thyme;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime returnValue = LocalDateTime.parse(time, formatter);
		return returnValue;
	}
	
	private boolean validateNOfParticipant(){
		if(nOfParticipantTextField.getText().trim().length()==0){
			return true;
		}
		try{
			int number = Integer.parseInt(nOfParticipantTextField.getText());
			return number>0;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	private int getNOfParticipants(){
		if(nOfParticipantTextField.getText().trim().length()==0){
			return -1;
		}
		return Integer.parseInt(nOfParticipantTextField.getText());
		
	}
	
	public void okButtonClick(ActionEvent e){
		//
		//Valider antall participants og place
		//
		if (!(fromDatePicker.getValue().toString().isEmpty() ||
				fromtimeField.getText().isEmpty() || totimeField.getText().isEmpty() || !validateNOfParticipant() ||subjectField.getText().isEmpty())){

			List<User> participants = new ArrayList<User>();
			List<Group> partakingGroups = new ArrayList<Group>();
			for(String str : participantListView.getItems()){
				String[] parts = str.split(":", 2);
				if (parts[0].trim().equalsIgnoreCase("Gruppe")){
					partakingGroups.add(new Group(parts[1].trim()));
				}
				else{
					participants.add(UserDB.getUser(parts[1].trim()));
				}
			}
			room = null; 
			if (chosenroomLabel.getText().trim().length() > 0)
				room = RoomDB.getRoom(chosenroomLabel.getText());
		
			
			Meeting meeting = new Meeting(CalendarClient.getCurrentUser(),
						room , placeField.getText(),
						toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText()), 
						toLocalDateTime(fromDatePicker.getValue(), totimeField.getText()),
						subjectField.getText(), getNOfParticipants(), participants);
			
			for(Group group : partakingGroups){
				MeetingDB.addGroup(group, meeting);
			}
			myController.setView(CalendarClient.CALENDAR_VIEW);
		}
		System.out.println("If not true in AddMeetingController");
			
	}
	
	@FXML
	public void cancelButtonClick(ActionEvent e){
		myController.setView(CalendarClient.CALENDAR_VIEW);
	}
	
	
	
	private boolean validateText(String value, String regex, TextField textField) {
		boolean isValid = value.matches(regex);
		String color = isValid ? "" : "-fx-border-color: red";
		textField.setStyle(color);
		return isValid;
	}
	
	
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}
	
	
	public LocalDateTime getStartTime(){
		return toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText());
	}
	
	public LocalDateTime getEndTime(){
		return toLocalDateTime(fromDatePicker.getValue(), totimeField.getText());
	}
	
	private void refreshLists(){
		groups = GroupDB.getallGroups();
		List<LoginUser> userList = UserDB.getAllUsers();
		for(int i = 0 ; i < userList.size() ; i++){
			System.out.println(i);
			System.out.println(userList.get(i));
			userList.get(i).getUsername();
					
			if(userList.get(i).getUsername().equals(CalendarClient.getCurrentUser().getUsername())){
				groups.remove(i);
			}
			else{
				users.add(userList.get(i).getUsername());
			}
		}
		for(String str : groups){
			participantNames.add("Gruppe: "+str);
		}
		for(String str : users){
			participantNames.add("Bruker: "+str);
		}
		participantComboBox.setItems(FXCollections.observableArrayList(participantNames));
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cameFromRoomOverview = false;
		participantComboBox.setEditable(true);
		participantComboBox.getSelectionModel().clearSelection();
		//refreshLists();
		participantComboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
	          @Override
	          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            final TextField editor = participantComboBox.getEditor();
	            final String selected = participantComboBox.getSelectionModel().getSelectedItem();
	            if (selected == null || !selected.equals(editor.getText())) {
	              filterItems(newValue, participantComboBox, participantNames);
	              participantComboBox.show();
	            }
	          }
	        });
			
		
			
	    participantComboBox.setOnAction(new EventHandler<ActionEvent>() {
	      @Override
	      public void handle(ActionEvent event) {
	        // Reset so all options are available:
	        Platform.runLater(new Runnable() {
	          @Override
	          public void run() {
	            String selected = participantComboBox.getSelectionModel().getSelectedItem();
	            if (participantComboBox.getItems().size() < participantNames.size()) {
	              participantComboBox.setItems(FXCollections.observableArrayList(participantNames));
	              String newSelected = participantComboBox.getSelectionModel()
	                  .getSelectedItem();
	              if (newSelected == null || !newSelected.equals(selected)) {
	                participantComboBox.getSelectionModel().select(selected);
	              }
	            }
	          }
	        });
	      }
	    });	
	}
	
	private <T> void filterItems(String filter, ComboBox<T> comboBox,List<T> items) {
		List<T> filteredItems = new ArrayList<>();
		for (T item : items) {
			if (item.toString().toLowerCase().startsWith(filter.toLowerCase())) {
				filteredItems.add(item);
			}
		}
		comboBox.setItems(FXCollections.observableArrayList(filteredItems));
	}


}
