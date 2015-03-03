package dbconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Calender;

public class CalenderDB extends DatabaseConnection {
	
	public static Calender getCalender(int calenderID){
		Calender calender = null;		
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("select * from calender where calenderID = '"+ calenderID + "'");
			myRs.first();
			calender = new Calender( Integer.parseInt(myRs.getString(1)), myRs.getString(2));
			System.out.println("Calender is fetched");
			return calender;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void rmCalender(int calenderID){
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("delete from calender where calenderID = '"+ calenderID + "'");
			myRs.first();
			System.out.println("calender is deleted");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
