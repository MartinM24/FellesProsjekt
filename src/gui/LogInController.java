package gui;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import dbconnection.DatabaseConnection;
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

public class LogInController extends Application {
	
	private static final String loginRegex = "[[a-zA-Z������]+[\\-\\s]*[a-zA-Z������]+]+";

	
	
	@FXML TextField	usernameField;
	@FXML PasswordField passwordField;
	@FXML Button cancelButton;
	@FXML Button okButton;
	@FXML Hyperlink fpHyperlink;
	@FXML Button newUserButton;
	ArrayList<LoginUser> users = DatabaseConnection.getAllUsers(); //TODO Find better method. 
	
	public void start(Stage stage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("GUI Logg inn.fxml"));
	    stage.setTitle("Logg Inn");
	    stage.setScene(new Scene(root, 900, 600)); //eksempelst�rrelser
	    stage.show();
	    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		if(validateText(usernameField.getText(), loginRegex , usernameField ) && validateText(passwordField.getText(), loginRegex, passwordField)) {
			boolean foo = false;
			int userID = 0;
			for (int i = 0; i < users.size(); i++){
				if (users.get(i).getUsername().equals(usernameField.getText())){
					foo = true;
					userID = i;
				}
			}
			if (foo){
				LoginUser user = users.get(userID);
				//Check some info about user. 
			
			} else {
				System.out.println("User does not exist");				
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
}

