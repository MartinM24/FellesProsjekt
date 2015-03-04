package dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
			if (count > 0) {
				return true; 
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}	
	
	public static boolean addMeeting(Meeting meeting, User user){
		//TODO skal ikke kunne legge inn i meeting uten at det blir lagt inn i participant
		try {
			String meetingQuery = "insert into meeting (mDescription, timeStart, timeEnd, sted)"  + "values(?, ?, ?, ?)";
			PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery, 
				    Statement.RETURN_GENERATED_KEYS);
			preparedMeetingStmt.setString (1, meeting.getDescription());
			preparedMeetingStmt.setTimestamp(2, meeting.getStartDB());
			preparedMeetingStmt.setTimestamp (3, meeting.getEndDB());
			preparedMeetingStmt.setString(4, meeting.getPlace());
			int res = preparedMeetingStmt.executeUpdate();
			if (res > 0) {
				System.out.println("Inserting Meeting worked");
			}
			
			ResultSet tableKeys = preparedMeetingStmt.getGeneratedKeys();
			String participantQuery = "insert into participant (meetingID,username)" + "values(?, ?)";
			PreparedStatement preparedParticipantStmt = con.prepareStatement(participantQuery);
			tableKeys.next();
			preparedParticipantStmt.setInt(1, tableKeys.getInt(1));
			preparedParticipantStmt.setString(2, user.getUsername());
			int res2 = preparedParticipantStmt.executeUpdate();
			if(res2 > 0){
				System.out.println("Inserting Participant worked");
				return true;
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false; 
	}
	
	public static void main(String[] args) {
		DatabaseConnection.startCon();
		User user = UserDB.getUser("mart");
		Meeting meeting = new Meeting(user, "a place", LocalDateTime.now(), LocalDateTime.now(), "desc", new ArrayList<User>());
		MeetingDB.addMeeting(meeting, user);
	}
}
