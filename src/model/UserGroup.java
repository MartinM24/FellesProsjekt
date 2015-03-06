package model;

import java.util.ArrayList;

public class UserGroup {
	
    String name;
    String purpose;

	ArrayList<User> participants;

    // Subgrup fields
    private ArrayList<UserGroup> subGroups;


    UserGroup parentGroup;


    public UserGroup getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(UserGroup parentGroup) {
        this.parentGroup = parentGroup;
    }

    public UserGroup (User owner, String name){
		this.name = name;
	}

	public ArrayList<User> getParticipants() {
        return participants;
	}

	public void addParticipants(User participants) {
		this.participants.add(participants);
	}
	
	public void removeParticipant(User participant){
		this.participants.remove(participant);
	}

	public ArrayList<UserGroup> getSubGroups() {
        
        return subGroups;
	}
	
	public void addSubGroup(UserGroup subGroup) {
		if (!subGroups.contains(subGroup)) {
            this.subGroups.add(subGroup);
            subGroup.setParentGroup(this);
        } else {
            throw new IllegalArgumentException("Group is allready a subgrupe");
        }
	}
	
	public void removeSubGroup(UserGroup subGroup){
		this.subGroups.remove(subGroup);
	}

	public String getName() {
		return name;
	}
	
	
	

}
