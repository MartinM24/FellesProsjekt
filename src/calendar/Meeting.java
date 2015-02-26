package calendar;

import java.sql.Time;
import java.util.List;

import meetingroom.Room;
import usergroup.User;

public class Meeting {

	private String meetingID;
	private User owner;
	private Room room;
	private String place;
	private Time timeStart;
	private Time timeEnd;
	private String description;
	private List<User> participants;
	
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	
	
	public Time getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

	public Time getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getOwner(){
		return owner;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public String getDescription(){
		return description;
	}
	
	public List<User> getParticipants(){
		return participants;
	}
/* Møteklassen skal inneholde: 
 * 
 *  En konstruktør med de nødvendige atributtene for et møte
 *  Roomid, Beskrivelse, DatoStart, DatoSlutt, TidStart, TidSlutt
 *  
 *  Get-ere og set-ere for hver av disse atributtene.  
 * 
 *  En metode for å sende denne informasjonen til en database. 
 *   
 * 	Når et møte blir laget vil det få en møteID. Som skal være unik. 
 * 
 * 
 */

}
