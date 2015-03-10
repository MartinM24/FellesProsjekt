package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Meeting;
import model.User;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements ControlledScreen, Initializable {
	MainController myController;
    @FXML GridPane calendarGrid;
    private int diffWeeksFromToday = 0;
    private LocalDateTime today;
    private List<VBox> meetingElements;


     @Override
    public void initialize(URL location, ResourceBundle resources) {
         meetingElements = new ArrayList<>();
    }


    @FXML
    private void nextWeekClick(ActionEvent e) {
        diffWeeksFromToday++;
        viewRefresh();
    }

    @FXML
    private void lastWeekClick(ActionEvent e) {
        diffWeeksFromToday--;
        viewRefresh();
    }

    @Override
    public void viewRefresh() {
        clearCalendar();
        today = LocalDateTime.now();

        Meeting m = new Meeting(1, null, null, "Her", "2015-03-10 13:00:00", "2015-05-01 13:00:00", "Test", 0, new ArrayList<User>());
        if (isInWeek(m)) {
            paintMeeting(m);
        }
        //showMeetings();
    }

    public void clearCalendar() {
        calendarGrid.getChildren().removeAll(meetingElements);
        meetingElements.clear();
    }

	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage; 
	}

    public void showMeetings(List<Meeting> meetings) {
        for (int i = 0, meetingsSize = meetings.size(); i < meetingsSize; i++) {
            Meeting m = meetings.get(i);
            
            paintMeeting(m);
        }
    }

    private void paintMeeting(Meeting meeting) {
        int startDayOfWeek;
        LocalTime startTime;
        int endDayOfWeek;
        LocalTime endTime;

        if(meeting.getTimeStart().toLocalDate().isBefore(getStartWeekDate(today).plusWeeks(diffWeeksFromToday))) {
            startDayOfWeek = 1;
            startTime = LocalTime.MIN;
        } else {
            startDayOfWeek = meeting.getStartDayOfWeek();
            startTime = meeting.getTimeStart().toLocalTime();
        }

        if(meeting.getTimeEnd().toLocalDate().isAfter(getEndWeekDate(today).plusWeeks(diffWeeksFromToday))) {
            endDayOfWeek = 7;
            endTime = LocalTime.MAX;
        } else {
            endDayOfWeek = meeting.getTimeEnd().getDayOfWeek().getValue();
            endTime = meeting.getTimeEnd().toLocalTime();
        }

        Label description = new Label(meeting.getDescription());
        Label time = new Label(meeting.getStartString() + " - " + meeting.getEndString());
        description.wrapTextProperty().set(true);
        time.wrapTextProperty().set(true);

        for (int i = startDayOfWeek; i <= endDayOfWeek; i++) {
            VBox meetingPane = new VBox();
            meetingPane.setStyle("-fx-background-color: red");
            int start = 0;
            int end = 24;
            if (i == startDayOfWeek) {
                meetingPane.getChildren().addAll(description, time);
                start = timeToPos(startTime);
            } else if (i == endDayOfWeek) {
                end = timeToPos(endTime);
            }
            int duration = end - start;
            if (duration == 0) duration = 1;
            calendarGrid.add(meetingPane, i, start, 1, duration);
            meetingElements.add(meetingPane);
        }

    }

    private int timeToPos(LocalTime startTime) {
        return startTime.getHour();
    }

    /**
     * Gets date of start of week containing dateTime
     * @param dateTime
     * @return start of week containing dateTime
     */

    private LocalDate getStartWeekDate(LocalDateTime dateTime) {
        int weekday = LocalDateTime.now().getDayOfWeek().getValue();
        return LocalDateTime.now().minusDays(weekday - 1).toLocalDate();
    }

    /**
     * Gets date of start of week containing dateTime
     * @param dateTime
     * @return start of week containing dateTime
     */

    private LocalDate getEndWeekDate(LocalDateTime dateTime) {
        return getStartWeekDate(dateTime).plusDays(6);
    }

    /**
     * Retruns a true if meeting is in week
     * @param meeting
     * @return
     */

    private boolean isInWeek(Meeting meeting){
        LocalDate weekStart = getStartWeekDate(today).plusWeeks(diffWeeksFromToday);
        LocalDate weekEnd = getEndWeekDate(today).plusWeeks(diffWeeksFromToday);
        if (meeting.getTimeEnd().toLocalDate().isBefore(weekStart)) {
            return false;
        }
        if (meeting.getTimeStart().toLocalDate().isAfter(weekEnd)) {
            return false;
        }
        return true;
    }

}
