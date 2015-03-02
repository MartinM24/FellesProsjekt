package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

public class Meeting {

	private String meetingID;
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
	public Meeting(String meetingID, User owner, Room room,
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
	public Meeting(String meetingID, User owner, String place,
			LocalDateTime timeStart, LocalDateTime timeEnd, String description,
			List<User> participants) {
		this(owner,place,timeStart,timeEnd,description,participants);
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
	public String getMeetingID() {
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

	public int getStartMinute() {
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
	public Timestamp getStartDB(){
		return new Timestamp(getMillis(timeStart));
	}

	/**
	 * Return the timestamp to be saved in the database for the endtime
	 * @return the timestamp for endtime
	 */
	public Timestamp getEndDB(){
		return new Timestamp(getMillis(timeEnd));
	}
	
	private long getMillis(LocalDateTime time){
		ZonedDateTime zdt = time.atZone(ZoneId.of(TimeZone.getDefault().getID()));
		return zdt.toEpochSecond();
	}
	

}
