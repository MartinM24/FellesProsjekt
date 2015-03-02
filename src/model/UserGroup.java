package model;

import java.util.ArrayList;

public class UserGroup {
	
	ArrayList<UserGroup> subGroup;
	ArrayList<User> participants;
	User owner;
	String name;
	
	
	public UserGroup (User owner, String name){
		this.owner = owner;
		this.name = name;
	}


	public ArrayList<User> getParticipants() {
		return participants;
	}


	public void addParticipants(User participants) {
		this.participants.add(participants);
	}
	
	public void rmParticipant(User participant){
		if (participant == owner){
			System.out.println("Can't delete owner");
		} else {
			this.participants.remove(participant);						
		}
	}


	public ArrayList<UserGroup> getSubGroup() {
		return subGroup;
	}
	
	public void addSubGroup(UserGroup subGroup) {
		this.subGroup.add(subGroup);
	}
	
	public void rmSubGroup(UserGroup subGroup){
		this.subGroup.remove(subGroup);						
	}


	public User getOwner() {
		return owner;
	}


	public String getName() {
		return name;
	}
	
	
	

}
