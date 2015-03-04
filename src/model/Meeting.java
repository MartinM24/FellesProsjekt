package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

//TODO add constructors
public class Meeting {

	private int meetingID;
	private User owner;
	private Room room;
	private String place;
	private LocalDateTime timeStart;
	private LocalDateTime timeEnd;
	private String description;
	private List<User> participants;
	
	
	
	/**
	 * Create meeting with room from database
	 * @param meetingID
	 * @param owner
	 * @param room
	 * @param timeStart
	 * @param timeEnd
	 * @param description
	 * @param participants
	 */
	public Meeting(int meetingID, User owner, Room room,
			LocalDateTime timeStart, LocalDateTime timeEnd, String description,
			List<User> participants) {
		this(owner,room,timeStart,timeEnd,description,participants);
		this.meetingID = meetingID;
	}

	/**
	 * Create new meeting with room
	 * @param owner
	 * @param room
	 * @param timeStart
	 * @param timeEnd
	 * @param description
	 * @param participants
	 */
	public Meeting(User owner, Room room, LocalDateTime timeStart,
			LocalDateTime timeEnd, String description, List<User> participants) {
		this.owner = owner;
		this.room = room;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.description = description;
		this.participants = participants;
		this.place = "";
	}
	
	
	/**
	 * create meeting with place from database
	 * @param meetingID
	 * @param owner
	 * @param place
	 * @param timeStart
	 * @param timeEnd
	 * @param description
	 * @param participants
	 */
	public Meeting(int meetingID, User owner, String place,
			Timestamp timeStart, Timestamp timeEnd, String description,
			List<User> participants) {
		this(owner,place,timeStart.toLocalDateTime(),timeEnd.toLocalDateTime(),description,participants);
		this.meetingID = meetingID;
	}

	/**
	 * Create new meeting with place
	 * @param owner
	 * @param place
	 * @param timeStart
	 * @param timeEnd
	 * @param description
	 * @param participants
	 */
	public Meeting(User owner, String place, LocalDateTime timeStart,
			LocalDateTime timeEnd, String description, List<User> participants) {
		this.owner = owner;
		this.place = place;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.description = description;
		this.participants = participants;
		this.room = null;
	}
	
	/**
	 * Methode som sjekker om bruker har tilgang
	 * @param user
	 * @return om bruker har tilgang
	 */
	public boolean hasAccess(User user){
		if (user == owner){
			return true;
		}
		return participants.contains(user);
	}
	
	/**
	 * Method to get place that will be shown in the GUI, either the room or the place.
	 * @return
	 */
	public String getGUIPlace(){
		if (room==null){
			return place;
		}
		else{
			return room.getName();
		}
	}
	
	public void setMeetingID(int id){
		this.meetingID = id;
	}
	
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public LocalDateTime getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(LocalDateTime timeStart) {
		this.timeStart = timeStart;
	}
	public LocalDateTime getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(LocalDateTime timeEnd) {
		this.timeEnd = timeEnd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMeetingID() {
		return meetingID;
	}
	public User getOwner() {
		return owner;
	}
	public Room getRoom() {
		return room;
	}
	public List<User> getParticipants() {
		return participants;
	}
	
	public int getStartHour() {
		return timeStart.getHour();
	}

	public int getMinute() {
		return timeStart.getMinute();
	}

	public int getEndHour() {
		return timeEnd.getHour();
	}

	public int getEndMinute() {
		return timeEnd.getMinute();
	}


	/**
	 * Return the timestamp to be saved in the database for the starttime
	 * @return the timestamp for starttime
	 */
	public String getStartDB(){
		Date dt = Date.from(timeStart.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}

	/**
	 * Return the timestamp to be saved in the database for the endtime
	 * @return the timestamp for endtime
	 */
	public String getEndDB(){
		Date dt = Date.from(timeEnd.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}
	
	private long getMillis(LocalDateTime time){
		ZonedDateTime zdt = time.atZone(ZoneId.of(TimeZone.getDefault().getID()));
		return zdt.toEpochSecond();
	}
	

}
