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
	
	public String getInvitationName() {
		return invitationName;
	}


	public String getMeetingID(){
		return meetingID;
	}
	
	public String getSender() {
		return sender;
	}
}
