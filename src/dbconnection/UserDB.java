package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import model.LoginUser;

public class UserDB extends DatabaseConnection{
	
	private UserDB() {
		super();
	}

	/**
	 * Gets all users in database and returns it as a List<Users>
	 * @return List<Users>
	 */
	
	public static ArrayList<LoginUser> getAllUsers() {
		ArrayList<LoginUser> userList = new ArrayList<LoginUser>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("select * from users");
			while (myRs.next()){
				userList.add(new LoginUser(myRs.getString(1), myRs.getString(2), myRs.getString(3), myRs.getString(4), myRs.getBytes(5), myRs.getBytes(6)));
			};
			System.out.println("Everything worked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public static LoginUser getUser(String username) {
		LoginUser user = null;		
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("select * from users where username = "+username);
			user = new LoginUser(myRs.getString(1), myRs.getString(2), myRs.getString(3), myRs.getString(4), myRs.getBytes(5), myRs.getBytes(6));
			System.out.println("Everything worked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}


	/**
	 * Adds a new loginUser to the Database
	 * @param LoginUser user
	 */
	public static void addUser(LoginUser user) {
		try {
		String query = "insert into users (username, firstname, lastname, email, salt, hash)"  + "values(?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString (1, user.getUsername());
		preparedStmt.setString (2, user.getFirstname());
		preparedStmt.setString (3, user.getLastname());
		preparedStmt.setString (4, user.getEmail());
		preparedStmt.setBytes(5, user.getDBSalt());
		preparedStmt.setBytes(6, user.getDBHash());
		preparedStmt.execute();
		System.out.println("Everything worked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}