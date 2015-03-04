package dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Calendar;
import model.Meeting;
import model.User;

public class CalendarDB extends DatabaseConnection {
	
	public static Calendar getCalendar(int calendarID){
		Calendar calendar = null;		
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("select * from calendar where calendarID = '"+ calendarID + "'");
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
			int res = myStatement.executeUpdate("delete from calendar where calendarID = '"+ calendarID + "'");
			if(res > 0) {
				System.out.println("Calendar " + Integer.toString(calendarID) + " has been remove");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
		
	public static Calendar addCalendar(){
		Calendar calendar = new Calendar("My Calendar");
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
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return calendar;
	}
	
	public static void addMeeting(Calendar calendar, Meeting meeting){
		try {
			String query = "insert into calendarmeeting (calendarID, meetingID)"  + "values(?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt (1, calendar.getCalenderID());
			preparedStmt.setInt (2, meeting.getMeetingID());
			preparedStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
		
	public static void main(String[] args) {
		DatabaseConnection.startCon();
		Calendar calendar = getCalendar(3);
		Meeting meeting = new Meeting (UserDB.getUser("will"), "A place underneath the sun", LocalDateTime.now(), LocalDateTime.now(), "Something something", new ArrayList<User>() );
		addMeeting(calendar, meeting);
	}
}
