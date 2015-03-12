package model;

public class MeetingVeiw {
	String date;
	String timeFrom;
	String timeToo;
	String title;
	String place; 
	String room;
	String status;
	
	public MeetingVeiw(String date, String timeFrom,String timeToo, String title, String place, String room, String status){
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeToo = timeToo;		
		this.title = title;
		this.place = place;
		this.room = room;
		this.status = status;
	}

}
