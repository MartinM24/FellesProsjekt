package model;

import dbconnection.CalendarDB;

public class Calendar {
	
	int calenderID;
	String name;
	
	public Calendar(int calenderID, String name){
		this.calenderID = calenderID;
		this.name = name;
	}
	
	public Calendar(String name){
		this.name = name;
	}

	public int getCalenderID() {
		return calenderID;
	}

	public void setCalenderID(int calenderID) {
		this.calenderID = calenderID;
		//TODO DBcommunicasjon
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		//TODO DBcomminicasjon
	}


}
