package dbconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Calendar;

public class CalenderDB extends DatabaseConnection {
	
	public static Calendar getCalendar(int calendarID){
		Calendar calendar = null;		
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("select * from calender where calendarID = '"+ calendarID + "'");
			myRs.first();
			calendar = new Calendar( Integer.parseInt(myRs.getString(1)), myRs.getString(2));
			System.out.println("Calender is fetched");
			return calendar;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean removeCalender(int calendarID){
		try{
			Statement myStatement = con.createStatement();
			int res = myStatement.executeUpdate("delete from calender where calenderID = '"+ calendarID + "'");
			if(res > 0) {
				System.out.println("Calendar " + Integer.toString(calendarID) + " has been remove");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
