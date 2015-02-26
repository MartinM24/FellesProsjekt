package dbconnection;

import java.sql.*;

import usergroup.User;

public class DatabaseConnection {
	
/*	public static void main(String[] args) {
		User user = new User("SivFanNR1","Per", "Sandberg", "per@frp.krf", "sivErPen");
		addUser(user);
		System.out.println("Per er i databasen vår.");
	} */

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
	
	public static void addUser(User user) {
		Connection con = startCon();
		try {
		String query = "insert into users (username, firstname, lastname, email)"  + "values(?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString (1, user.getUsername());
		preparedStmt.setString (2, user.getFirstname());
		preparedStmt.setString (3, user.getLastname());
		preparedStmt.setString (4, user.getEmail());
		preparedStmt.execute();
		System.out.println("Everything worked");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		endCon(con);     
	}
	
	
	
	
	
}

