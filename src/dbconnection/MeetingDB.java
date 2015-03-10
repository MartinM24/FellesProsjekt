package dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Group;
import model.Meeting;
import model.Room;
import model.User;

public class MeetingDB extends DatabaseConnection{
    private final static String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	private MeetingDB() {
		
	}
	
	public static List<Meeting> getAllMeetings(User user){
		List<Meeting> meetingList = new ArrayList<Meeting>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT meeting.meetingID FROM meeting INNER JOIN participant ON meeting.meetingID = participant.meetingID WHERE participant.username='"+user.getUsername()+"' AND participant.visibility <> -1");
            while (myRs.next()){
                int id = myRs.getInt(1);
				meetingList.add(getMeeting(id));
			}
			System.out.println("Everything worked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return meetingList;
	}
	
	public static Meeting getMeeting(int meetingID){
		try{
			Statement sqlSelect = con.createStatement();
			ResultSet myRs = sqlSelect.executeQuery("select * from meeting WHERE meeting.meetingID = '"+meetingID+"'");
			myRs.next();
			return new Meeting(myRs.getInt(1), UserDB.getUser(myRs.getString(8)), null/*7*/, myRs.getString(5), myRs.getString(3), myRs.getString(4), myRs.getString(2), myRs.getInt(6), null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Deletes 
	 * @param meetingId
	 * @param user
	 * @return
	 */
	public static void deleteMeeting(int meetingID, User user){
		try {
			Meeting meeting = MeetingDB.getMeeting(meetingID);
			if(meeting.getOwner().getUsername().equals(user.getUsername())){
				MeetingDB.deleteMeeting(meetingID);
			}
			else{
				MeetingDB.removeParticipant(meetingID, user);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Only meetingowner is allowed to invoke this method. Must check this before calling this method.
	 * @param meetingID
	 * @return
	 */
	private static boolean deleteMeeting(int meetingID) {
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
	
	public static void updateMeetingDescription(int meetingID, String description){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET mDescription='"+description+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void updateMeetingTimeStart(int meetingID, LocalDateTime timeStart){
		try {
			//TODO denne metoden skal booke m�teromet p� nytt. 
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET timeEnd='"+getDBTime(timeStart)+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void updateMeetingTimeEnd(int meetingID, LocalDateTime timeEnd){
		try {
			//TODO denne metoden skal booke m�teromet p� nytt. 
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET timeEnd='"+getDBTime(timeEnd)+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void updateMeetingPlace(int meetingID, String place){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET place='"+place+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public static void updateMeetingNofParticipants(int meetingID, int nOfParticipant){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET nOfParticipant='"+nOfParticipant+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void updateMeetingRoom(int meetingID, Room room){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET roomName='"+room.getName()+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void updateMeetingOwner(int meetingID, User owner){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET owner='"+owner.getUsername()+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		
	private static boolean removeParticipant(int meetingID, User user) {
		try {
			Statement myStatement = con.createStatement(); 
			int count = myStatement.executeUpdate("UPDATE participant SET visibility = -1 WHERE meetingID = '" + meetingID + "' AND username= '" + user.getUsername()+"'");
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
				preparedMeetingStmt.setString(6, meeting.getRoom().getName());				
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
	
	public static void addGroup(Group group, Meeting meeting){
		for(User user: group){
			addParticipant(meeting, user, 0);
		}
	}
	
    public static String getDBTime(LocalDateTime time) {
        Date dt = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(dt);
    }
	
}

