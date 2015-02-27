package security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Password {
	private byte[] salt;
	private byte[] hash;
	
	/**
	 * Constructor to create password for new user
	 * @param password given by user
	 */
	public Password(String password){
		SecureRandom random = new SecureRandom();
	    salt = new byte[32];
	    random.nextBytes(salt);
	    createHash(password);
	}
	
	/**
	 * Constructor to create password for user in database
	 * @param password given by user
	 * @param salt from database
	 */
	public Password(String password, byte[] salt){
		this.salt = salt;
		createHash(password);
	}
	
	/**
	 * Creates hash from given password
	 * @param password given by user
	 */
	private void createHash(String password){
		try {
			byte[] passwordArray = password.getBytes("UTF-8");
			
			byte[] saltedPassword = new byte[passwordArray.length+32];
			
			System.arraycopy(salt, 0, saltedPassword, 0, 32);
			System.arraycopy(passwordArray, 0, saltedPassword, 32, passwordArray.length);
			
			try {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA-256");	
				md.update(saltedPassword); // Change this to "UTF-16" if needed
				hash = md.digest();
			
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Return the hash
	 * @return the hash
	 */
	public byte[] getHash(){
		return this.hash;
	}
	
	/**
	 * Return the salt
	 * @return the salt
	 */
	public byte[] getSalt(){
		return this.salt;
	}
		
	/**
	 * Compares this password with a given hash
	 * @param dbHash hash from database
	 * @return the password equals the hash
	 */
	public boolean comparePassword(byte[] dbHash){
		return Arrays.equals(this.getHash(), dbHash);
	}
	
}
