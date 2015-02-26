package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteUserDB {

	public static void sendSjkema(String fn, String en, String pw, String ep, String bn) throws InstantiationException, IllegalAccessException {	
		Connection con  =  null;
		try {
			
			//sender brukerinfo til databasen. 
		  Class.forName("com.mysql.jdbc.Driver");
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/mariuene_MMMAT";
		  String user = "mariuene_admin";
		  String password = "1234";
		  
		  con = DriverManager.getConnection(url,user,password);
		  System.out.println("Tilkoblingen fungerte.");
		  
		  String query = "insert into test3 (firstname, lastname, passord, epost, username)"  + "values(?, ?, ?, ?, ?)";
		  
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
