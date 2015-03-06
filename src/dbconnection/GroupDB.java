package dbconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import model.Group;
import model.User;

public class GroupDB extends DatabaseConnection{

	public static void addGroup(String name) {
		try {
			String addGroupQuery = "insert into groups (groupName)"  + "values(?)";
			PreparedStatement preparedStmt = con.prepareStatement(addGroupQuery);
			preparedStmt.setString (1, name);
			int res = preparedStmt.executeUpdate();
			if (res > 0) {
				System.out.println("Inserting Meeting worked");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void addMember(User user, Group group){
		try {
			String addGroupQuery = "insert into usergroup (username, groupName)"  + "values(?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(addGroupQuery);
			preparedStmt.setString (1, user.getUsername());
			preparedStmt.setString (2, group.getName());
			int res = preparedStmt.executeUpdate();
			if (res > 0) {
				System.out.println("Inserting Meeting worked");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public static void addParent(Group group, Group subGroup) {
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE groups SET parentID = '"+group.getName()+"' WHERE groupName = '" + subGroup.getName() + "'");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void removeMember(User user, Group group){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("DELETE FROM usergroup WHERE username = '" + user.getUsername() + "' AND groupName =  '"+group.getName()+"'");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void deleteGroup(Group group){
		try {
			Statement myStatement = con.createStatement(); 
			List<Group> childList = group.getSubGroup();
			for(Group g: childList){
				myStatement.executeUpdate("DELETE FROM groups WHERE groupName = '" + g.getName()+"'");
			}
			myStatement.executeUpdate("DELETE FROM groups WHERE groupName = '" + group.getName()+"'");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static HashMap<String, List<String>> getAllGroups(){
		HashMap<String, List<String>> groupMap = new HashMap<String,List<String>>();
		try {
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT * FROM groups");
			while (myRs.next()){
				if(groupMap.containsKey(myRs.getString(2))){
					groupMap.get(myRs.getString(2)).add(myRs.getString(1));
				}
				else{
					List<String> temp = new ArrayList<String>();
					temp.add(myRs.getString(1));
					groupMap.put(myRs.getString(2), temp);
				}
			} 
			return groupMap;
		}
			catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static Group getGroup(String name) {
		try {
			Statement myStatement = con.createStatement(); 
			ResultSet myRs = myStatement.executeQuery("SELECT * FROM groups WHERE groupName = '" + name + "'");
			myRs.next();
			return new Group
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
