package model;

public class InvitationVeiw {
	String invitationName;
	String sender;
	String meetingID;
	String yourStatus;
	String timeTo;
	String timeFrom;
	String date;
	
	public InvitationVeiw(String sender, String invitationName, String meetingID){
		this.sender = sender;
		this.invitationName = invitationName;
		this.meetingID = meetingID;
	}
	
	public InvitationVeiw(String invitationName, String meetingID, String yourStatus, String timeTo, String timeFrom, String date){
		this.invitationName = invitationName;
		this.meetingID = meetingID;
		this.yourStatus = yourStatus;
		this.timeTo = timeTo;
		this.timeFrom = timeFrom;
		this.date = date; 
	}
	
	
	
	public String getTimeTo() {
		return timeTo;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public String getDate() {
		return date;
	}

	public String getInvitationName() {
		return invitationName;
	}


	public String getMeetingID(){
		return meetingID;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getYourStatus(){
		return yourStatus;
	}
}
