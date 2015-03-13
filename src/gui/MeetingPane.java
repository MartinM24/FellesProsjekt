package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
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
        ((GridPane)this.getParent()).setMargin(this, new Insets(0, 2, 0, 2));
        this.setStyle(m.getFXStyle());
        Label time = new Label(m.getStartString() + " - " + m.getEndString());
        Label desc = new Label(m.getDescription());
        time.setFont(new Font(10));
        desc.setFont(new Font(10));
        time.setWrapText(true);
        desc.setWrapText(true);
        this.getChildren().addAll(time, desc);
    }
}
