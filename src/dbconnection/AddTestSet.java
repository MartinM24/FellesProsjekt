package dbconnection;

import model.LoginUser;
import model.Meeting;
import model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class AddTestSet {
	private static ArrayList<User> users; 
	private static ArrayList<Meeting> meetings; 
	
	public static void main(String[] args) {
		users = new ArrayList<>();
		meetings = new ArrayList<>();
		DatabaseConnection.startCon();
		addTestUsers();
		addTestMeetings();
	}

	private static void addTestUsers() {
		String[] first = {"Ole", "Per", "Espen", "Birk", "Magnus"};
		String[] last = {"Olsen", "Persen", "Espesen", "Birkesen", "Magnusen"};
		for (int i = 0; i < last.length; i++) {
			LoginUser user = new LoginUser("user" + Integer.toString(i+1), first[i], last[i]);
			users.add(user);
			UserDB.addUser(user);
		}
	}
	
	private static void addTestMeetings() {
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			int owner = rnd.nextInt(5);
			int par = rnd.nextInt(5);
			ArrayList<User> parUser = new ArrayList<>();
			parUser.add(users.get(par));
			int nrPar = rnd.nextInt(7);
			long start = rnd.nextInt(6000);
			long end = rnd.nextInt(300);
			LocalDateTime timeStart = LocalDateTime.now().plusMinutes(start); 
			LocalDateTime timeEnd = LocalDateTime.now().plusMinutes(start).plusMinutes(end);
			Meeting meeting = new Meeting(users.get(owner), null, "Hardanger vida", timeStart , timeEnd, "Meeting" + Integer.toString(i), nrPar, parUser);
            MeetingDB.addMeeting(meeting);
			meetings.add(meeting);
		}
	}

}
