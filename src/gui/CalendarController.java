package gui;

import calendarClient.CalendarClient;
import dbconnection.MeetingDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Meeting;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalendarController implements ControlledScreen, Initializable {
    MainController myController;
    @FXML
    private CalendarPane calendarGrid;
    @FXML
    private Label rageLabel;
    private int diffWeeksFromToday = 0;
    private LocalDateTime today;
    private HashMap<Meeting, Integer> meetingOverlapMap = new HashMap<>();

    /**
     * Gets date of start of week containing dateTime
     *
     * @param dateTime
     * @return start of week containing dateTime
     */
    private static LocalDate getStartWeekDate(LocalDateTime dateTime) {
        int weekday = dateTime.getDayOfWeek().getValue();
        return dateTime.minusDays(weekday - 1).toLocalDate();
    }

    /**
     * Gets date of start of week containing dateTime
     *
     * @param dateTime
     * @return start of week containing dateTime
     */

    public static LocalDate getEndWeekDate(LocalDateTime dateTime) {
        return getStartWeekDate(dateTime).plusDays(6);
    }

    @Override
    public void viewRefresh() {
        this.today = LocalDateTime.now();
        showMeetings(MeetingDB.getAllMeetings(CalendarClient.getCurrentUser()));
        String start = getStartWeekDate(today).plusWeeks(diffWeeksFromToday).format(DateTimeFormatter.ofPattern("dd.MM"));
        String end = getEndWeekDate(today).plusWeeks(diffWeeksFromToday).format(DateTimeFormatter.ofPattern("dd.MM"));
        rageLabel.setText(start + " - " + end);
        //List<Meeting> meetings = MeetingDB.getAllMeetings(CalendarClient.getCurrentUser());
        //showMeetings(meetings);
    }

    @Override
    public void setScreenParent(MainController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    @FXML
    private void thisWeekClick(ActionEvent e) {
        diffWeeksFromToday = 0;
        viewRefresh();
    }

    public void showMeetings(List<Meeting> meetings) {
        calendarGrid.clear();
        calendarGrid.drawCalendarGrid(getMaxOverlapPerDay(meetingWeekFilter(meetings)));
        List<Meeting> thisWeek = meetingWeekFilter(meetings);
        for (Meeting m: thisWeek) {
            calendarGrid.addMeeting(m, meetingOverlapMap.get(m));
        }
    }

    private int[] getMaxOverlapPerDay(List<Meeting> meetings) {
        int[] overlap = new int[7];
        for (int i = 0; i < 7; i++) {
            overlap[i] = getMaxOverlap(meetingsInDayFilter(meetings, i + 1));
        }
        return overlap;
    }

    private int getMaxOverlap(List<Meeting> meetings) {
        int maxOverlap = 0;
        for (Meeting m1 : meetings) {
            int overlap = 0;
            for (Meeting m : meetings) {
                if (m1 != m) {
                    if (m1.doesOverlap(m)) {
                        overlap++;
                        System.out.println("Overlapper " + overlap + " : " + m.toString() + " - " + m1.toString());
                    }
                }
            }
            meetingOverlapMap.put(m1, overlap);
            if (overlap > maxOverlap)
                maxOverlap = overlap;
        }
        return maxOverlap;
    }



    private List<Meeting> meetingsInDayFilter(List<Meeting> meetings, int dayOfWeek) {
        List<Meeting> res = new ArrayList<>();
        LocalDateTime startOfDay = getStartWeekDate(today).plusWeeks(diffWeeksFromToday).plusDays(dayOfWeek - 1).atTime(0, 0);
        LocalDateTime endOfDay = getStartWeekDate(today).plusWeeks(diffWeeksFromToday).plusDays(dayOfWeek - 1).atTime(23, 59, 59, 9999);
        for (Meeting m : meetings) {
            if (m.getTimeEnd().isBefore(startOfDay)) {
                continue;
            }
            if (m.getTimeStart().isAfter(endOfDay)) {
                continue;
            }
            res.add(m);
        }
        return res;
    }

    private List<Meeting> meetingWeekFilter(List<Meeting> meetings) {
        List<Meeting> res = new ArrayList<>();
        for (Meeting meeting : meetings) {
            if (isInWeek(meeting)) {
                res.add(meeting);
            }
        }
        return res;
    }

    /**
     * Retruns a true if meeting is in week
     *
     * @param meeting
     * @return
     */

    private boolean isInWeek(Meeting meeting) {
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
