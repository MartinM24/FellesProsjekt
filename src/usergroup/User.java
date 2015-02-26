package usergroup;

public class User {
	
	
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private String username;
	
	//Her skal det være felter med kalendere, grupper og møter. 

	public User(String username, String fristname, String lastname, String email, String password) {
		this.username = username;
		this.firstname = fristname;
		this.lastname = lastname;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
