package login;

//import java.sql.*;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogIn {

	private StringProperty usernameProperty = new SimpleStringProperty();
	private StringProperty passwordProperty = new SimpleStringProperty();
	
	
	public String getUsername() {
		return usernameProperty.getValue();
	}

	public void setUsername(String username) {
		usernameProperty.setValue(username);
		
	}
	
	public String getPassword() {
		return passwordProperty.getValue();
	}

	public void setPassword(String password) {
		usernameProperty.setValue(password);
	}
	
	


	public String toStrng() {
	return "Username = " + getUsername() + "Password = " + getPassword();
	
	}
	
	
	

}
