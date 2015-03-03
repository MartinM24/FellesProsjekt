package dbconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.LoginUser;
import model.Meeting;
import model.User;

public class MeetingDB extends DatabaseConnection{
	
	private MeetingDB() {
		
	}
	
	public static List<Meeting> getAllMeetings(User user){
		List<Meeting> meetingList = new ArrayList<Meeting>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT *FROM meeting INNER JOIN participant INNER JOIN users WHERE users.username='"+user.getUsername()+"'");
			while (myRs.next()){
				meetingList.add(new Meeting(Integer.parseInt(myRs.getString(1)),user,myRs.getString(5),myRs.getTimestamp(3),myRs.getTimestamp(4),myRs.getString(2),new ArrayList<User>()));
			};
			System.out.println("Everything worked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return meetingList;
	}
	
	public static boolean removeMeeting(int meetingID) {
		try {
			Statement myStatement = con.createStatement(); 
			int count = myStatement.executeUpdate("DELETE FROM meeting WHERE meetingID = '" + meetingID + "'");
			if (count == 1) {
				return true; 
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}	
	
}
