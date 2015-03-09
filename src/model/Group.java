package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dbconnection.GroupDB;

public class Group implements Iterable<User>{
	private String name;
	private List<Group> subGroups;
	private List<User> members;
	
	
	/**
	 * Constructor when getting group from database
	 * @param name
	 */
	public Group (String name){
		this.name = name;
		subGroups = new ArrayList<Group>();
		makeChildren();
		members = GroupDB.getAllMembers(name);
		members.addAll(getAllMembers(this, members));
	}

	public List<User> getAllMembers(Group group, List<User> listAllMembers){
		for(int i = 0; i < subGroups.size(); i++){
			listAllMembers.addAll(getAllMembers(subGroups.get(i), listAllMembers));
		}
		return listAllMembers;
		
	}
		
	
	
	/**
	 * Constructor when making a new group
	 * @param name
	 * @param parentGroup
	 */
	public Group (String name, Group parentGroup){
		this(name);
		GroupDB.addGroup(name);
		if(parentGroup==null){
			GroupDB.addParent(parentGroup, this);
		}
	}

	private void makeChildren(){
		HashMap<String, List<String>> groupMap = GroupDB.getAllGroups();
		List<String> currentParent = new ArrayList<String>();
		currentParent.add(this.getName());
		while(!currentParent.isEmpty() || groupMap.containsKey(currentParent.get(0))){
			for(String s: groupMap.get(currentParent.remove(0))){
				subGroups.add( new Group(s));
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
	
	
	/**
	 * Method to add subgroup
	 * @param subGroup
	 * @return False if the subgroup is one of this group's ancestors
	 */
	public boolean addSubGroup(Group subGroup) {
		Group currentGroup = this;
		while(currentGroup.getParent()!=null){
			currentGroup = currentGroup.getParent();
			if (currentGroup.getName()==subGroup.getName()){
				return false;
			}
		}
		this.subGroups.add(subGroup);
		GroupDB.addParent(this, subGroup);
		return true;
	}
	
	private Group getParent(){
		return new Group(GroupDB.getParent(this));
	}
	
	public void removeSubGroup(Group subGroup){
		this.subGroups.remove(subGroup);						
	}

	public String getName() {
		return name;
	}


	@Override
	public Iterator<User> iterator() {
		return members.iterator();
	}
}
