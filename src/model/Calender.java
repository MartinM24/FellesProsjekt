package model;

public class Calender {
	
	int calenderID;
	String description;
	
	public Calender(int calenderID, String description){
		this.calenderID = calenderID;
		this.description = description;
	}

	public int getCalenderID() {
		return calenderID;
	}

	public void setCalenderID(int calenderID) {
		this.calenderID = calenderID;
		//TODO DBcommunicasjon
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		//TODO DBcomminicasjon
	}

}
