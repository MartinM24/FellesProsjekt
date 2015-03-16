package gui;

import calendarClient.CalendarClient;
import dbconnection.UserDB;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.LoginUser;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements ControlledScreen, Initializable{
    private static final String WRONG_LOGIN_STYLE = "-fx-border-color: red";

	// Saves the parent controller for this controller
	MainController myController;
	
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

    @Override
    public void viewRefresh() {

    }
    
    public void handleKeyPressed(KeyEvent event){
    	if(event.getCode() == KeyCode.ENTER) {
        	okButtonClick(new ActionEvent());
        } 
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
					CalendarClient.setCurrentUser(user);
					clearView();
					myController.setView(CalendarClient.CALENDAR_VIEW);

				} else {
					//Wrong password
					wrongLoginFeedback();
				}
			} else {
				//Wrong username
				wrongLoginFeedback();
			}
		} else {
			//Wrong username format
			wrongLoginFeedback();
		}
	}
	
	private void wrongLoginFeedback() {
		passwordField.setStyle(WRONG_LOGIN_STYLE);
		usernameField.setStyle(WRONG_LOGIN_STYLE);
		loginFeedback.setVisible(true);
	}

	@FXML public void cancelButtonClick(ActionEvent event) {
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
		clearView();
		myController.setView(CalendarClient.NEW_USER_VIEW);
	} 
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginFeedback.setText("Brukernavn eller passord er feil");
		loginFeedback.setTextFill(Color.RED);		
	}

    @Override
    public void clearView() {
    	usernameField.setText("");;
    	passwordField.setText("");;
    	loginFeedback.setText("");
    }
}

