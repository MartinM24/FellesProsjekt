package usergroup;

import java.util.ArrayList;

import security.Password;

public class User {
	
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private ArrayList<UserGroup> groups;
	
	public User(String username, String fristname, String lastname, String email) {
		this.username = username;
		this.firstname = fristname;
		this.lastname = lastname;
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}
	
	public ArrayList<UserGroup> getGroups() {
		return groups;
	}
	
	public void addGroup(UserGroup group){
		this.groups.add(group);
	}
	
	public void rmGroup(UserGroup group){
		this.groups.remove(group);
	}
}
