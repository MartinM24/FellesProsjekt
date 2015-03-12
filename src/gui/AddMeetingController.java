package gui;

import calendarClient.CalendarClient;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import model.Meeting;
import model.Room;
import model.User;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMeetingController implements ControlledScreen, Initializable {
	MainController myController;
	public Room room;	
	public static final String TIME_REGEX = "([0-2])(\\d\\:)([0-5])\\d";
	@FXML TextField subjectField;
	@FXML TextField fromtimeField; 
	@FXML TextField totimeField;
	@FXML Button findroomButton;
	private ControlledScreen meetingRoomOverview;

    @Override
    public void viewRefresh() {
		this.meetingRoomOverview = myController.getControllerForScreen(CalendarClient.MEETING_ROOM_OVERVIEW_SCREEN);
		try{
			this.room = ((MeetingRoomOverviewController) meetingRoomOverview).getRoom();			
			chosenroomLabel.setText(room.getName());
		} catch (Exception e) {
			System.out.println("Ja, dette var vel ikke planlagt");
		}
    }

    @FXML DatePicker fromDatePicker;
	@FXML DatePicker toDatePicker;
	@FXML Button saveButton;
	@FXML Button cancelButton;
	@FXML Button okButton;
	@FXML Label label;
	@FXML Label chosenroomLabel;
	
	@FXML
	public void initialize() {	
		setTooltips();
	}
	
	
	public void findroomButtonClick(ActionEvent e){
		if (toDatePicker.getValue().toString().isEmpty() || fromDatePicker.getValue().toString().isEmpty() ||
				fromtimeField.getText().isEmpty() || totimeField.getText().isEmpty() || subjectField.getText().isEmpty()){
			label.setText("Ikke alle verdier er fyllt inn");
		} else {
			myController.setView(CalendarClient.MEETING_ROOM_OVERVIEW_SCREEN);
		}
		
	}
	
	public void fromtimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			validateText(fromtimeField.getText(), TIME_REGEX, fromtimeField);
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
		}

	}
	
	/**
	 * Sjekker om m�tet ender f�r det ender.
	 * @param o
	 * @param oldValue
	 * @param newValue
	 */
	public void toDatePickerChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		fromDatePicker.setStyle("");
		toDatePicker.setStyle("");
		if (!(newValue)){
			if(fromDatePicker.getValue().isAfter(toDatePicker.getValue())){
				fromDatePicker.setStyle("-fx-border-color: red");
				toDatePicker.setStyle("-fx-border-color: red");				
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
	
	public void saveButtonClick(ActionEvent e){
		if (!(toDatePicker.getValue().toString().isEmpty() || fromDatePicker.getValue().toString().isEmpty() ||
				fromtimeField.getText().isEmpty() || totimeField.getText().isEmpty() || subjectField.getText().isEmpty())){
				new Meeting(new User("Karl", "Karl", "Karl", "Karl"),
						null, "Somwhere over the rainbow",
						toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText()), 
						toLocalDateTime(toDatePicker.getValue(), totimeField.getText()),
						subjectField.getText(), -1, new ArrayList<User>());
		}
			
	}
	
	public void okButtonClick(ActionEvent e){
		if (!(toDatePicker.getValue().toString().isEmpty() || fromDatePicker.getValue().toString().isEmpty() ||
				fromtimeField.getText().isEmpty() || totimeField.getText().isEmpty() || subjectField.getText().isEmpty())){
			new Meeting(calendarClient.CalendarClient.getCurrentUser(), room
						, "Somwhere over the rainbow", getStartTime(), 
						getEndTime(),subjectField.getText(), -1, new ArrayList<User>());
				
				myController.setView(CalendarClient.CALENDAR_VIEW);
		}
			
	}
	@FXML
	public void cancelButtonClick(ActionEvent e){
		myController.setView(CalendarClient.CALENDAR_VIEW);
	}
	
	
	private void showTooltip(TextField textField) {
		textField.getTooltip().show(textField, textField.getScene().getWindow().getX()
				+ textField.getLayoutX() + textField.getWidth() + 60, 
				textField.getScene().getWindow().getY() 
				+ textField.getLayoutY() + textField.getHeight());
		textField.getTooltip().autoHideProperty().setValue(true);
	}
	
	private void setTooltips() {
		subjectField.tooltipProperty().setValue(new Tooltip("Hva er bakgrunnen til avtalen?"));
		fromDatePicker.tooltipProperty().setValue(new Tooltip("Skrive TT:MM"));
		toDatePicker.tooltipProperty().setValue(new Tooltip("Skriv TT:MM"));
	}

	private void hideAllTooltips() {
		subjectField.getTooltip().hide();
		fromDatePicker.getTooltip().hide();
		toDatePicker.getTooltip().hide();  
	}
	
	@FXML
	private void focusedChange() {
		hideAllTooltips();
	}
	
	private boolean validateText(String value, String regex, TextField textField) {
		hideAllTooltips();
		boolean isValid = value.matches(regex);
		String color = isValid ? "" : "-fx-border-color: red";
		textField.setStyle(color);
		if(!isValid) showTooltip(textField);
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
		return toLocalDateTime(toDatePicker.getValue(), totimeField.getText());
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub	
	}


}
