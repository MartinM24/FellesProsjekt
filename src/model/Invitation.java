package model;

public class Invitation {
	
	private User sender;
	private User reciver;
	private Meeting meeting;
	private Group group;

	public Invitation(User sender, User reciver, Meeting meeting){
		this.sender = sender;
		this.reciver = reciver;
		this.meeting = meeting;
	}
	
	public Invitation(User sender, User reciver, Group group){
		this.sender = sender;
		this.reciver = sender;
		this.group = group;
	}
	
	public static InvitationVeiw makeInvitationVeiwMeeting(Invitation inv){
		return new InvitationVeiw(inv.getReciver().getFirstname() +" "+ inv.getReciver().getLastname(),inv.getMeeting().getDescription(), ""+inv.getMeeting().getMeetingID());
	}
	
	public User getSender() {
		return sender;
	}

	public User getReciver() {
		return reciver;
	}


	public Meeting getMeeting() {
		return meeting;
	}

	public Group getGroup() {
		return group;
	}


}
