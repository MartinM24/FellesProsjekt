package dbconnection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import model.LoginUser;
import model.Meeting;
import model.User;

public class AddTestSet {
	private static ArrayList<User> users; 
	private static ArrayList<Meeting> meetings; 
	
	public static void main(String[] args) {
		users = new ArrayList<>();
		meetings = new ArrayList<>();
		DatabaseConnection.startCon();
		addTestUsers();
		addTestMeetigs();
	}

	private static void addTestUsers() {
		String[] first = {"Ole", "Per", "Espen", "Birk", "Magnus"};
		String[] last = {"Olsen", "Persen", "Espesen", "Birkesen", "Magnusen"};
		for (int i = 0; i < last.length; i++) {
			LoginUser user = new LoginUser("user" + Integer.toString(i+1), first[i], last[i]);
			users.add(user);
			//UserDB.addUser(user);
		}
	}
	
	private static void addTestMeetigs() {
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
			Meeting meeting = new Meeting(users.get(owner), null, "Hardanger vida", 
					timeStart , timeEnd, "Meeting" + Integer.toString(i), nrPar, parUser);
			meetings.add(meeting);
		}
	}

}
