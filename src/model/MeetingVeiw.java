package model;

public class MeetingVeiw {
	private String date;
	private String timeFrom;
	private String timeToo;
	private String title;
	private String place;
	private String room;
	private String status;
	private String user;
    private int meetingID;
	
	public MeetingVeiw(int meetingID, String date, String timeFrom,String timeToo, String title, String place, String room, String status){
		this.setDate(date);
		this.setTimeFrom(timeFrom);
		this.setTimeToo(timeToo);
		this.setTitle(title);
		this.setPlace(place);
		this.setRoom(room);
		this.setStatus(status);
        this.setMeetingID(meetingID);
	}
	
	public MeetingVeiw(String user, String status){
		this.setUser(user);
		this.setStatus(status);
	}

    public int getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(int meetingID) {
        this.meetingID = meetingID;
    }

    public String getDate() {
		return date;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public String getTimeToo() {
		return timeToo;
	}

	public String getTitle() {
		return title;
	}

	public String getPlace() {
		return place;
	}

	public String getRoom() {
		return room;
	}

	public String getStatus() {
		return status;
	}

	public String getUser() {
		return user; 
	}

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public void setTimeToo(String timeToo) {
        this.timeToo = timeToo;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
