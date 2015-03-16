package model;


public class LoginUser extends User {

	private byte[] dbSalt; 
	private byte[] dbHash;
	
	public LoginUser(String username, String fristname, String lastname, String email, byte[] dbsalt, byte[] dbhash) {
		super(username, fristname, lastname, email);
		this.dbSalt = dbsalt; 
		this.dbHash = dbhash; 
	}
	
	public LoginUser(String username, String fristname, String lastname) {
		super(username, fristname, lastname, "test@test.no");
		Password pass = new Password("123456");
		this.dbSalt = pass.getSalt(); 
		this.dbHash = pass.getHash(); 
	}

    public LoginUser(String username, String fristname, String lastname, String passw) {
        super(username, fristname, lastname, "test@test.no");
        Password pass = new Password(passw);
        this.dbSalt = pass.getSalt();
        this.dbHash = pass.getHash();
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
		}
	}

	public byte[] getDBSalt() {
		return dbSalt;
	}

	public byte[] getDBHash() {
		return dbHash;
	}
}
