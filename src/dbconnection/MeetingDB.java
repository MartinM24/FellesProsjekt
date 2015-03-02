package dbconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.LoginUser;
import model.Meeting;
import model.User;

public class MeetingDB extends DatabaseConnection{
	
	
	public static List<Meeting> getAllMeetings(User user){
		List<Meeting> meetingList = new ArrayList<Meeting>();
		try{
			Statement myStatement = con.createStatement();
			ResultSet myRs = myStatement.executeQuery("SELECT *FROM meeting INNER JOIN participant INNER JOIN users WHERE users.username='"+user.getUsername()+"'");
			while (myRs.next()){
				meetingList.add(new Meeting(myRs.getString(1),user,myRs.getString(5),myRs.getTimestamp(3),myRs.getTimestamp(4),myRs.getString(2),new ArrayList<User>()));
			};
			System.out.println("Everything worked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return meetingList;
	}
	
	
}
