package model;

import java.time.LocalDateTime;
import java.util.List;

public class Meeting {

	private String meetingID;
	private User owner;
	private Room room;
	private String place;
	private LocalDateTime timeStart;
	private LocalDateTime timeEnd;
	private String description;
	private List<User> participants;
	
	
	
	public Meeting(String meetingID, User owner, Room room, String place,
			LocalDateTime timeStart, LocalDateTime timeEnd, String description,
			List<User> participants) {
		this.meetingID = meetingID;
		this.owner = owner;
		this.room = room;
		this.place = place;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.description = description;
		this.participants = participants;
	}
	
	public Meeting(User owner, Room room, String place,
			LocalDateTime timeStart, LocalDateTime timeEnd, String description,
			List<User> participants){
		this.owner = owner;
		this.room = room;
		this.place = place;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.description = description;
		this.participants = participants;
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
	
	

}
