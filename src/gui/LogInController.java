package gui;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import dbconnection.DatabaseConnection;
import dbconnection.UserDB;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import model.LoginUser;
import model.User;

public class LogInController implements ControlledScreen{
    // Saves the parent controller for this controller
	ScreensController myController; 
	
	private static final String loginRegex = "\\w*";
	
	// Fields from FXMLen
	@FXML TextField	usernameField;
	@FXML TextField passwordField;
	@FXML Button cancelButton;
	@FXML Button okButton;
	@FXML Hyperlink fpHyperlink;
	@FXML Button newUserButton; 
	
	public void start(Stage stage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("GUI Logg inn.fxml"));
	    stage.setTitle("Logg Inn");
	    stage.setScene(new Scene(root, 900, 600)); //eksempelst�rrelser
	    stage.show();
	    }
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		LoginUser user = null;
		System.out.println("Harry er kul.");
		if(validateText(usernameField.getText(), loginRegex , usernameField )) {
			try{
				
				user = UserDB.getLoginUser(usernameField.getText());
				System.out.println("Harry elsker Rakel");
			} catch(Exception e){
				//TODO grafical stuff
				System.out.println("noe skjedde, det var ikke bra");
				e.printStackTrace();
			}
			
			
			if (user.checkPassword(passwordField.getText())){
				//TODO log inn.
			} else {
				//TODO grafiske ting
				System.out.println("Dumme mennekse, passordet ditt er feil");
			}
			
		}
	}

//	@FXML
//	public void initialize() {	
//	}
	
	//public void usernameFocusedChange()
	
	
	public void UsernameTextChange(ObservableValue<String> o,  String oldValue, String newValue) {
		validateText(newValue, loginRegex, usernameField);
	}
	
	public void passwordTextChange(ObservableValue<String> o,  String oldValue, String newValue) {
		
	}
	@FXML
	private void focusedChange() {
		hideAllTooltips();
	}
	
/*	@FXML
	private void newUserButtonClick(ActionEvent event){
		Stage stage = new Stage();
        try {
        	//TODO FIKS!!!
        } catch (IOException e) {
            e.printStackTrace();
	}
	} */
	
	private void hideAllTooltips() {
		
		usernameField.getTooltip().hide(); 
		passwordField.getTooltip().hide();  
	}
	
	private boolean validateText(String value, String regex, TextField textField) {
		hideAllTooltips();
		boolean isValid = value.matches(regex);
		if (!isValid && value.length() == 0) {
			isValid = true;
			}
		String color = isValid ? "" : "-fx-background-color: red";
		textField.setStyle(color);
		if(!isValid) showTooltip(textField);
		return isValid;
	}
	
	private void showTooltip(TextField textField) {
		textField.getTooltip().show(textField, textField.getScene().getWindow().getX()
				+ textField.getLayoutX() + textField.getWidth() + 5, 
				textField.getScene().getWindow().getY() 
				+ textField.getLayoutY() + textField.getHeight());
		textField.getTooltip().autoHideProperty().setValue(true);
	}
	
	private void setTooltips() {
		usernameField.tooltipProperty().setValue(new Tooltip("Her kan du skrive hva du vil (fritekst)"));
		passwordField.tooltipProperty().setValue(new Tooltip("Her kan du ikke bruke �, �, �"));
		
	}

	@Override
	/**
	 * Sets parent controller
	 */
	public void setScreenParent(ScreensController screenPage) {
		// TODO Auto-generated method stub
		setTooltips();
		this.myController = screenPage;
	}
}

