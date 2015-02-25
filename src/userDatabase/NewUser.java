package userDatabase;

import java.sql.*;
import java.util.ArrayList;


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

public class NewUser extends Application {
	
	ArrayList<ArrayList<String>> brukere = GetSkjema();
	@FXML TextField FirstnameTextField;
	@FXML TextField	LastnameTextField;
	@FXML TextField	UsernameTextField;
	@FXML TextField	MailTextField;
	@FXML PasswordField PasswordPasswordField1;
	@FXML PasswordField PasswordPasswordField2;
	@FXML Button OKButton;
	@FXML Button CancelButton;
	
	@Override
	public void start(Stage stage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("NewUserGUI.fxml"));
	    stage.setTitle("OpprettBruker");
	    stage.setScene(new Scene(root, 900, 600));
	    stage.show();
	    }
	
	public void FirstnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		
	}
	public void LastnameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		
	}
	public void MailFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		
	}
	public void OKButtonClick(ActionEvent e) throws InstantiationException, IllegalAccessException{
		sendSjkema(FirstnameTextField.getText(),
				LastnameTextField.getText(), 
				PasswordPasswordField1.getText(), 
				MailTextField.getText(), 
				UsernameTextField.getText());
	}
	public void CancelButtonClick(ActionEvent e){
		
	}
	
	public void Password1FocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		validatePassword();
	}

	public void Password2FocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		validatePassword();
	}
	
	
	
	
	
	
	public void UsernameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
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
		if(!(PasswordPasswordField1.getText().equals(PasswordPasswordField2.getText()))){
			System.out.println("Passwords don't match");
		}
	}
	
	public ArrayList<ArrayList<String>> GetSkjema() {			
		Connection con  =  null;
		ArrayList<ArrayList<String>> userList = new ArrayList<ArrayList<String>>();
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/mariuene_MMMAT";
		  String user = "mariuene_admin";
		  String pw = "1234";
		  		  
		  con = DriverManager.getConnection(url,user,pw);
		  System.out.println("Tilkoblingen fungerte.");
		  
		  Statement myStatement = con.createStatement();
		  ResultSet myRs = myStatement.executeQuery("select * from test3");
		  while (myRs.next()){
			ArrayList<String> temp = new ArrayList<String>();
			for (int i = 0; i<5; i++){
				temp.add(i, myRs.getString(i+2));
			}
			userList.add(temp);
		  }
		  for (int i = 0; i<userList.size(); i++){
			  System.out.println(userList.get(i));
		  }
		  
		  } catch (SQLException ex) {
		    System.out.println("Tilkobling feilet: "+ex.getMessage());
		  } catch (ClassNotFoundException ex) {
		    System.out.println("Feilet under driverlasting: "+ex.getMessage());
		  } finally {
		    try {
		      if (con !=  null) con.close();
		    } catch (SQLException ex) {
		      System.out.println("Epic fail: "+ex.getMessage());
		    }
		  }		
		return userList;    
	} 
	
	public static void sendSjkema(String fn, String en, String pw, String ep, String bn) throws InstantiationException, IllegalAccessException {	
		Connection con  =  null;
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/mariuene_MMMAT";
		  String user = "mariuene_admin";
		  
		  con = DriverManager.getConnection(url,user,pw);
		  System.out.println("Tilkoblingen fungerte.");
		  
		  String query = "insert into test3 (firstname, lastname, passord, epost, username)" 
		  + "values(?, ?, ?, ?, ?)";
		  
	      PreparedStatement preparedStmt = con.prepareStatement(query);
	      preparedStmt.setString (1, fn);
	      preparedStmt.setString (2, en);
	      preparedStmt.setString (3, pw);
	      preparedStmt.setString (4, ep);
	      preparedStmt.setString (5, bn);
	      
	      preparedStmt.execute();
	      System.out.println("Everything worked");
	      con.close();
		  
		  } catch (SQLException ex) {
		    System.out.println("Tilkobling feilet: "+ex.getMessage());
		  } catch (ClassNotFoundException ex) {
		    System.out.println("Feilet under driverlasting: "+ex.getMessage());
		  } finally {
		    try {
		      if (con !=  null) con.close();
		    } catch (SQLException ex) {
		      System.out.println("Epic fail: "+ex.getMessage());
		    }
		  }
	}
}

