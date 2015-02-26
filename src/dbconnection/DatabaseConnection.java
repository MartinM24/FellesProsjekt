package dbconnection;

import java.sql.*;

public class DatabaseConnection {

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
/*		Her kommer det kode som bruker user
 * 		String query = "insert into test3 (firstname, lastname, passord, epost, username)"  + "values(?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString (1, fn);
		preparedStmt.setString (2, en);
		preparedStmt.setString (3, pw);
		preparedStmt.setString (4, ep);
		preparedStmt.setString (5, bn);
		preparedStmt.execute();
		System.out.println("Everything worked");
*/		
		endCon(con);
	      
	}
}

