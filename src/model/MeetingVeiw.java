package model;

public class MeetingVeiw {
	String date;
	String timeFrom;
	String timeToo;
	String title;
	String place; 
	String room;
	String status;
	String user;
	
	public MeetingVeiw(String date, String timeFrom,String timeToo, String title, String place, String room, String status){
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeToo = timeToo;		
		this.title = title;
		this.place = place;
		this.room = room;
		this.status = status;
	}
	
	public MeetingVeiw(String date, String timeFrom,String timeToo, String title, String user, String status){
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeToo = timeToo;		
		this.title = title;
		this.user = user;
		this.status = status;
	}
	
	public String getDate() {
		return date;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public String getTimeToo() {
		return timeToo;
	}

	public String getTitle() {
		return title;
	}

	public String getPlace() {
		return place;
	}

	public String getRoom() {
		return room;
	}

	public String getStatus() {
		return status;
	}

	public String getUser() {
		return user; 
	}
	
}
