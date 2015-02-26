package security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Password {
	private byte[] salt;
	private byte[] hash;
	
	public Password(String password){
		SecureRandom random = new SecureRandom();
	    salt = new byte[32];
	    random.nextBytes(salt);
	    createHash(password);
	}
	
	public Password(String password, byte[] salt){
		this.salt = salt;
		createHash(password);
	}
	
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
	
	public byte[] getHash(){
		return this.hash;
	}
	
	public byte[] getSalt(){
		return this.salt;
	}
	
	public boolean comparePassword(byte[] dbHash){
		return Arrays.equals(this.getHash(), dbHash);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Password pass = new Password("Test");
		byte[] dbHash = pass.getHash();
		byte[] dbSalt = pass.getSalt();
		Password trying = new Password("Test",dbSalt);
		System.out.println(trying.comparePassword(dbHash));
		Password correct = new Password("test",dbSalt);
		System.out.println(correct.comparePassword(dbHash));
	}
}
