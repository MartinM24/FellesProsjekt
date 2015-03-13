package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.Meeting;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 11.03.15.
 */
public class CalendarPane extends GridPane {

    public static final String DAY_STYLE = "-fx-border-color: transparent gray transparent gray;" +
            "-fx-background-color: transparent;" +
            "-fx-border-width: 1;";
    public static final String HOUR_STYLE = "-fx-border-color: transparent transparent darkgray transparent;" +
            "-fx-background-color: transparent;" +
            "-fx-border-width: 1;";

    //Holds meetings first dimention weekDay second column number
    private ArrayList<Day> days;



    /**
     * Draws the required number of columns to show overlapping meetings
     *
     * @param overlaps number of meetings that numberOfColumns on Monday
     */
    public void drawCalendarGrid(int[] overlaps) {
        //set up a ArrayList to the size of calendarGrid
        days = new ArrayList<>();
        int columnIndex = 1;
        for (int i = 0; i < overlaps.length; i++) {
            days.add(new Day(overlaps[i] + 1, columnIndex));
            columnIndex = columnIndex + overlaps[i] + 1;
        }
        addTimeline();
        drawRows();
        drawWeek(overlaps);
        styleRows();
        styleColumns(overlaps);
        this.setPrefHeight(1500.0);
    }

    private int getNumberOfColumns(int weekDay){
        return days.get(weekDay-1).getNumberOfColumns();
    }

    private void addTimeline() {
        this.getColumnConstraints().add(makeColumn(12.5));
        Region region = new Region();
        region.setStyle(DAY_STYLE);
        for (int i = 0; i < 24; i ++) {
            String time = LocalTime.of(i, 0).format(DateTimeFormatter.ofPattern("HH:mm"));
            Label label = new Label(time);
            label.setAlignment(Pos.CENTER);
            this.add(label, 0, i*4, 1, 4);
        }

        this.add(region, 0, 0, 1, 24 * 4);
    }

    public void addDay(int maxOverlap) {
        int numberOfColumns = maxOverlap + 1;
        List<ColumnConstraints> colums = new ArrayList<>();
        for (int i = 0; i < numberOfColumns; i++) {
            colums.add(makeColumn(12.5 / numberOfColumns));
        }
        this.getColumnConstraints().addAll(colums);
    }


    private void styleRows() {
        int width = this.getColumnConstraints().size();
        for (int i = 0; i < 24 * 4; i += 4) {
            Region region = new Region();
            region.setStyle(HOUR_STYLE);
            this.add(region, 0, i, width, 4);
        }
    }

    private void styleColumns(int[] overlaps) {
        int dayStart = 1;
        for (int i = 0; i < overlaps.length; i++) {
            int dayWidth = overlaps[i] + 1;
            Region region = new Region();
            region.setStyle(DAY_STYLE);
            this.add(region, dayStart, 0, dayWidth, 24 * 4);
            dayStart += dayWidth;
        }

    }

    private void drawWeek(int[] overlaps) {
        for (int i = 0; i < overlaps.length; i++) {
            addDay(overlaps[i]);
        }
    }

    private void drawRows() {
        List<RowConstraints> rows = new ArrayList<>();
        for (int i = 0; i < 24 * 4; i++) {
            rows.add(makeRow(100 / (24 * 4)));
        }
        this.getRowConstraints().addAll(rows);
    }

    private RowConstraints makeRow(double percent) {
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(percent);
        return row;
    }

    private ColumnConstraints makeColumn(double percent) {
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(percent);
        return column;
    }

    public void clear() {
        this.getChildren().clear();
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();
        if (days != null) {
            this.days.clear();
        }
    }


    public int getPosFromTime(LocalTime time) {
        int kvarter = time.getHour() * 4;
        if (time.getMinute() < 15)
            kvarter += 1;
        else if (time.getMinute() < 30)
            kvarter += 2;
        else if (time.getMinute() < 45)
            kvarter += 3;
        else kvarter += 4;
        return kvarter -1;
    }

    public void addMeeting(Meeting meeting, int numOverlaps){
        int dayOfWeek = meeting.getDayOfWeek()-1;
        Day day = days.get(dayOfWeek);
        int numColumn = day.getNumberOfColumns();
        int column = 0;
        while(column < numColumn) {
            if (meeting.numberOfOverlap(day.getColumn(column)) > 0) {
                column ++;
                continue;
            }
            int columnIndex = day.getFirstColumnIndex() + column;
            int rowIndex =  getPosFromTime(meeting.getTimeStart().toLocalTime());
            int rowSpan = getPosFromTime(meeting.getTimeEnd().toLocalTime()) - rowIndex;
            int columnSpan = 1; //day.getNumberOfColumns() - numOverlaps;
            System.out.println(meeting + " " + numOverlaps);
            day.addMeetingToColumn(meeting, column);
            this.add(new MeetingPane(meeting), columnIndex, rowIndex, columnSpan, rowSpan);
            break;
        }

    }

}
