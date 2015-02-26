package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import usergroup.LoginUser;
import usergroup.User;

public class DatabaseConnection {
	
//	public static void main(String[] args) {
//		LoginUser user = new LoginUser("SivFanNR2","Trine", "Grande", "trine@venstre.hoyre", "JensErBest");
//		addUser(user);
//		System.out.println("User added.");
//	}

	private static Connection startCon() {	
		Connection con  =  null;
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/mariuene_MMMAT";
		  String user = "mariuene_admin";
		  String pw = "1234";		  
		  con = DriverManager.getConnection(url,user,pw);
		  System.out.println("Tilkoblingen fungerte.");
		 
		  } catch (SQLException ex) {
		    System.out.println("Tilkobling feilet: "+ex.getMessage());
		  } catch (ClassNotFoundException ex) {
		    System.out.println("Feilet under driverlasting: "+ex.getMessage());
		  }
		return con;
	}
	
	private static void endCon(Connection con) {
	    try {
		      if (con !=  null) con.close();
		    } catch (SQLException ex) {
		      System.out.println("Epic fail: "+ex.getMessage());
		    }
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
	
	/**
	 * Gets all users in database and returns it as a List<Users>
	 * @return List<Users>
	 */

	public static List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}

