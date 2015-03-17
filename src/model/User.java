package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String firstname;

	private String lastname;
	private String email;
	private String username;
	private List<Group> groups;

	@Override
	public String toString() {
		return "User [firstname=" + firstname + ", lastname=" + lastname
				+ ", email=" + email + ", username=" + username + ", groups="
				+ groups + "]";
	}
	
	public User(String username, String fristname, String lastname, String email) {
		this.username = username;
		this.firstname = fristname;
		this.lastname = lastname;
		this.email = email;
		groups = new ArrayList<Group>();
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
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public void addGroup(Group group){
		this.groups.add(group);
	}
	
	public void removeGroup(Group group){
		this.groups.remove(group);
	}

    @Override
    public boolean equals(Object object) {
        return this.getUsername().trim().equals(((User) object).getUsername().trim());
    }
}
