package calendar;

import java.util.List;

import usergroup.LoginUser;
import usergroup.User;
import dbconnection.DatabaseConnection;

public class CalendarSystem {
	private LoginUser loginUser; 
	private List<User> users; 
	
	public CalendarSystem() {
		this.users = DatabaseConnection.getAllUsers();
	}
	
	
	
	

}
