package dbconnection;

import java.sql.*;
import java.util.Scanner;

public class NewUserConnection {

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