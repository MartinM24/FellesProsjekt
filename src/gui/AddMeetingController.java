package gui;

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
/*	
 * This is a mainmethod. I use it to test. 
 * 
	public static void main(String[] args) {
		launch(args);
	}
	
    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("AddMeetingGUI.fxml")); 
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
*/	
	
	public void fromtimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			validateText(fromtimeField.getText(), LOGIN_REGEX, fromtimeField);
		}
	}
	
	public void totimeFieldChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue){
		if (!(newValue)){
			validateText(totimeField.getText(), LOGIN_REGEX, totimeField);			
			}
	}
	
	
	public void findroomButtonClick(ActionEvent e){
		//TODO Meetingroom list. 
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
