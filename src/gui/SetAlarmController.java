package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import model.MeetingVeiw;
import model.RoomVeiw;
import calendarClient.CalendarClient;
import dbconnection.MeetingDB;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SetAlarmController implements ControlledScreen, Initializable {
	
	public static final String TIME_REGEX = "([0-2])(\\d\\:)([0-5])\\d";
	MainController myController;
	@FXML Button saveButton;
	@FXML Button cancelButton;
	@FXML DatePicker fromDatePicker;
	@FXML TextField fromtimeField;

	private ControlledScreen meetingOverviewController;
	
	public void saveButtonClick(ActionEvent e){
		if(isValidTime()){
			MeetingVeiw meeting = ((MeetingOverviewController) meetingOverviewController).getMeetingVeiw();
			MeetingDB.setAlarm(meeting.getMeetingID(),CalendarClient.getCurrentUser() , toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText()));
			clearView(); 
			myController.setView(CalendarClient.MEETING_OVERVIEW_VIEW);
		}
	}
	

	public void cancelButtonClick(ActionEvent e){
		clearView() ;
		myController.setView(CalendarClient.MEETING_OVERVIEW_VIEW);
	}
	
	private boolean isValidTime(){
		if(validateText(fromtimeField.getText(), TIME_REGEX, fromtimeField)){	
			String[] tid1 = fromtimeField.getText().split("\\:");
			String[] tid2 = fromtimeField.getText().split("\\:");
			if (Integer.parseInt(tid1[0]) > Integer.parseInt(tid2[0]) || 
					(Integer.parseInt(tid1[0]) == Integer.parseInt(tid2[0]) &&
					Integer.parseInt(tid1[1]) > Integer.parseInt(tid2[1]))){
				fromtimeField.setStyle("-fx-border-color: red");
				fromtimeField.setStyle("-fx-border-color: red");
				return false;
			} else if (Integer.parseInt(tid1[0])>23 || Integer.parseInt(tid2[0])>23){
				fromtimeField.setStyle("-fx-border-color: red");
				fromtimeField.setStyle("-fx-border-color: red");
				return false;
			} else {
				fromtimeField.setStyle("");
				return true;
			}
			
		}
		return false;
	}
	
	public void fromtimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			try{
				isValidTime();
			} catch (Exception e) {
                e.printStackTrace();
			}
		}

	}
	
	private boolean validateText(String value, String regex, TextField textField) {
		boolean isValid = value.matches(regex);
		String color = isValid ? "" : "-fx-border-color: red";
		textField.setStyle(color);
		return isValid;
	}
	
	public LocalDateTime toLocalDateTime(LocalDate localDate, String thyme){
		String time = localDate +  " " + thyme;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime returnValue = LocalDateTime.parse(time, formatter);
		return returnValue;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
		
	}

	@Override
	public void viewRefresh() {
		this.meetingOverviewController = myController.getControllerForScreen(CalendarClient.MEETING_OVERVIEW_SCREEN);
		fromDatePicker.setValue(LocalDate.now());
	}

	@Override
	public void clearView() {
		fromDatePicker.setValue(LocalDate.now());
		fromtimeField.setText("");
		
	}

}
