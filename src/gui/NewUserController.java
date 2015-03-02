package gui;

import java.util.ArrayList;

import model.LoginUser;
import model.Password;
import model.User;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserController extends Application {
	
	
	@FXML TextField FirstnameTextField;
	@FXML TextField	LastnameTextField;
	@FXML TextField	UsernameTextField;
	@FXML TextField	MailTextField;
	@FXML PasswordField PasswordPasswordField1;
	@FXML PasswordField PasswordPasswordField2;
	@FXML Button OKButton;
	@FXML Button CancelButton;
	ArrayList<LoginUser> users = UserDB.getAllUsers();
	
	@Override 
	public void start(Stage stage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("NewUserGUI.fxml"));
	    stage.setTitle("OpprettBruker");
	    stage.setScene(new Scene(root, 900, 600)); 
	    stage.show();
	    }

	public static void main(String[] args) {
		launch(args);
	}
	
	public void FirstnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//tom fordi jeg ikke har skrivet noe logikk her jaja
	}
	public void LastnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//tom fordi jeg ikke har skrivet noe logikk her
	}
	public void MailFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//tom fordi jeg ikke har skrivet noe logikk her
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
		//sjekker om brukernavnet du har valgt er tatt. 
		
		boolean foo = false;
		for (int i = 0; i<users.size();i++){
			if (UsernameTextField.getText().equals(users.get(i).getUsername())){
				foo = true;
			}
		if (foo){
			System.out.println("Username is taken");
		}
		}
	} 
	
	private void validatePassword() {
		if(!(PasswordPasswordField1.getText().equals(PasswordPasswordField2.getText()))){
			System.out.println("Passwords don't match");
		}
	}
}

