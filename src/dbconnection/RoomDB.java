package dbconnection;

import model.Room;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 06.03.15.
 */
public class RoomDB extends DatabaseConnection {

    private RoomDB() {
    }

    public static int addRoom(Room room){
        //TODO skal ikke kunne legge inn i meeting uten at det blir lagt inn i participant
        try {
            String meetingQuery = "insert into room (roomName, capacity)"  + "values(?, ?)";
            PreparedStatement preparedMeetingStmt = con.prepareStatement(meetingQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedMeetingStmt.setString(1, room.getName());
            preparedMeetingStmt.setInt(2, room.getCapacity());
            int res = preparedMeetingStmt.executeUpdate();
            if (res > 0) {
                System.out.println("Inserting room worked");
            }
            ResultSet tableKeys = preparedMeetingStmt.getGeneratedKeys();
            tableKeys.next();
            return tableKeys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static List<Room> getAllRooms(User user){
        List<Room> roomList = new ArrayList<>();
        try{
            Statement myStatement = con.createStatement();
            ResultSet myRs = myStatement.executeQuery("SELECT room.roomID FROM room");
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
            ResultSet myRs = sqlSelect.executeQuery("select * from room WHERE roomName = '"+name+"'");
            myRs.next();
            return new Room( myRs.getString(1), myRs.getInt(2));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
