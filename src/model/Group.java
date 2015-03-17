package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	/**
	 * Constructor that adds group to the database
	 * @param name
	 * @param parent
	 * @param members
	 */
	public Group(String name, Group parent, List<User> members){
		this.name = name;
		GroupDB.addGroup(name);
		if(parent!=null){
			GroupDB.addParent(parent, this);
		}
		this.members = new ArrayList<User>();
		subGroups = new ArrayList<Group>();
		for(int i = 0 ; i < members.size() ; i++){
			addMember(members.get(i));
		}
	}
	
	public List<User> getAllMembers(){
		List<User> directMembers = GroupDB.getAllMembers(this.getName());
		Set<User> directMembersSet = new HashSet<User>();
		directMembersSet.addAll(directMembers);
		for(int i = 0 ; i < subGroups.size() ; i++){
			directMembersSet.addAll(subGroups.get(i).getAllMembers());
		}
		//directMembers.clear();
		directMembers.addAll(directMembersSet);
		return directMembers;
	}

	public List<User> getAllMembers(Group group, List<User> listAllMembers){
		listAllMembers.addAll(GroupDB.getAllMembers(group.getName()));
//		for(int i = 0; i < group.subGroups.size(); i++){
			//listAllMembers.addAll(getAllMembers(group.subGroups.get(i), listAllMembers));
//		}
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
		List<String> subGroupNames = GroupDB.getSubGroups(this.getName());
		for(String groupName : subGroupNames){
			subGroups.add(new Group(groupName));
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
