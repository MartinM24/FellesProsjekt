package model;

public class InvitationVeiw {
	String invitationName;
	String sender;
	String meetingID;
	
	public InvitationVeiw(String sender, String invitationName, String meetingID){
		this.sender = sender;
		this.invitationName = invitationName;
		this.meetingID = meetingID;
	}
	
	public String getMeetingID(){
		return meetingID;
	}
}
