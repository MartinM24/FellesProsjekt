package gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import calendarClient.CalendarClient;
import model.Meeting;
import model.User;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class AddMeetingController implements ControlledScreen {
	MainController myController;
	
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
			if(validateText(totimeField.getText(), LOGIN_REGEX, totimeField)){	
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

	public void findroomButtonClick(ActionEvent e){
		//TODO Meetingroom list. 
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
				
				myController.setView(CalendarClient.CALENDAR_VIEW);
		}
			
	}
	
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



}
