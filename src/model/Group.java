package model;

import java.util.ArrayList;
import java.util.HashMap;
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
			//System.out.println("Group(name, parent, members) : Member: "+members.get(i).getUsername());
			addMember(members.get(i));
		}
	}
	
	public List<User> getAllMembers(){
		List<User> directMembers = GroupDB.getAllMembers(this.getName());
		//System.out.println("Group : getAllMembers()"+directMembers);
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
		for(int i = 0; i < group.subGroups.size(); i++){
			//listAllMembers.addAll(getAllMembers(group.subGroups.get(i), listAllMembers));
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
		HashMap<String, List<String>> groupMap = GroupDB.getAllGroupsHash();
		System.out.println(this.getName()+"groupMap size = "+groupMap.size());
		List<String> currentParent = new ArrayList<String>();
		currentParent.add(this.getName());
		while(!currentParent.isEmpty() && groupMap.containsKey(currentParent.get(0))){
			String tempPar = currentParent.remove(0);
			if(groupMap.containsKey(tempPar)){
				for(String s: groupMap.get(tempPar)){
					subGroups.add( new Group(s));
					currentParent.add(s);
				}
			}
			
		}
	}
	
	
	public List<User> getMembers() {
		return members;
	}


	public void addMember(User member) {
		System.out.println("Group-addMember : member = "+member.getUsername());
		if(!this.members.contains(member)){
			//System.out.println("Group-addMember : In the if");
			this.members.add(member);
			GroupDB.addMember(member, this);
		}
	}
	
	public void removeMember(User member){
		this.members.remove(member);	
		//System.out.println("Group-removeMember(member):member = "+member.getUsername());
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
