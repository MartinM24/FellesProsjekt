package gui;

import calendarClient.CalendarClient;
import dbconnection.UserDB;
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
import model.Password;

import java.net.URL;
import java.util.ResourceBundle;

public class NewUserController implements ControlledScreen, Initializable {
	private static final String NOT_VALID_FIELD_STYLE = "-fx-border-color: red";
	private static final String MAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String USERNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
	private static final String NAME_REGEX = "^[\\p{L} .'-]+$";
	MainController myController;
	
	//Fields
	@FXML TextField FirstnameTextField;
	@FXML TextField	LastnameTextField;
	@FXML TextField	UsernameTextField;
	@FXML PasswordField PasswordField1;
	@FXML PasswordField PasswordField2;
	@FXML TextField	MailTextField;
	
	//Status- or feedback labels 
	@FXML Label firstnameStatus;
	@FXML Label lastnameStatus;
	@FXML Label usernameStatus;
	@FXML Label password1Status;
	@FXML Label password2Status;
	@FXML Label mailStatus;
	@FXML Label formStatus;
	
	//Buttons
	@FXML Button OKButton;
	@FXML Button CancelButton;
	
	// Should not have any fatal errors, but it lacks validation

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//sets wrapTextProperty for all status labels to true
		usernameStatus.wrapTextProperty().set(true);
		firstnameStatus.wrapTextProperty().set(true);
		lastnameStatus.wrapTextProperty().set(true);
		password1Status.wrapTextProperty().set(true);
		password2Status.wrapTextProperty().set(true);
		mailStatus.wrapTextProperty().set(true);
		formStatus.wrapTextProperty().set(true);
	}
	
	public void FirstnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		FirstnameTextField.setText(FirstnameTextField.getText().trim());
		if (!newValue) {
			//check if firstname is valid set graphic accordingly
			isValidName(FirstnameTextField, firstnameStatus);
		} else {
			// remove graphic 
			firstnameStatus.setVisible(false);
			FirstnameTextField.setStyle("");	
		}
	}
	
	public void LastnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		LastnameTextField.setText(LastnameTextField.getText().trim());
		if (!newValue) {
			//check if firstname is valid set graphic accordingly
			isValidName(LastnameTextField, lastnameStatus);
		} else {
			// remove graphic 
			lastnameStatus.setVisible(false);
			LastnameTextField.setStyle("");	
		}
	}
	
	/**
	 * Checks if nameFields name isValid and sets graphic accordingly 
	 * @param nameField with the text that is going to be validated
	 * @param nameStatus the status label that is going to inform user what is wrong
	 * @return true if name isValid
	 */
	private boolean isValidName(TextField nameField, Label nameStatus) {
		//Checks first if name is long enough
		if (nameField.getText().length() < 2) {
			nameField.setStyle(NOT_VALID_FIELD_STYLE);
			nameStatus.setText("Navnet må være minst 2 tegn langt");
			nameStatus.setTextFill(Color.RED);
			nameStatus.setVisible(true);
			return false;
		}
		// Set first letter to be upper case
		String value = nameField.getText().trim();
		char firstletter = Character.toUpperCase(value.charAt(0));
		nameField.setText(firstletter + value.substring(1));
		// Check nameFild against regex
		if(!nameField.getText().matches(NAME_REGEX)){
			nameField.setStyle(NOT_VALID_FIELD_STYLE);
			nameStatus.setText("Du kan kun ha bokstaver og bindestrek i navnet");
			nameStatus.setTextFill(Color.RED);
			nameStatus.setVisible(true);
			return false; 
		}
		return true;
	}
	
	public void MailFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		MailTextField.setText(MailTextField.getText().trim());
		if(!newValue) {
			isValidEmail();
		} else {
			MailTextField.setStyle("");
			mailStatus.setVisible(false);
		}
	}
	
	
	
	private boolean isValidEmail() {
		if (!MailTextField.getText().matches(MAIL_REGEX)) {
			mailStatus.setText("Email addressen er ikke gyldig");
			mailStatus.setTextFill(Color.RED);
			mailStatus.setVisible(true);
			MailTextField.setStyle(NOT_VALID_FIELD_STYLE);
			return false;
		}
		return true; 	
	}

	public void OKButtonClick(ActionEvent e) throws InstantiationException, IllegalAccessException{
		// checks if there is some text in all fields
		// final validation of all fields before adding new user to the database
		if(isValidForm()){
			Password pass = new Password(PasswordField1.getText());
			boolean beenAdded = UserDB.addUser(new LoginUser(UsernameTextField.getText(), FirstnameTextField.getText(), LastnameTextField.getText(), MailTextField.getText(), pass.getSalt(), pass.getHash()));
			if (beenAdded) {
				emptyForm();
				clearView();
				myController.setView(CalendarClient.LOG_IN_VIEW);
			} else {
				formStatus.setText("Brukeren kunne ikke blitt lagt til sjekk internett tilkoblingen din og prøv igjen");
				formStatus.setTextFill(Color.RED);
				formStatus.setVisible(true);
			}
		}
	}
	
	private void emptyForm() {
		FirstnameTextField.setText("");
		LastnameTextField.setText("");
		UsernameTextField.setText("");
		PasswordField1.setText("");
		PasswordField2.setText("");
		MailTextField.setText("");
		
	}

	public boolean isValidForm() {
		boolean isValid = isValidName(FirstnameTextField, firstnameStatus)
				& isValidName(LastnameTextField, lastnameStatus)
				& isValidUsername() 
				& isValidPassword() 
				& isEqualPassword() 
				& isValidEmail();
		return isValid; 
	}
	
	public void CancelButtonClick(ActionEvent e){
		//Move user back to userLogin screen 
		clearView();
		myController.setView(CalendarClient.LOG_IN_VIEW);
	}

    @Override
    public void viewRefresh() {

    }

    public void Password1FocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//Trim password
		PasswordField1.setText(PasswordField1.getText().trim());
		//Validates only when focus is moved away from field 
		if(!newValue) {
			isValidPassword();
		} else {
			// Hide password too short feedback
			password1Status.setText("Passordet må være minst 6 tegn langt");
			password1Status.setVisible(false);
			PasswordField1.setStyle("");
		}
	}
	/**
	 * Checks if password in passwordField1 isValid. Sets graphic accordingly
	 * @return true if password isValid
	 */
	private boolean isValidPassword() {
		if(PasswordField1.getText().length() < 6) {
			//Show password too short feedback
			password1Status.setText("Passordet må være minst 6 tegn langt");
			password1Status.setTextFill(Color.RED);
			password1Status.setVisible(true);
			PasswordField1.setStyle(NOT_VALID_FIELD_STYLE);
			return false;
		}
		return true;
	}

	public void password1TextChange(ObservableValue<String> o,  String oldValue, String newValue){
		//When password1TextChange remove text from password1 and hide notEqualFeedback
		PasswordField2.setText("");
		showNotEqualFeedback(false);
	}


	public void Password2FocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		//Trim password
		PasswordField2.setText(PasswordField2.getText().trim());
		//Validates only when focus is moved away from field 
		if(!newValue) {
			isEqualPassword(); 
		} 	else {
			// If user shifts focus to passwordfield2 remove feedback 
			showNotEqualFeedback(false);
		}
	}
	
	/**
	 * Validate that passwords are equal sets graphic accordingly 
	 * @return true if passwords are equal
	 */
	private boolean isEqualPassword() {
		//validates equality only if there is text in both fields
		boolean isValid = PasswordField1.getText().equals(PasswordField2.getText());			
		showNotEqualFeedback(!isValid);	
		return isValid;
	}
	
	/**
	 * Changes visibility for passwordEqual feedback
	 * @param visable true: positive feedback, false: negative feedback
	 */
	private void showNotEqualFeedback(boolean visable) {
		String color = visable ? NOT_VALID_FIELD_STYLE : "";
		PasswordField2.setStyle(color);
		password2Status.setText("Passordene er ikke like");
		password2Status.setTextFill(Color.RED);
		password2Status.setVisible(visable);
	}
	
	public void UsernameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		// Trim username
		UsernameTextField.setText(UsernameTextField.getText().trim());
		
		//Validates only when focus is moved away from field 
		if (!newValue) {
			//check if username is vailid
			isValidUsername();
		} else {
			//remove graphic 
			usernameStatus.setVisible(false);
			UsernameTextField.setStyle("");
		}
	}
	
	/**
	 * Checks if username is valid, sets Graphic accordingly 
	 * @return true if username is valid
	 */
	private boolean isValidUsername() {
		if (!UsernameTextField.getText().matches(USERNAME_REGEX)) {
			usernameStatus.setText("Brukernavn må ha 3-16 tegn og kan bare inneholde bokstaver og tall");
			usernameStatus.setTextFill(Color.RED);
			usernameStatus.setVisible(true);
			UsernameTextField.setStyle(NOT_VALID_FIELD_STYLE);
			return false; 
		}
		if (UserDB.checkUser(UsernameTextField.getText())){
			usernameStatus.setText("Brukernavnet eksisterer allerede");
			usernameStatus.setTextFill(Color.RED);
			usernameStatus.setVisible(true);
			UsernameTextField.setStyle(NOT_VALID_FIELD_STYLE);
			return false; 
		}
		return true;
	}

    @Override
    public void clearView() {
    	FirstnameTextField.setText("");
    	LastnameTextField.setText("");
    	UsernameTextField.setText("");
    	PasswordField1.setText("");
    	PasswordField2.setText("");
    	MailTextField.setText("");
    	
    	FirstnameTextField.setStyle("");
    	LastnameTextField.setStyle("");
    	UsernameTextField.setStyle("");
    	PasswordField1.setStyle("");
    	PasswordField2.setStyle("");
    	MailTextField.setStyle("");
    	
    	firstnameStatus.setText("");
    	lastnameStatus.setText("");
    	usernameStatus.setText("");
    	password1Status.setText("");
    	password2Status.setText("");
    	mailStatus.setText("");
    	formStatus.setText("");
    }

    @Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}
}

