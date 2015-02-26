package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReadUsersDB {
	
	public static ArrayList<ArrayList<String>> GetSkjema() {			
		Connection con  =  null;
		ArrayList<ArrayList<String>> userList = new ArrayList<ArrayList<String>>(); //return listen
		try {
		  Class.forName("com.mysql.jdbc.Driver");
		  String url = "jdbc:mysql://mysql.stud.ntnu.no/mariuene_MMMAT";
		  String user = "mariuene_admin";
		  String pw = "1234";
		  //kobler opp på database
		  con = DriverManager.getConnection(url,user,pw);
		  System.out.println("Tilkoblingen fungerte.");
		  
		  Statement myStatement = con.createStatement();
		  ResultSet myRs = myStatement.executeQuery("select * from test3"); //sender et query
		  while (myRs.next()){
			ArrayList<String> temp = new ArrayList<String>();
			for (int i = 0; i<5; i++){
				temp.add(i, myRs.getString(i+2));
			}
			userList.add(temp); //legger til liste over brukerinfo i databasen i Userlist
		  }
		  for (int i = 0; i<userList.size(); i++){
			  System.out.println(userList.get(i)); //skriver ut brukerinfo (Debug)
		  }
		  //exceptions. 
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
		return userList;    
	} 
	
}
