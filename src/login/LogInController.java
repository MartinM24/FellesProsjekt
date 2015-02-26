package login;

import java.time.LocalTime;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class LogInController {
	
	private static final String loginRegex = "[[a-zA-ZæøåÆØÅ]+[\\-\\s]*[a-zA-ZæøåÆØÅ]+]+";

	
	LogIn login = new LogIn();
	
	@FXML TextField	usernameField;
	@FXML PasswordField passwordField;
	@FXML Button cancelButton;
	@FXML Button okButton;
	@FXML Hyperlink fpHyperlink;
	
	
	@FXML
	public void initialize() {	
	}
	
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
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		if(validateText(usernameField.getText(), loginRegex , usernameField )) {
			if (validateText(passwordField.getText(), loginRegex, passwordField)) {
				login.setUsername(usernameField.getText());
				login.setPassword(passwordField.getText());
			}
		}
	}
	
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
		passwordField.tooltipProperty().setValue(new Tooltip("Her kan du ikke bruke æ, ø, å"));
		
	}
}

