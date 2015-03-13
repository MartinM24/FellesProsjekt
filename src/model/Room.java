package model;


public class Room {

    private final String name;
    private final int capacity;
    private String capacityString;
    private String avalibility;

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }



    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

	public String getAvalibility() {
		return avalibility;
	}
	
	public String getCapacityString() {
		return capacityString;
	}

    
    
}
