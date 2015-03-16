package dbconnection;

import model.Group;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupDB extends DatabaseConnection{

	public static void addGroup(String name) {
		try {
			String addGroupQuery = "insert into groups (groupName)"  + "values(?)";
			PreparedStatement preparedStmt = con.prepareStatement(addGroupQuery);
			preparedStmt.setString(1, name);
            preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static String getParent(Group group){
		try {
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT parentID FROM groups WHERE groupName = '"+group.getName()+"'");
			myRs.next();
			if(myRs.wasNull()){
				return null;
			}
			return myRs.getString(1);
		}
			catch (Exception e) {
                e.printStackTrace();

                System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static void addMember(User user, Group group){
		try {
			String addGroupQuery = "insert into usergrouplink (username, groupName)"  + "values(?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(addGroupQuery);
			preparedStmt.setString (1, user.getUsername());
			preparedStmt.setString (2, group.getName());
            preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public static void addParent(Group parentGroup, Group group) {
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE groups SET parentID = '"+parentGroup.getName()+"' WHERE groupName = '" + group.getName() + "'");
		} catch (Exception e) {
            e.printStackTrace();

            System.out.println(e.getMessage());
		}
	}
	
	public static void removeParent(Group group){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("UPDATE groups SET parentID = 'NULL' WHERE groupName = '" + group.getName() + "'");
		} catch (Exception e) {
            e.printStackTrace();

            System.out.println(e.getMessage());
		}
	}
	
	public static void removeMember(User user, Group group){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("DELETE FROM usergrouplink WHERE username = '" + user.getUsername() + "' AND groupName =  '"+group.getName()+"'");
		} catch (Exception e) {
            e.printStackTrace();

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
            e.printStackTrace();

            System.out.println(e.getMessage());
		}
	}
	
	public static List<String> getAllGroups(String user){
		List<String> allGroups = getallGroups();
        List<String> res = new ArrayList<>();
		for(String str : allGroups){
			boolean isInGroup = false;
			Group parentGroup = new Group(str);
			for(User usr: parentGroup.getAllMembers()){
				if(usr.getUsername().equals(user)){
					isInGroup = true;
				}				
			}
			if(isInGroup){
				res.add(str);
			}
				
		}
		return res;
	}
	
	public static List<String> getallGroups(){
		List<String> rList = new ArrayList<String>();
		try {
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT groupName FROM groups");
			while (myRs.next()){
				rList.add(myRs.getString(1));
			} 
			return rList;
		}
		catch (Exception e) {
            e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public static HashMap<String, List<String>> getAllGroupsHash(){
		HashMap<String, List<String>> groupMap = new HashMap<String,List<String>>();
		try {
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT groupName, parentID FROM groups");
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
                e.printStackTrace();
                System.out.println(e.getMessage());
		}
		return null;
	}

	public static List<User> getAllMembers(String groupName) {
		try {
			Statement myStatement = con.createStatement(); 
			ResultSet myRs = myStatement.executeQuery("SELECT username FROM usergrouplink WHERE groupName = '" + groupName + "'");
			List<User> returnList = new ArrayList<>();
			while(myRs.next()){
				returnList.add(UserDB.getUser((myRs.getString(1))));
			}
			return returnList;
		} catch (Exception e) {
            e.printStackTrace();

            System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	public static boolean groupExist(String groupName){
		try{
			Statement mysStatement = con.createStatement();
			ResultSet myRs = mysStatement.executeQuery("select count(*) from groups where groupName = '"+ groupName +"'");
			myRs.first();
			return (myRs.getInt(1) > 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return true;
	}
}
