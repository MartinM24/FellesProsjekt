package usergroup;

import security.Password;

public class User {
	
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	
	//Her skal det v�re felter med kalendere, grupper og m�ter. 
	
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

	public void setLastname(String lastname) {
		this.lastname = lastname;
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


	
}
