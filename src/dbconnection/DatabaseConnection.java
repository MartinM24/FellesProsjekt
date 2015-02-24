package dbconnection;

import java.sql.*;
import java.util.Scanner;

public class DatabaseConnection {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {	
		Connection con  =  null;
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/mariuene_MMMAT";
		  String user = "mariuene_admin";
		  @SuppressWarnings("resource")
		  Scanner user_input = new Scanner(System.in);
		  System.out.println("Passord: ");
		  String pw = user_input.next();
		  
		  con = DriverManager.getConnection(url,user,pw);
		  System.out.println("Tilkoblingen fungerte.");
		  
		  Statement myStatement = con.createStatement();
		  ResultSet myRs = myStatement.executeQuery("select * from test2");
		  while (myRs.next()){
			  System.out.println(myRs.getString(2)+ " motherfucking " + myRs.getString(3));
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
	}
}

