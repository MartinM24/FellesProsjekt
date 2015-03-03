package gui;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import model.LoginUser;
import calendarClient.CalendarClient;
import dbconnection.UserDB;

public class LogInController implements ControlledScreen{
    // Saves the parent controller for this controller
	ScreensController myController; 
	
	private static final String LOGIN_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
	
	// Fields from FXMLen
	@FXML TextField	usernameField;
	@FXML PasswordField passwordField;
	@FXML Button cancelButton;
	@FXML Button okButton;
	@FXML Button newUserButton; 
	
	@FXML
	public void initialize() {	
		setTooltips();
	}
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		LoginUser user = null;
		if(validateText(usernameField.getText(), LOGIN_REGEX , usernameField )) {
			try{
				user = UserDB.getLoginUser(usernameField.getText());
			} catch(Exception e){
				System.out.println("Can't fetch user with username " + usernameField.getText() + " in the database");
			}
			if(user != null){
				if (user.checkPassword(passwordField.getText())){
					myController.setScreen(CalendarClient.CALENDAR_SCREEN);
					System.out.println("You have loged in");
				} else {
					System.out.println("Password is wrong");
					wrongLoginFeedback();
				}				
			} else {
				wrongLoginFeedback();
			}
		}
	}
	
	private void wrongLoginFeedback() {
		passwordField.tooltipProperty().setValue(new Tooltip("Passordet eller bruker navnet er feil"));
		showTooltip(passwordField);
		passwordField.setStyle("-fx-background-color: red");
		usernameField.setStyle("-fx-background-color: red");
	}

	@FXML public void cancelButtonClick(ActionEvent event) {
		//TODO check if Platform.exit is the right method. I did get:
		//Java has been detached already, but someone is still trying to use it at -[GlassRunnable run]:/HUDSON/workspace/8u25/label/macosx-universal-30/rt/modules/graphics/src/main/native-glass/mac/GlassApplication.m:92
		Platform.exit();
	}
	
	public void passwordFocusedChange(ObservableValue<Boolean> o,  boolean oldValue, boolean newValue) {
		resetGraphic();
	}
	
	public void usernameFocusedChange(ObservableValue<Boolean> o, boolean oldValue, boolean newValue) {
		resetGraphic();
	}
	
	private void resetGraphic(){
		hideAllTooltips();
		setTooltips();
		passwordField.setStyle("");
		usernameField.setStyle("");
		
	}
	
	@FXML
	private void focusedChange() {
		hideAllTooltips();
	}
	
	@FXML
	private void newUserButtonClick(ActionEvent event){
		myController.setScreen(CalendarClient.NEW_USER_SCREEN);
	} 
	
	
	private boolean validateText(String value, String regex, TextField textField) {
		hideAllTooltips();
		boolean isValid = value.matches(regex);
		String color = isValid ? "" : "-fx-background-color: red";
		textField.setStyle(color);
		if(!isValid) showTooltip(textField);
		return isValid;
	}
	
	private void showTooltip(TextField textField) {
		textField.getTooltip().show(textField, textField.getScene().getWindow().getX()
				+ textField.getLayoutX() + textField.getWidth() + 60, 
				textField.getScene().getWindow().getY() 
				+ textField.getLayoutY() + textField.getHeight());
		textField.getTooltip().autoHideProperty().setValue(true);
	}
	
	private void setTooltips() {
		usernameField.tooltipProperty().setValue(new Tooltip("Bokstaver, tall og _- er  tillat. Lengden er mellom 3 og 16 tegn"));
		passwordField.tooltipProperty().setValue(new Tooltip("Her kan du ikke bruke æ, ø, å"));
		
	}

	private void hideAllTooltips() {
		usernameField.getTooltip().hide(); 
		passwordField.getTooltip().hide();  
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}


}

