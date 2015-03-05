package dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	
//	public static Meeting getMeeting(int meetingID){
//		try{
//			Statement sqlSelect = con.createStatement();
//			
//			
////			ResultSet myRs = myStatement.executeQuery("select * from meeting WHERE meeting.meetingID = '"+meetingID+"'");
////			if myRs.get
////				Meeting = new Meeting(myRs.getString(1), myRs.getString(2), myRs.getString(3),));
////			};
//		}
//	}
	
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
	

	public static boolean addParticipant(Meeting meeting, User user, int attendence){
		if(attendence>2 || attendence < 0){
			throw new IllegalArgumentException("Feil i attendence");
		}
		try {
			String participantQuery = "insert into participant (username, meetingID, attendence, visibility, alarmtid, answered, participantChange, timeChange, placeChange, descriptionChange)"  + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedParticipantStmt = con.prepareStatement(participantQuery);
			preparedParticipantStmt.setString (1, user.getUsername());
			preparedParticipantStmt.setInt(2, meeting.getMeetingID());
			preparedParticipantStmt.setInt (3, attendence);
			preparedParticipantStmt.setInt(4, 0);
			preparedParticipantStmt.setNull(5,java.sql.Types.DATE);
			preparedParticipantStmt.setBoolean(6, false);
			preparedParticipantStmt.setBoolean(7,false);
			preparedParticipantStmt.setBoolean(8,false);
			preparedParticipantStmt.setBoolean(9,false);
			preparedParticipantStmt.setBoolean(10,false);
			int res = preparedParticipantStmt.executeUpdate();
			if (res > 0) {
				System.out.println("Inserting Participant worked");
				return true;
			}
		}
		catch(Exception e){
			System.out.println("Feil i insert av participant");
		}
		return false;
	}
	
	public static int addMeeting(Meeting meeting){
		//TODO skal ikke kunne legge inn i meeting uten at det blir lagt inn i participant
		try {
			String meetingQuery = "insert into meeting (mDescription, timeStart, timeEnd, sted, nOfParticipant, roomID, owner)"  + "values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery, 
				    Statement.RETURN_GENERATED_KEYS);
			preparedMeetingStmt.setString (1, meeting.getDescription());
			preparedMeetingStmt.setString(2, meeting.getStartDB());
			preparedMeetingStmt.setString (3, meeting.getEndDB());
			preparedMeetingStmt.setString(4, meeting.getPlace());
			preparedMeetingStmt.setInt(5, meeting.getNOfParticipantSet());
			if(meeting.getRoom()==null){
				preparedMeetingStmt.setNull(6, java.sql.Types.INTEGER);
			}
			else{
				preparedMeetingStmt.setInt(6, meeting.getRoom().getRoomID());				
			}
			preparedMeetingStmt.setString(7, meeting.getOwner().getUsername());
			int res = preparedMeetingStmt.executeUpdate();
			if (res > 0) {
				System.out.println("Inserting Meeting worked");
			}
			
			ResultSet tableKeys = preparedMeetingStmt.getGeneratedKeys();
			tableKeys.next();
			meeting.setMeetingID(tableKeys.getInt(1));
			MeetingDB.addParticipant(meeting, meeting.getOwner(), 2);
			return meeting.getMeetingID();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
			return -1;
		}
}

