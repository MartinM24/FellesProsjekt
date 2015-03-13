package dbconnection;

import model.Meeting;
import model.Room;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 06.03.15.
 */
public class RoomDB extends DatabaseConnection {
    private final static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    private RoomDB() {
    }

    public static void addRoom(Room room){
        //TODO skal ikke kunne legge inn i meeting uten at det blir lagt inn i participant
        try {
            String meetingQuery = "insert into room (roomName, capacity)"  + "values(?, ?)";
            PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery);
            preparedMeetingStmt.setString(1, room.getName());
            preparedMeetingStmt.setInt(2, room.getCapacity());
            int res = preparedMeetingStmt.executeUpdate();
            if (res > 0) {
                System.out.println("Inserting room worked");
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public static void deleteRoom(Room room){
		try {
			Statement myStatement = con.createStatement(); 
			myStatement.executeUpdate("DELETE FROM room WHERE roomName = '" + room.getName() + "'");
			}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

    public static List<Room> getAllRooms(){
        List<Room> roomList = new ArrayList<>();
        try{
            Statement myStatement = con.createStatement();
            ResultSet myRs = myStatement.executeQuery("SELECT roomName FROM room");
            while (myRs.next()){
                roomList.add(getRoom(myRs.getString(1)));
            }
            System.out.println("Everything worked");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    public static Room getRoom(String name){
        try{
            Statement sqlSelect = con.createStatement();
            ResultSet myRs = sqlSelect.executeQuery("SELECT * FROM room WHERE roomName = '"+name+"'");
            myRs.next();
            return new Room(myRs.getString(1), myRs.getInt(2));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<ArrayList<LocalDateTime>> getAvailability(String name){
    	ArrayList<ArrayList<LocalDateTime>> availability = new ArrayList<ArrayList<LocalDateTime>>();
        try{
            Statement sqlSelect = con.createStatement();
            ResultSet myRs = sqlSelect.executeQuery("SELECT timeStart, timeEnd FROM meeting WHERE roomName = '"+name+"'");
            while(myRs.next()){
            	ArrayList<LocalDateTime> temp = new ArrayList<LocalDateTime>();
            	temp.add(0, convertStringToDate(myRs.getString(1)));
            	temp.add(1, convertStringToDate(myRs.getString(2)));
            	availability.add(temp);
            }
            return availability;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    private static LocalDateTime convertStringToDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        System.out.println(str);
        return LocalDateTime.parse(str.split("\\.")[0], formatter);
    }

}
