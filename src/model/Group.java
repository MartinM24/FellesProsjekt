package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dbconnection.GroupDB;

public class Group {
	private String name;
	private List<Group> subGroups;
	private List<User> members;
	
	
	public Group (String name){
		this.name = name;
		members = new ArrayList<User>();
		subGroups = new ArrayList<Group>();
		GroupDB.addGroup(name);
		makeChildren();
	}

	private void makeChildren(){
		HashMap<String, List<String>> groupMap = GroupDB.getAllGroups();
		List<String> currentParent = new ArrayList<String>();
		currentParent.add(this.getName());
		while(!currentParent.isEmpty() || groupMap.containsKey(currentParent.get(0))){
			for(String s: groupMap.get(currentParent.remove(0))){
				subGroups.add(GroupDB.getGroup(s));
				currentParent.add(s);
			}
			
		}
	}
	

	
	public List<User> getMembers() {
		return members;
	}


	public void addMember(User member) {
		if(!this.members.contains(member)){
			this.members.add(member);
			GroupDB.addMember(member, this);
		}
	}
	
	public void removeMember(User member){
		this.members.remove(member);		
		GroupDB.removeMember(member, this);
	}


	public List<Group> getSubGroup() {
		return subGroups;
	}
	
	public void addSubGroup(Group subGroup) {
		this.subGroups.add(subGroup);
		GroupDB.addParent(this, subGroup);
	}
	
	public void removeSubGroup(Group subGroup){
		this.subGroups.remove(subGroup);						
	}

	public String getName() {
		return name;
	}
}
