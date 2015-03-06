package model;

import dbconnection.RoomDB;

public class Room {

    private final int roomID;
    private final String name;
    private final int capacity;

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.roomID = RoomDB.addRoom(this);
    }

    public Room(int roomID, String name, int capacity) {
        this.roomID = roomID;
        this.name = name;
        this.capacity = capacity;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
