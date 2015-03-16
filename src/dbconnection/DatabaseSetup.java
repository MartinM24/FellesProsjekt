package dbconnection;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class DatabaseSetup extends DatabaseConnection{
	private final static String filepath = "Database/";
	
	protected DatabaseSetup(){
		super();
	}
	
	private static File[] finder( String dirName){
    	File dir = new File(dirName);

    	return dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt"); }
    	} );

    }
	
	private static String readFile(String path, Charset encoding) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
		
	
	private static List<String> getQueries(){
		File[] files = finder(filepath);
		List<String> queries = new ArrayList<String>();
		for (int i = 0 ; i  < files.length ; i++){
			queries.add(readFile(files[i].getAbsolutePath(),Charset.defaultCharset()));
		}
		
		return queries;
	}
	
	private static List<String> getTableNames(){
		File[] files = finder(filepath);
		List<String> tables = new ArrayList<String>();
		for( int i = 0 ; i < files.length ; i++){
			tables.add(files[i].getName());
		}
		return tables;
	}
	
	public static void createDatabase(){
		List<String> queries = getQueries();
		
		for (String sql : queries){
			Statement myStatement;
			try {
				myStatement = con.createStatement();
				myStatement.executeUpdate(sql);
			} catch (SQLTimeoutException e) {
				DatabaseConnection.startCon();
			}	catch (SQLException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public static void deleteDatabase(){
		List<String> tables = getTableNames();
		 
		try {
			con.createStatement();
			DatabaseMetaData md = con.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
				  tables.add(rs.getString(3));
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		for(String s : tables){
			try{
				Statement myStatement = con.createStatement();
				myStatement.executeUpdate("DROP TABLE "+s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DatabaseConnection.startCon();
		for (int i = 0 ; i < 5; i++){
			//deleteDatabase();
			createDatabase();
			
		}
	}
	
}
