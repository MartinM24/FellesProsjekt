package model;

import dbconnection.RoomDB;

public class Room {

    private final String name;
    private final int capacity;

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public Room(String name) {
        this.name = name;
        this.capacity = RoomDB.getRoom(name).getCapacity();
    }


    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
