package newuser;

import java.util.ArrayList;
import dbconnection.ReadUsersDB;
import dbconnection.WriteUserDB;
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
	
	ArrayList<ArrayList<String>> brukere = ReadUsersDB.GetSkjema(); //f�rste som begynner. 
	@FXML TextField FirstnameTextField;
	@FXML TextField	LastnameTextField;
	@FXML TextField	UsernameTextField;
	@FXML TextField	MailTextField;
	@FXML PasswordField PasswordPasswordField1;
	@FXML PasswordField PasswordPasswordField2;
	@FXML Button OKButton;
	@FXML Button CancelButton;
	
	
	//main metoden.
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override //Start �pner veiwet
	public void start(Stage stage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("NewUserGUI.fxml"));
	    stage.setTitle("OpprettBruker");
	    stage.setScene(new Scene(root, 900, 600)); //exsempelst�rellser
	    stage.show();
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
		//SJekker f�rst om alle felter er fylt ut for � s� sende skjemaet. 
		//Ingen logikk her fordi det skal skje p� hver av feltene individuelt. 
		
		if (FirstnameTextField.getText().isEmpty() ||
				LastnameTextField.getText().isEmpty() || 
				PasswordPasswordField1.getText().isEmpty() || 
				MailTextField.getText().isEmpty() ||
				UsernameTextField.getText().isEmpty()){
			System.out.println("one or more empty fields");
		} else {
			WriteUserDB.sendSjkema(FirstnameTextField.getText(),
				LastnameTextField.getText(), 
				PasswordPasswordField1.getText(), 
				MailTextField.getText(), 
				UsernameTextField.getText());
		}
	}
	public void CancelButtonClick(ActionEvent e){
		//tom fordi jeg ikke har skrivet noe logikk her
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
		for (int i = 0; i<brukere.size();i++){
			if (UsernameTextField.getText().equals(brukere.get(i).get(6))){
				foo = true;
			}
		if (foo){
			System.out.println("Username is taken");
		}
		}
	}
	
	private void validatePassword() {
		//sammenligner passord. den er litt dum.
		if(!(PasswordPasswordField1.getText().equals(PasswordPasswordField2.getText()))){
			System.out.println("Passwords don't match");
		}
	}
	
}

