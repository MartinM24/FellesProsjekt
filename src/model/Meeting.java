package model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import dbconnection.DatabaseConnection;
import dbconnection.MeetingDB;

//TODO add constructors
public class Meeting implements Comparable<Meeting>{

    private static final String DATABASE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String PRINT_TIME_FORMAT = "HH:mm";
    private int meetingID;
    private User owner;
    private Room room;
    private String place;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String description;
    private int nOfParticipant;
    private List<User> participants;


    /**
     * Constructor, used when first creating meeting. Adds meeting in db
     * If nOfParticipants is not set, set it to -1.
     *
     * @param owner
     * @param room
     * @param place
     * @param timeStart
     * @param timeEnd
     * @param description
     * @param nOfParticipant
     * @param participants
     */
    public Meeting(User owner, Room room, String place,
                   LocalDateTime timeStart, LocalDateTime timeEnd, String description,
                   int nOfParticipant, List<User> participants) {
        this.owner = owner;
        this.room = room;
        this.place = place;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.description = description;
        this.nOfParticipant = nOfParticipant;
        this.participants = participants;
        this.meetingID = MeetingDB.addMeeting(this);
        MeetingDB.addParticipant(this, owner, 1);
        //for(User user : participants){
        //	MeetingDB.addParticipant(this, user, 0);
        //}
    }


    /**
     * @param meetingID
     * @param owner
     * @param room
     * @param place
     * @param timeStart
     * @param timeEnd
     * @param description
     * @param nOfParticipant
     * @param participants
     */
    public Meeting(int meetingID, User owner, Room room, String place,
                   String timeStart, String timeEnd, String description,
                   int nOfParticipant, List<User> participants) {
        this.meetingID = meetingID;
        this.owner = owner;
        this.room = room;
        this.place = place;
        this.timeStart = convertStringToDate(timeStart);
        this.timeEnd = convertStringToDate(timeEnd);
        this.description = description;
        this.nOfParticipant = nOfParticipant;
        this.participants = participants;
    }


    public Meeting(int meetingID) {
        Meeting tempMeeting = MeetingDB.getMeeting(meetingID);
        this.meetingID = tempMeeting.meetingID;
        this.owner = tempMeeting.owner;
        this.room = tempMeeting.room;
        this.place = tempMeeting.place;
        this.timeStart = tempMeeting.timeStart;
        this.timeEnd = tempMeeting.timeEnd;
        this.description = tempMeeting.description;
        this.nOfParticipant = tempMeeting.nOfParticipant;
        this.participants = tempMeeting.participants;
    }



    private LocalDateTime convertStringToDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATABASE_DATE_FORMAT);

        return LocalDateTime.parse(str.split("\\.")[0], formatter);
    }


    /**
     * Methode som sjekker om bruker har tilgang
     *
     * @param user
     * @return om bruker har tilgang
     */
    public boolean hasAccess(User user) {
        if (user == owner) {
            return true;
        }
        return participants.contains(user);
    }

    public int getNOfParticipantSet() {
        return nOfParticipant;
    }

    public int getNOfParticipant() {
        if (nOfParticipant == -1) {
            return participants.size();
        } else {
            return nOfParticipant;
        }
    }

    /**
     * Method to get place that will be shown in the GUI, either the room or the place.
     *
     * @return
     */
    public String getGUIPlace() {
        if (room == null) {
            return place;
        } else {
            return room.getName();
        }
    }

    public void setMeetingID(int id) {
        this.meetingID = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMeetingID() {
        return meetingID;
    }

    public User getOwner() {
        return owner;
    }

    public Room getRoom() {
        return room;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public int getStartHour() {
        return timeStart.getHour();
    }

    public int getMinute() {
        return timeStart.getMinute();
    }

    public int getEndHour() {
        return timeEnd.getHour();
    }

    public int getEndMinute() {
        return timeEnd.getMinute();
    }

    public int getStartDayOfWeek() {
        return timeStart.getDayOfWeek().getValue();
    }

    public String getStartString(){
        return this.getTimeStart().format(DateTimeFormatter.ofPattern(PRINT_TIME_FORMAT));
    }

    public String getEndString(){
        return this.getTimeEnd().format(DateTimeFormatter.ofPattern(PRINT_TIME_FORMAT));

    }

    /**
     * Return the string to be saved in the database for the starttime
     *
     * @return the string for starttime
     */
    public String getStartDB() {
        Date dt = Date.from(timeStart.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat(DATABASE_DATE_FORMAT);
        return sdf.format(dt);
    }

    /**
     * Return the string to be saved in the database for the endtime
     *
     * @return the string for endtime
     */
    public String getEndDB() {
        Date dt = Date.from(timeEnd.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat(DATABASE_DATE_FORMAT);
        return sdf.format(dt);
    }


    @Override
    public String toString() {
        return "Meeting [dateFormat=" + DATABASE_DATE_FORMAT + ", meetingID=" + meetingID
                + ", owner=" + owner + ", room=" + room + ", place=" + place
                + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd
                + ", description=" + description + ", nOfParticipant="
                + nOfParticipant + ", participants=" + participants + "]";
    }


    @Override
    public int compareTo(Meeting o) {
        if (o.getTimeStart().isAfter(this.getTimeStart()))
            return 1;
        if (o.getTimeStart().isEqual(this.getTimeStart()))
            return 0;
        return -1;
    }

    public boolean doesOverlap(Meeting m) {
        boolean o1 = m.getTimeStart().isBefore(this.getTimeEnd()) && this.getTimeStart().isBefore(m.getTimeEnd()) ;
        //boolean o2 = this.getTimeStart().isBefore(m.getTimeStart()) && this.getTimeEnd().isAfter(m.getTimeStart());
        //boolean o3 = m.getTimeStart().equals(this.getTimeStart());
        //System.out.println("o3 " + o3 + " :" + m + " - " + this);
        return o1;
    }

    public int numberOfOverlap(List<Meeting> meetings) {
        int overlap = 0;
        for (Meeting m : meetings) {
            if (this != m) {
                if (this.doesOverlap(m)) {
                    overlap++;
                }
            }
        }
        return overlap;
    }

    public int getDayOfWeek(){
        return timeStart.getDayOfWeek().getValue();
    }

    public String getFXStyle() {
        return "-fx-background-color: #4986e7";
    }


}
