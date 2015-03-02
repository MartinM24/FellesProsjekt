package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import model.LoginUser;

public class UserConnection extends DatabaseConnection{

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {	
		Connection con  =  null;
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/mariuene_MMMAT";
		  String user = "mariuene_admin";
		  @SuppressWarnings("resource")
		  Scanner user_input = new Scanner(System.in);
		  System.out.println("Fornavn: ");
		  String fn = user_input.next();
		  System.out.println("Etternavn: ");
		  String en = user_input.next();
		  System.out.println("Passord ");
		  String pw = user_input.next();
		  System.out.println("Epost: ");
		  String ep = user_input.next();
		  System.out.println("Username: ");
		  String bn = user_input.next();
		  
		  con = DriverManager.getConnection(url,user,"1234");
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

	/**
	 * Gets all users in database and returns it as a List<Users>
	 * @return List<Users>
	 */
	
	public static ArrayList<LoginUser> getAllUsers() {
		Connection con = startCon();
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
		endCon(con);
		return userList;
	}
	
	public static ArrayList<LoginUser> getUser(String username) {
		Connection con = startCon();
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
		endCon(con);
		return userList;
	}


	/**
	 * Adds a new loginUser to the Database
	 * @param LoginUser user
	 */
	public static void addUser(LoginUser user) {
		Connection con = startCon();
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
		endCon(con);     
	}
}