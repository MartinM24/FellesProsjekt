package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.LoginUser;
import calendarClient.CalendarClient;
import dbconnection.UserDB;

public class LogInController implements ControlledScreen, Initializable{
    private static final String WRONG_LOGIN_STYLE = "-fx-border-color: red";

	// Saves the parent controller for this controller
	ScreensController myController; 
	
	private static final String LOGIN_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
	
	// Fields from FXMLen
	@FXML TextField	usernameField;
	@FXML PasswordField passwordField;
	@FXML Label loginFeedback;
	@FXML Button cancelButton;
	@FXML Button okButton;
	@FXML Button newUserButton; 
	
	@FXML
	public void initialize() {	
	}
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		String username = usernameField.getText();
		// Check if username is a valid one 
		if(username.matches(LOGIN_REGEX)){
			//Check if username is registrated
			if (UserDB.checkUser(username)) {
				//Fetch user with username form DB
				LoginUser user = UserDB.getLoginUser(username);
				//Check if password is correct
				if (user.checkPassword(passwordField.getText())){
					//Correct password send to calendar screen
					myController.setScreen(CalendarClient.CALENDAR_SCREEN);
				} else {
					//Wrong password
					System.out.println("User exist, but worng password");
					wrongLoginFeedback();
				}
			} else {
				//Wrong username
				System.out.println("Username " + username + " does not exict");
				wrongLoginFeedback();
			}
		} else {
			//Wrong username format
			System.out.println("Username " + username + " is in the worng format");
			wrongLoginFeedback();
		}
	}
	
	private void wrongLoginFeedback() {
		passwordField.setStyle(WRONG_LOGIN_STYLE);
		usernameField.setStyle(WRONG_LOGIN_STYLE);
		loginFeedback.setVisible(true);
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
		passwordField.setStyle("");
		usernameField.setStyle("");
		loginFeedback.setVisible(false);
	}
	
	@FXML
	private void newUserButtonClick(ActionEvent event){
		myController.setScreen(CalendarClient.NEW_USER_SCREEN);
	} 
	
	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginFeedback.setText("Brukernavn eller passord er feil");
		loginFeedback.setTextFill(Color.RED);		
	}


}

