package dbconnection;

import calendarClient.CalendarClient;
import model.*;

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

public class MeetingDB extends DatabaseConnection{
    private final static String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	private MeetingDB() {
		
	}
	
	public static List<Meeting> getAllMeetings(User user){
		List<Meeting> meetingList = new ArrayList<Meeting>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT meeting.meetingID FROM meeting INNER JOIN participant ON meeting.meetingID = participant.meetingID WHERE participant.username='"+user.getUsername()+"' AND participant.visibility <> -1 AND participant.attendence = 1");
            while (myRs.next()){
                int id = myRs.getInt(1);
				meetingList.add(getMeeting(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return meetingList;
	}
	
	public static List<InvitationVeiw> getAllInvitationViews(LoginUser user){
		List<InvitationVeiw> invitationlist = new ArrayList<InvitationVeiw>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT meeting.owner, meeting.mDescription, meeting.meetingID FROM participant INNER JOIN meeting ON participant.meetingID = meeting.meetingID WHERE participant.username='"+user.getUsername()+"' AND participant.attendence = 0");
			while (myRs.next()){
				invitationlist.add(new InvitationVeiw(myRs.getString(1), myRs.getString(2),""+myRs.getInt(3)) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invitationlist;
	}
	
	@SuppressWarnings("deprecation")
	public static List<InvitationVeiw> getAllInvitationViewsWithAttendence(LoginUser user){
		List<InvitationVeiw> invitationlist = new ArrayList<InvitationVeiw>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT meeting.mDescription, meeting.meetingID, participant.attendence, meeting.timeStart, meeting.timeEnd, users.firstname, users.lastname FROM participant INNER JOIN meeting ON participant.meetingID = meeting.meetingID INNER JOIN users ON meeting.owner = users.username WHERE participant.username='"+user.getUsername()+"'");
			String temp;
			while (myRs.next()){
				if(0>myRs.getInt(3))
					temp = "Deltar ikke";
				else if (0 == myRs.getInt(3))
					temp = "ikke svart";
				else
					temp = "Deltar";
				
				LocalDateTime tidFra = Meeting.convertStringToDate(myRs.getString(4)); 
				LocalDateTime tidTil = Meeting.convertStringToDate(myRs.getString(5));
				invitationlist.add(new InvitationVeiw(myRs.getString(1),""+myRs.getInt(2), temp,tidFra.toLocalTime().toString(), tidTil.toLocalTime().toString(), tidFra.toLocalDate().toString(), myRs.getString(6)+" "+myRs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invitationlist;
	}
	
	public static List<Meeting> getAlarmTriggered(User user){
		List<Meeting> rList = new ArrayList<Meeting>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT meetingID, alarmtid FROM participant WHERE username='"+user.getUsername()+"'");
			while (myRs.next()){
				String temp = myRs.getString(2);
				if(temp!=null){
					LocalDateTime tid = Meeting.convertStringToDate(temp);
					if(tid.isBefore(LocalDateTime.now())){
						rList.add(MeetingDB.getMeeting(myRs.getInt(1)));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rList;
	}
	
	
	
	public static void setAlarm(int meetingID, User user, LocalDateTime time){
		try {
			Statement myStatement = con.createStatement();
				myStatement.executeUpdate("UPDATE participant SET alarmtid='"+getDBTime(time)+"' WHERE meetingID='"+meetingID+"' AND username = '"+user.getUsername()+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public static void setAlarm(int meetingID, User user){
		try {
			Statement myStatement = con.createStatement();
				myStatement.executeUpdate("UPDATE participant SET alarmtid = NULL WHERE meetingID='"+meetingID+"' AND username = '"+user.getUsername()+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public static void updateInvitation(String meetingID, int attendence){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE participant SET attendence='"+attendence+"' WHERE meetingID='"+meetingID+"' AND username='"+calendarClient.CalendarClient.getCurrentUser().getUsername()+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static List<User> getParticipants(int meetingID){
		List<User> rList = new ArrayList<User>();
		try{
			Statement sqlSelect = con.createStatement();
			ResultSet myRs = sqlSelect.executeQuery("select username from participant WHERE meetingID = '"+meetingID+"'");
			while(myRs.next()){
				rList.add(UserDB.getUser(myRs.getString(1)));
			}
			return rList;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Meeting getMeeting(int meetingID){
		try{
			Statement sqlSelect = con.createStatement();
			ResultSet myRs = sqlSelect.executeQuery("select * from meeting WHERE meeting.meetingID = '"+meetingID+"'");
			myRs.next();
			String roomName = myRs.getString(7);
			Room room;
			if (myRs.wasNull())
				room = null;
			else
				room = RoomDB.getRoom(roomName);
			return new Meeting(myRs.getInt(1), UserDB.getUser(myRs.getString(8)), room, myRs.getString(5), myRs.getString(3), myRs.getString(4), myRs.getString(2), myRs.getInt(6), getParticipants(myRs.getInt(1)));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Deletes 
	 * @param meetingID
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
			updateParticipants(meetingID, CalendarClient.getCurrentUser(), "descriptionChange", true);
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE meeting SET mDescription='"+description+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void updateMeetingTimeStart(int meetingID, LocalDateTime timeStart){
		try {
			updateParticipants(meetingID, CalendarClient.getCurrentUser(), "timeChange", true);
			Statement myStatement = con.createStatement();
			myStatement.executeUpdate("UPDATE meeting SET timeStart='"+getDBTime(timeStart)+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void updateMeetingTimeEnd(int meetingID, LocalDateTime timeEnd){
		try {
			updateParticipants(meetingID, CalendarClient.getCurrentUser(), "timeChange", true);
			Statement myStatement = con.createStatement();
			myStatement.executeUpdate("UPDATE meeting SET timeEnd='"+getDBTime(timeEnd)+"' WHERE meetingID='"+meetingID+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void updateMeetingPlace(int meetingID, String place){
		try {
			updateParticipants(meetingID, CalendarClient.getCurrentUser(), "placeChange", true);
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
			updateParticipants(meetingID, CalendarClient.getCurrentUser(), "placeChange", true);
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
	
	private static void updateParticipant(int meetingID, User user, String field, boolean value){
		int sqlBool = 0;
		if(value){
			sqlBool = 1;
		}
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE participant SET "+field+"='"+sqlBool+"' WHERE meetingID='"+meetingID+"' AND username = '"+user.getUsername()+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void updateParticipants(int meetingID, User user, String field, boolean value){
		int sqlBool = 0;
		if(value){
			sqlBool = 1;
		}
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE participant SET "+field+"='"+sqlBool+"' WHERE meetingID='"+meetingID+"' AND username <> '"+user.getUsername()+"'");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		
	public static boolean removeParticipant(int meetingID, User user) {
		try {

			updateParticipants(meetingID, user, "participantChange", true);
			Statement myStatement = con.createStatement();
			int count = myStatement.executeUpdate("UPDATE participant SET visibility = -1, attendence = -1 WHERE meetingID = '" + meetingID + "' AND username= '" + user.getUsername()+"'");
			if (count > 0) {
				return true; 
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

    public static boolean deleteParticipant(int meetingID, User user) {
        try {
            if(MeetingDB.getMeeting(meetingID).getOwner().getUsername().trim().equals(user.getUsername().trim())){
                return false;
            }
            Statement myStm = con.createStatement();
            int count = myStm.executeUpdate("DELETE FROM participant WHERE  meetingID = '" + meetingID + "' AND username= '" + user.getUsername() + "'");
            if (count > 0) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	

	public static boolean participantExist(Meeting meeting, User user){
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT * FROM participant WHERE username='"+user.getUsername()+"' AND meetingID='"+meeting.getMeetingID()+"'");
			return (myRs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addParticipant(Meeting meeting, User user, int attendence){
		if(attendence>2 || attendence < 0){
			throw new IllegalArgumentException("Feil i attendence");
		}
		if(participantExist(meeting, user)){
			return false;
		}
		try {
			updateParticipants(meeting.getMeetingID(), user, "participantChange", true);
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
				return true;
			}
		}
		catch(Exception e){
            e.printStackTrace();
   		}
		return false;
	}
	
	public static int addMeeting(Meeting meeting){
		try {
			String meetingQuery = "insert into meeting (mDescription, timeStart, timeEnd, place, nOfParticipant, roomName, owner)"  + "values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery, 
				    Statement.RETURN_GENERATED_KEYS);
			preparedMeetingStmt.setString (1, meeting.getDescription());
			preparedMeetingStmt.setString(2, meeting.getStartDB());
			preparedMeetingStmt.setString (3, meeting.getEndDB());
			preparedMeetingStmt.setString(4, meeting.getPlace());
			preparedMeetingStmt.setInt(5, meeting.getNOfParticipantSet());
			if(meeting.getRoom()==null){
				preparedMeetingStmt.setNull(6, java.sql.Types.VARCHAR);
			}
			else{
				preparedMeetingStmt.setString(6, meeting.getRoom().getName());				
			}
			preparedMeetingStmt.setString(7, meeting.getOwner().getUsername());
			preparedMeetingStmt.executeUpdate();
			ResultSet tableKeys = preparedMeetingStmt.getGeneratedKeys();
			tableKeys.next();
			meeting.setMeetingID(tableKeys.getInt(1));
			for(User user : meeting.getParticipants()){
				MeetingDB.addParticipant(meeting, user, 0);
			}
			return meeting.getMeetingID();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
			return -1;
	
	}
	
	/**
	 * Adds participants
	 * @param group
	 * @param meeting
	 */
	public static void addGroup(Group group, Meeting meeting){
		try {
			String meetingQuery = "insert into meetinggrouplink (meetingID, groupName)"  + "values(?, ?)";
			PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery);
			preparedMeetingStmt.setInt (1, meeting.getMeetingID());
			preparedMeetingStmt.setString(2, group.getName());
            preparedMeetingStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		for(User user: group){
			addParticipant(meeting, user, 0);
		}
	}
	
	/**
	 * Does not add Participants
	 * @param group
	 * @param meeting
	 * @param i does fuck all
	 */
	public static void addGroup(Group group, Meeting meeting, int i){
		try {
			String meetingQuery = "insert into meetinggrouplink (meetingID, groupName)"  + "values(?, ?)";
			PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery);
			preparedMeetingStmt.setInt (1, meeting.getMeetingID());
			preparedMeetingStmt.setString(2, group.getName());
			preparedMeetingStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void removeGroup(Group group, Meeting meeting){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("DELETE FROM meetinggrouplink WHERE groupName = '" + group.getName()+"' AND meetingID='"+meeting.getMeetingID()+"'");
            for(User usr: GroupDB.getAllMembers(group.getName())) {
                MeetingDB.deleteParticipant(meeting.getMeetingID(), usr);
            }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static List<Group> getAllGroups(Meeting meeting){
		List<Group> groupList = new ArrayList<>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT groupName FROM meetinggrouplink WHERE meetingID='"+meeting.getMeetingID()+"'");
			while (myRs.next()){
				groupList.add(new Group(myRs.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groupList;
	}
	
    public static String getDBTime(LocalDateTime time) {
        Date dt = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(dt);
    }
    
    
    /**
     * Return list with changes(participantChange, timeChange, placeChange, descriptionChange) in meeting
     * @param meeting
     * @param user
     * @return
     */
    public static List<Boolean> getChanges(Meeting meeting, User user){
    	List<Boolean> changes = new ArrayList<Boolean>();
    	try{
			Statement sqlSelect = con.createStatement();
			
			ResultSet myRs = sqlSelect.executeQuery("select participantChange, timeChange, placeChange, descriptionChange from participant WHERE participant.meetingID = '"+meeting.getMeetingID()+"' AND participant.username = '"+user.getUsername()+"'");
			myRs.next();
			for(int i = 1; i <= 4 ; i++){
				changes.add(myRs.getBoolean(i));
			}
    	}
    	
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return changes;
}
	public static int getAttendence(LoginUser user, Meeting meeting) {
		try {
			Statement myStatement = con.createStatement(); 
			ResultSet myRs = myStatement.executeQuery("SELECT attendence FROM participant WHERE username= '"+user.getUsername()+"' AND meetingID= '"+meeting.getMeetingID()+"'");
			myRs.next();
			return (myRs.getInt(1));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return -2;
	}
	
	public static List<MeetingVeiw> getAttendenceForMeeting(Meeting meeting) {
    	List<MeetingVeiw> attendence = new ArrayList<MeetingVeiw>();
    	String temp;
    	try{
			Statement sqlSelect = con.createStatement();
			ResultSet myRs = sqlSelect.executeQuery("SELECT username, attendence FROM participant WHERE meetingID='"+ meeting.getMeetingID() +"'");
			while(myRs.next()){
				User user = UserDB.getUser((myRs.getString(1)));
				if (myRs.getInt(2) <0)
					temp = "Deltar ikke";			
				else if (myRs.getInt(2) == 0)
					temp = "Ikke svart";
				else 
					temp = "deltar";
				attendence.add(new MeetingVeiw(
						user.getFirstname()+" "+user.getLastname(), 
						temp));
			}
    	}
    	
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return attendence;
	}
    
    public static void resetChanged(User user){
    	List<Meeting> meetings = getAllMeetings(user);
    	for(Meeting meeting:meetings){
    		updateParticipant(meeting.getMeetingID(), user, "participantChange", false);
    		updateParticipant(meeting.getMeetingID(), user, "timeChange", false);
    		updateParticipant(meeting.getMeetingID(), user, "placeChange", false);
    		updateParticipant(meeting.getMeetingID(), user, "descriptionChange", false);
    	}
    	for(Meeting meeting : MeetingDB.getAllMeetings(user)){
    		MeetingDB.setAlarm(meeting.getMeetingID(), user);
    	}
    }
    
    public static List<String> getAllChanges(User user){
    	List<List<Boolean>> changes = new ArrayList<List<Boolean>>();
    	List<Meeting> meetings = getAllMeetings(user);
    	for(int i = 0 ; i < meetings.size() ; i++){
    		List<Boolean> temp = getChanges(meetings.get(i), user);
    		boolean changed = false;
    		for(boolean b : temp){
    			if(b){
    				changed = true;
    				break;
    			}
    		}
    		if(changed){
    			changes.add(temp);
    		}
    		else{
    			meetings.remove(i);
    			i--;
    		}
    	}
    	List<String> changeMessages = new ArrayList<String>();
    	for(Meeting meeting : MeetingDB.getAlarmTriggered(user)){
    		changeMessages.add("Din alarm for "+meeting.getDescription()+" er aktivert");
    	}
    	for(int i = 0 ; i < changes.size() ; i++){
    		changeMessages.add(getMessage(meetings.get(i), changes.get(i)));
    	}
    	return changeMessages;
    }
    
    private static String getMessage(Meeting meeting, List<Boolean> changes){
    	List<String> changesString = new ArrayList<String>();
    	if(changes.get(0)){
    		changesString.add("Deltagerliste");
    	}
    	if(changes.get(1)){
    		changesString.add("Tid");
    	}
    	if(changes.get(2)){
    		changesString.add("Sted");
    	}
    	if(changes.get(3)){
    		changesString.add("Beskrivelse");
    	}
    	
    	String str = meeting.getDescription()+" er endret. ";
    	for(int i = 0 ; i < changesString.size() ; i++){
    		if(i==0){
    			str+=changesString.get(i);
    		}
    		else{
    			str+=" ,"+changesString.get(i).toLowerCase();
    		}
    	}
    	str+=" er blitt endret";
    	return str;
	}

}

