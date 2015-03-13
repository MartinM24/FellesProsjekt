package gui;

import model.Meeting;

import java.util.ArrayList;

/**
 * Created by Martin on 11.03.15.
 */
public class Day{
    private ArrayList<ArrayList<Meeting>> columns;
    private int firstColumnIndex;

    public Day(int numberOfColumns, int firstColumnIndex){
        this.firstColumnIndex = firstColumnIndex;
        columns = new ArrayList<>();
        for (int i = 0; i < numberOfColumns; i++) {
            columns.add(new ArrayList<>());
        }
    }

    public int getNumberOfColumns(){
        return columns.size();
    }

    public ArrayList<Meeting> getColumn(int i){
        return columns.get(i);
    }

    public void addMeetingToColumn(Meeting meeting, int i) {
        columns.get(i).add(meeting);
    }

    public int getFirstColumnIndex() {
        return firstColumnIndex;
    }
}
