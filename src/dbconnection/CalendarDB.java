package dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Calendar;
import model.User;

public class CalendarDB extends DatabaseConnection {
	
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
		
	public static void addCalendar(User user, Calendar calendar){
		try {
			String meetingQuery = "insert into calendar (name)"  + "values(?)";
			PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery, 
				    Statement.RETURN_GENERATED_KEYS);
			preparedMeetingStmt.setString (1, calendar.getName());
			int res = preparedMeetingStmt.executeUpdate();
			if (res > 0) {
				System.out.println("Inserting Calendar worked");
			}
			ResultSet tableKeys = preparedMeetingStmt.getGeneratedKeys();
			tableKeys.next();
			calendar.setCalenderID(tableKeys.getInt(1));
			try {
				
				String sqlQuery = "UPDATE users "+
						"SET calendarID = ? "+
						"WHERE users.username = ?";
				PreparedStatement myStatement = con.prepareStatement(sqlQuery); 
				myStatement.setInt(1, calendar.getCalenderID());
				myStatement.setString(2,user.getUsername());
				myStatement.executeUpdate(sqlQuery);
				System.out.println(sqlQuery);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		catch(Exception e2){
			
		}
	}
		
	
}
