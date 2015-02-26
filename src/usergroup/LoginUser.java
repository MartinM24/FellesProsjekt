package usergroup;

import security.Password;

public class LoginUser extends User {

	private byte[] dbSalt; 
	private byte[] dbHash;

	public LoginUser(String username, String fristname, String lastname, String email, byte[] dbsalt, byte[] dbhash) {
		super(username, fristname, lastname, email);
		this.dbSalt = dbsalt; 
		this.dbHash = dbhash; 
	}
	
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
	
	
}
