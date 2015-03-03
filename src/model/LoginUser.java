package model;


public class LoginUser extends User {

	private byte[] dbSalt; 
	private byte[] dbHash;
	private Calendar personalCalendar;

	public LoginUser(String username, String fristname, String lastname, String email, byte[] dbsalt, byte[] dbhash) {
		super(username, fristname, lastname, email);
		this.dbSalt = dbsalt; 
		this.dbHash = dbhash; 
		this.personalCalendar = new Calendar("My Calendar",this);
	}
	
	


/*	public LoginUser(String username, String fristname, String lastname, String email, String password) {
		super(username, fristname, lastname, email);
		Password d = new Password(password); 
		this.dbSalt = d.getSalt();
		this.dbHash = d.getHash();
	}*/
	
	/**
	 * Check if password is correct
	 * @param loginString
	 * @return boolean 
	 */
	public boolean checkPassword(String loginString) {
		Password d = new Password(loginString, dbSalt);
		return d.comparePassword(dbHash);
	}
	
	public void changePassword(String loginString) {
		if (checkPassword(loginString)) {
			Password d = new Password(loginString); 
			this.dbSalt = d.getSalt();
			this.dbHash = d.getHash();
			//TODO Update database password for user
		}
	}

	public byte[] getDBSalt() {
		return dbSalt;
	}

	public byte[] getDBHash() {
		return dbHash;
	}
	
	
}
