package model;

import java.sql.Time;
import java.util.List;

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
/* M�teklassen skal inneholde: 
 * 
 *  En konstrukt�r med de n�dvendige atributtene for et m�te
 *  Roomid, Beskrivelse, DatoStart, DatoSlutt, TidStart, TidSlutt
 *  
 *  Get-ere og set-ere for hver av disse atributtene.  
 * 
 *  En metode for � sende denne informasjonen til en database. 
 *   
 * 	N�r et m�te blir laget vil det f� en m�teID. Som skal v�re unik. 
 * 
 * 
 */

}
