package model;

public class RoomVeiw {
	
    private String name;
	private String capacityString;
	private String avalibility;
	private int capacity;

	public RoomVeiw(String name, String capacity, String avalibility) {
    	this.name = name;
    	this.capacityString = capacity;
    	this.avalibility = avalibility;
    	this.capacity = Integer.parseInt(capacity);
    }

	public String getName() {
		return name;
	}

	public String getCapacityString() {
		return capacityString;
	}

	public String getAvalibility() {
		return avalibility;
	}

	public int getCapacity() {
		return capacity;
	}

}
