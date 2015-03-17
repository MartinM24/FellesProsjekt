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
import javafx.scene.input.MouseEvent;
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
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import dbconnection.GroupDB;
import dbconnection.MeetingDB;
import dbconnection.RoomDB;
import dbconnection.UserDB;

public class EditMeetingController implements ControlledScreen, Initializable {
	MainController myController;

	public Room room;
    private Meeting meeting;

	private List<String> users = new ArrayList<>();
    private List<String> participantNames = new ArrayList<>();
	private List<String> addedParticipants = new ArrayList<>();
	public boolean cameFromRoomOverview;
	public static final String TIME_REGEX = "([0-2])(\\d\\:)([0-5])\\d";
	@FXML TextField subjectField;
	@FXML TextField fromtimeField; 
	@FXML TextField totimeField;
	@FXML TextField placeField;
	@FXML TextField nOfParticipantTextField;
	@FXML Button findroomButton;
	@FXML Button removeRoomButton;
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
        fromDatePicker.setEditable(false);
    }

	
	private void gotoView(){
		cameFromRoomOverview = false;
	}

	private void disablePlace(){
		placeField.setEditable(false);
		placeField.setText("");
		placeField.disableProperty().set(true);
	}
	
	@Override
	public void viewRefresh() {
		if(chosenroomLabel.getText().trim().length()!=0){
			disablePlace();
		}
		else{
			placeField.disableProperty().set(false);
			placeField.setEditable(true);
		}
		this.meetingRoomOverview = myController.getControllerForScreen(CalendarClient.MEETING_ROOM_OVERVIEW_SCREEN);
		if(!cameFromRoomOverview){
            Set<User> usernamesInGroup = new HashSet<>();
            totimeField.setText(meeting.getEndString());
            fromtimeField.setText(meeting.getStartString());
            placeField.setText(meeting.getPlace());
            fromDatePicker.setValue(meeting.getTimeStart().toLocalDate());
            chosenroomLabel.setText("");
            if(meeting.getRoom() != null)
                chosenroomLabel.setText(meeting.getRoom().getName());
            subjectField.setText(meeting.getDescription());
            refreshLists();
            for(Group group: MeetingDB.getAllGroups(meeting)){
                String str = "Gruppe: " + group.getName();
                for(User usr: GroupDB.getAllMembers(group.getName())){
                    usernamesInGroup.add(usr);
                }
                addParticipant(str);
            }
            if (meeting.getParticipants() != null) {
                for(User usr: meeting.getParticipants()) {
                    boolean contain = false;
                    for(User gUsr: usernamesInGroup) {
                        if (usr.getUsername().equals(gUsr.getUsername())){
                            contain = true;
                        }
                    }
                    if(!contain && !usr.getUsername().equals(CalendarClient.getCurrentUser().getUsername())) {
                        String str = "Bruker: " + usr.getUsername() + ": " + usr.getFirstname() + " " + usr.getLastname();
                        addParticipant(str);
                    }
                }
            }
            cameFromRoomOverview = false;
            participantListView.setItems(FXCollections.observableArrayList(addedParticipants));
		}
	}

    private void removeName(String name) {
        for (int i = 0; i < participantNames.size(); i++) {
            if(name.equals(participantNames.get(i))){
                participantNames.remove(i);
            }
        }
    }

	
	@FXML
	public void handleMouseClick(MouseEvent e){
        String selected = participantListView.getSelectionModel().getSelectedItem();
        if(selected != null) {
            removeParticipant(selected);
            participantListView.getSelectionModel().clearSelection();
        }
	}
	
	@FXML
	public void findroomButtonClick(ActionEvent e){
		if (fromDatePicker.getValue().toString().isEmpty() ||
				fromtimeField.getText().isEmpty() ||  !validateNOfParticipant() || totimeField.getText().isEmpty() || subjectField.getText().isEmpty()){
			label.setText("Ikke alle verdier er fylt inn");
		} else {
            MeetingRoomOverviewController roomCtrl = (MeetingRoomOverviewController) myController.getControllerForScreen(CalendarClient.MEETING_ROOM_OVERVIEW_SCREEN);
            roomCtrl.setCapacity(getCapacity());
            roomCtrl.setStart(getStartTime());
            roomCtrl.setEnd(getEndTime());
            roomCtrl.setCameFromView(CalendarClient.EDIT_MEETING_VIEW);
            myController.setView(CalendarClient.MEETING_ROOM_OVERVIEW_SCREEN);
            cameFromRoomOverview = true;
		}
		
	}
	
	public void fromtimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			chosenroomLabel.setText("");
			try{
				validateText(fromtimeField.getText(), TIME_REGEX, fromtimeField);				
			} catch (Exception e) {
                e.printStackTrace();
			}
		}
	}
	

	@FXML
	public void datePickerChange(ObservableValue<Boolean> o, boolean oldValue, boolean newValue){
		chosenroomLabel.setText("");

	}
	
	public void totimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			chosenroomLabel.setText("");
			try{
				if(validateText(totimeField.getText(), TIME_REGEX, totimeField)){	
					String[] tid1 = fromtimeField.getText().split(":");
					String[] tid2 = totimeField.getText().split(":");
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
                e.printStackTrace();
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
            if(participantListView.getItems() != null){
                for(String str : participantListView.getItems()){
                    String[] parts = str.split(":");
                    if (parts[0].trim().equalsIgnoreCase("Gruppe")){
                        partakingGroups.add(new Group(parts[1].trim()));
                    }
                    else{
                        participants.add(UserDB.getUser(parts[1].trim()));
                    }
                }
            }

            room = null;
			if (chosenroomLabel.getText().trim().length() > 0)
				room = RoomDB.getRoom(chosenroomLabel.getText());


            // Update time
            if(!meeting.getTimeStart().equals(toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText()))) {
                MeetingDB.updateMeetingTimeEnd(meeting.getMeetingID(), toLocalDateTime(fromDatePicker.getValue(), totimeField.getText()));
                MeetingDB.updateMeetingTimeStart(meeting.getMeetingID(), toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText()));
            }
            if(!meeting.getTimeEnd().equals(toLocalDateTime(fromDatePicker.getValue(), totimeField.getText()))) {
                MeetingDB.updateMeetingTimeEnd(meeting.getMeetingID(), toLocalDateTime(fromDatePicker.getValue(), totimeField.getText()));
                MeetingDB.updateMeetingTimeStart(meeting.getMeetingID(), toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText()));
            }

            //Update room
            if (room == null && meeting.getRoom() != null) {
                MeetingDB.updateMeetingRoom(meeting.getMeetingID(), null);
            }
            else if(room == null && meeting.getRoom() == null) {

            }
            else if (room != null && meeting.getRoom() == null) {
                MeetingDB.updateMeetingRoom(meeting.getMeetingID(), room);
            }
            else if(!meeting.getRoom().getName().equals(room.getName())) {
                MeetingDB.updateMeetingRoom(meeting.getMeetingID(), room);
            }

            //Update place
            if(!meeting.getPlace().equals(placeField.getText())) {
                MeetingDB.updateMeetingPlace(meeting.getMeetingID(), placeField.getText());
            }

            //Update description
            if(!meeting.getDescription().equals(subjectField.getText())) {
                MeetingDB.updateMeetingDescription(meeting.getMeetingID(), subjectField.getText());
            }

            //Update noOfParticipant
            if(meeting.getNOfParticipantSet() != getNOfParticipants() ) {
                MeetingDB.updateMeetingNofParticipants(meeting.getMeetingID(), getNOfParticipants());
            }

            List<User> part = meeting.getParticipants();

            //Update Group
            for (Group g: MeetingDB.getAllGroups(meeting)) {
                MeetingDB.removeGroup(g, meeting);
                for(User gUsr: GroupDB.getAllMembers(g.getName())){
                    for (int i = 0; i < part.size() ; i++) {
                        User usr = part.get(i);
                        if(usr.getUsername().equals(gUsr.getUsername())) {
                            part.remove(i);
                            i--;
                        }
                    }
                }
            }

			for(Group group : partakingGroups){
				MeetingDB.addGroup(group, meeting);
                for(User gUsr: GroupDB.getAllMembers(group.getName())){
                    part.add(gUsr);
                    participants.add(gUsr);
                }
            }


            //Update participant
            if (part != null) {
                for (User p : part) {
                    String old = p.getUsername();
                    boolean removed = true;
                    for (User usr : participants) {
                        String ny = usr.getUsername().trim();
                        if (old.equals(ny)) {
                            removed = false;
                        }
                    }
                    if (removed) {
                        MeetingDB.deleteParticipant(meeting.getMeetingID(), p);
                    }
                }

                for (User usr : participants) {
                    String ny = usr.getUsername().trim();
                    boolean added = true;
                    for (User p : part) {
                        String old = p.getUsername();
                        if (old.equals(ny)) {
                            added = false;
                        }
                    }
                    if (added) {
                        MeetingDB.addParticipant(meeting, UserDB.getUser(ny), 0);
                    }
                }
            } else {
                 for (String ny: addedParticipants) {
                     MeetingDB.addParticipant(meeting, UserDB.getUser(ny), 0);
                 }
            }
            meeting = null;
            clearView();
			myController.setView(CalendarClient.CALENDAR_VIEW);
        }
	}
	
	@FXML
	public void cancelButtonClick(ActionEvent e){
		clearView();
		myController.setView(CalendarClient.CALENDAR_VIEW);
        meeting = null;
	}
	
	@FXML
	public void removeRoomButtonClick(ActionEvent e){
		chosenroomLabel.setText("");
		placeField.setEditable(true);
		placeField.disableProperty().set(false);
	}
	
	public int getCapacity(){
		int i = getNOfParticipants();
		if(i==-1){
			Set<String> userNames = new HashSet<>();
			userNames.add(CalendarClient.getCurrentUser().getUsername());
            if(participantListView.getItems() != null) {
                for(String str : participantListView.getItems()){
                    String[] parts = str.split(":");
                    if (parts[0].trim().equalsIgnoreCase("Gruppe")){
                        for(User user : GroupDB.getAllMembers(parts[1].trim())){
                            userNames.add(user.getUsername());
                        }
                    }
                    else{
                        userNames.add(parts[1].trim());
                    }
                }
                return userNames.size();
            }
			return userNames.size();
		}
		return i;
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
        List<String> groups = GroupDB.getallGroups();
		List<LoginUser> userList = UserDB.getAllUsers();
		for(int i = 0 ; i < userList.size() ; i++){
			userList.get(i).getUsername();

			if(!userList.get(i).getUsername().equals(CalendarClient.getCurrentUser().getUsername())){
				users.add(userList.get(i).getUsername());
			}
		}
		for(String str : groups){
			participantNames.add("Gruppe: "+str);
		}
		for(String str : users){
			participantNames.add( "Bruker: " + str + ": " + UserDB.getUser(str).getFirstname() + " " + UserDB.getUser(str).getLastname());
		}
		participantComboBox.setItems(FXCollections.observableArrayList(participantNames));
	}

	private void addParticipant(String name){
		if(name.trim().length() > 0) {
            participantNames.remove(name);
            addedParticipants.add(name);
            participantListView.setItems(null);
            participantListView.setItems(FXCollections.observableArrayList(addedParticipants));
            participantComboBox.setItems(FXCollections.observableArrayList(participantNames));

        }
	}
	
	private void removeParticipant(String name){
        if(name.trim().length() > 0) {
            participantNames.add(name);
            addedParticipants.remove(name);
            participantListView.setItems(null);
            participantListView.setItems(FXCollections.observableArrayList(addedParticipants));
            participantComboBox.setItems(FXCollections.observableArrayList(participantNames));
        }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        fromDatePicker.editableProperty().set(false);
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
	            if(selected!=null){
					addParticipant(selected);
				}
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


    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    @Override
    public void clearView() {
    	room = null;
    	users.clear();
    	participantNames.clear();
    	addedParticipants.clear();
    	cameFromRoomOverview = false;
    	subjectField.setText("");
    	fromtimeField.setText(""); 
    	totimeField.setText("");
    	placeField.setText("");
    	nOfParticipantTextField.setText("");
    	subjectField.setStyle("");
    	fromtimeField.setStyle(""); 
    	totimeField.setStyle("");
    	placeField.setStyle("");
    	nOfParticipantTextField.setStyle("");
    	participantComboBox.setItems(null);
    	participantListView.setItems(null);
        participantListView.getSelectionModel().clearSelection();

        fromDatePicker.setValue(LocalDate.now());
        label.setText("");
    	chosenroomLabel.setText("");
    }
}
