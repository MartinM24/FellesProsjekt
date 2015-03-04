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
	
	
	/**
	 * returns calendar from database with the given calendarID
	 * @param calendarID
	 * @return
	 */
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
	
	/**
	 * Removes from the database, the calendar with the given calendarID 
	 * @param calendarID
	 * @return
	 */
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
	
	
	/**
	 * Add a default calendar in the database and returns it. Connection to user is done in UserDB
	 * @return the created calendar
	 */
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
	
	
	/**
	 * Adds a connection between the given calendar and the given meeting in the database
	 * <b> Both must be added in the database before using this method </b>
	 * @param calendar
	 * @param meeting
	 */
	public static void addMeeting(Calendar calendar, Meeting meeting){
		try {
			String addMeetingQuery = "insert into meeting (mDescription, timeStart, timeEnd, sted)"  + "values(?, ?, ?, ?)";
			PreparedStatement addMeetingStmt = con.prepareStatement(addMeetingQuery, 
				    Statement.RETURN_GENERATED_KEYS);
			addMeetingStmt.setString (1, meeting.getDescription());
			addMeetingStmt.setTimestamp(2, meeting.getStartDB());
			addMeetingStmt.setTimestamp(3, meeting.getEndDB());
			addMeetingStmt.setString (4, meeting.getPlace());
			addMeetingStmt.executeUpdate();
			ResultSet tableKeys = addMeetingStmt.getGeneratedKeys();
			tableKeys.next();
			meeting.setMeetingID(tableKeys.getInt(1));
			
			String addCalendarMeetingQuery = "insert into calendarmeeting (calendarID, meetingID)"  + "values(?, ?)";
			PreparedStatement addCalendarMeetingStmt = con.prepareStatement(addCalendarMeetingQuery);
			addCalendarMeetingStmt.setInt (1, calendar.getCalenderID());
			addCalendarMeetingStmt.setInt (2, meeting.getMeetingID());
			addCalendarMeetingStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		DatabaseConnection.startCon();
		Calendar calendar = getCalendar(3);
		Meeting meeting = new Meeting (UserDB.getUser("will"), "A place underneath the moon", LocalDateTime.now(), LocalDateTime.now(), "Profit", new ArrayList<User>() );
		addMeeting(calendar, meeting);
	}
}
