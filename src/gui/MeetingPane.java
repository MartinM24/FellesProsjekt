package gui;

import calendarClient.CalendarClient;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Meeting;

import java.time.LocalDateTime;

/**
 * Created by Martin on 11.03.15.
 */
public class MeetingPane extends VBox {
    LocalDateTime startTime;
    LocalDateTime endTime;


    public MeetingPane(Meeting m) {
        ((GridPane)this.getParent()).setMargin(this, new Insets(0.5, 2, 0, 2));
        this.setStyle(m.getFXStyle());
        Label time = new Label(m.getStartString() + " - " + m.getEndString());
        Label desc = new Label(m.getDescription());
        time.setFont(new Font(10));
        desc.setFont(new Font(10));
        time.setWrapText(true);
        desc.setWrapText(true);
        this.getChildren().addAll(time, desc);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                CalendarClient.mainController.setView(CalendarClient.ADD_MEETING_VIEW);
                AddMeetingController mCtrl = (AddMeetingController) CalendarClient.mainController.getControllerForScreen(CalendarClient.ADD_MEETING_SCREEN);
                mCtrl.subjectField.setText(m.getDescription());
                event.consume();
            }
        });

    }




}
