package model;

public class InvitationVeiw {
	String invitationName;
	String sender;
	String meetingID;
	String yourStatus;
	
	public InvitationVeiw(String sender, String invitationName, String meetingID){
		this.sender = sender;
		this.invitationName = invitationName;
		this.meetingID = meetingID;
	}
	
	public InvitationVeiw(String sender, String invitationName, String meetingID, String yourStatus){
		this.sender = sender;
		this.invitationName = invitationName;
		this.meetingID = meetingID;
		this.yourStatus = yourStatus;
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
