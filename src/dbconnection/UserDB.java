package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import model.LoginUser;
import model.User;

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
	
	public static LoginUser getLoginUser(String username) {
		LoginUser user = null;		
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("select * from users where username = '" + username + "'");
			myRs.first();
			user = new LoginUser(myRs.getString(1), myRs.getString(2), myRs.getString(3), myRs.getString(4), myRs.getBytes(5), myRs.getBytes(6));
			System.out.println("Everything worked");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
	
	public static User getUser(String username) {
		User user = null;		
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("select * from users where username = '"+ username + "'");
			myRs.first();
			user = new User(myRs.getString(1), myRs.getString(2), myRs.getString(3), myRs.getString(4));
			System.out.println("Everything worked");
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean checkUser(String username){
		try{
			Statement mysStatement = con.createStatement();
			ResultSet myRs = mysStatement.executeQuery("select count(1) from users where username = '"+ username +"'");
			myRs.first();
			return (myRs.getInt(1) > 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;	
		} 	
	}
	
	public static void rmUser(String username){
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("delete from users where username = '"+ username + "'");
			myRs.first();
			System.out.println("user is deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	public static void removeUser(LoginUser user){
		try{
			Statement myStatement = con.createStatement();
			myStatement.executeUpdate("delete from users where username = "+user.getUsername());
			System.out.println("Everything worked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}