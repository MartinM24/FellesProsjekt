package gui;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Meeting;
import model.User;
import dbconnection.MeetingDB;
import sun.launcher.resources.launcher;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class AddMeetingController implements ControlledScreen {
	ScreensController myController;
	
	public static final String LOGIN_REGEX = "([0-2])(\\d\\:)([0-5])\\d";
	@FXML TextField subjectField;
	@FXML TextField fromtimeField; 
	@FXML TextField totimeField;
	@FXML Button findroomButton;
	@FXML DatePicker fromDatePicker;
	@FXML DatePicker toDatePicker;
	
	
	@FXML
	public void initialize() {	
		setTooltips();
	}
	
	public void fromtimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			validateText(fromtimeField.getText(), LOGIN_REGEX, fromtimeField);
		}
	}
	
	public void totimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			if(validateText(totimeField.getText(), LOGIN_REGEX, totimeField)){	
				String[] tid1 = fromtimeField.getText().split("\\:");
				String[] tid2 = totimeField.getText().split("\\:");
				if (Integer.parseInt(tid1[0]) > Integer.parseInt(tid2[0]) || 
						(Integer.parseInt(tid1[0]) == Integer.parseInt(tid2[0]) &&
						Integer.parseInt(tid1[1]) > Integer.parseInt(tid2[1]))){
					totimeField.setStyle("-fx-background-color: red");
					fromtimeField.setStyle("-fx-background-color: red");
			} else {
				fromtimeField.setStyle("");
			}
				
			}
		}

	}
	
	public void toDatePickerChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		fromDatePicker.setStyle("");
		toDatePicker.setStyle("");
		if (!(newValue)){
			if(fromDatePicker.getValue().isAfter(toDatePicker.getValue())){
				fromDatePicker.setStyle("-fx-background-color: red");
				toDatePicker.setStyle("-fx-background-color: red");				
			}
		}
	}

	public void findroomButtonClick(ActionEvent e){
		//TODO Meetingroom list. 
	}
	
	public LocalDateTime toLocalDateTime(LocalDate localDate, String thyme){
		String time = localDate +  " " + thyme;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime returnValue = LocalDateTime.parse(time, formatter);
		return returnValue;
	}
	
	public void saveButtonClick(ActionEvent e){
		//TODO add meeting to database.		
		new Meeting(new User("Karl", "Karl", "Karl", "Karl"),
				null, "Somwhere over the rainbow",
				toLocalDateTime(fromDatePicker.getValue(), fromtimeField.getText()), 
				toLocalDateTime(toDatePicker.getValue(), totimeField.getText()),
				subjectField.getText(), -1, new ArrayList<User>());
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
		String color = isValid ? "" : "-fx-background-color: red";
		textField.setStyle(color);
		if(!isValid) showTooltip(textField);
		return isValid;
	}
	
	
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}



}
