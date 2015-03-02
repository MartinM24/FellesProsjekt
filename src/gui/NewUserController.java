package gui;

import model.LoginUser;
import model.Password;
import dbconnection.UserDB;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserController implements ControlledScreen {
	ScreensController myController; 
	
	@FXML TextField FirstnameTextField;
	@FXML TextField	LastnameTextField;
	@FXML TextField	UsernameTextField;
	@FXML TextField	MailTextField;
	@FXML PasswordField PasswordPasswordField1;
	@FXML PasswordField PasswordPasswordField2;
	@FXML Button OKButton;
	@FXML Button CancelButton;

	
	public void FirstnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//tom 
	}
	public void LastnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//tom 
	}
	public void MailFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//tom 
	}
	public void OKButtonClick(ActionEvent e) throws InstantiationException, IllegalAccessException{
		
		if (FirstnameTextField.getText().isEmpty() ||
				LastnameTextField.getText().isEmpty() || 
				PasswordPasswordField1.getText().isEmpty() || 
				MailTextField.getText().isEmpty() ||
				UsernameTextField.getText().isEmpty()){
			System.out.println("one or more empty fields");
		} else { 
			Password pass = new Password(PasswordPasswordField1.getText());
			UserDB.addUser(new LoginUser(UsernameTextField.getText(), FirstnameTextField.getText(), LastnameTextField.getText(), MailTextField.getText(), pass.getSalt(), pass.getHash()));
		}
	}
	public void CancelButtonClick(ActionEvent e){
		//Move user back to userLogin. 
	}
	
	public void Password1FocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		validatePassword();
	}

	public void Password2FocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		validatePassword();
	}
	
	public void UsernameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		if (UserDB.getUser(UsernameTextField.getText()) == null){
			System.out.println("Brukernavnet finnes ikke");
			//TODO Grafisk. 
		} else {
			System.out.println("Brukernavnet finnes");

		}
	} 
	
	private void validatePassword() {
		if(!(PasswordPasswordField1.getText().equals(PasswordPasswordField2.getText()))){
			//TODO Grafical feedback
			System.out.println("Passwords don't match");
		}
	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage;
	}
}

