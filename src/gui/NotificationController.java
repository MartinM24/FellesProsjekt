package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import model.User;
import calendarClient.CalendarClient;
import dbconnection.MeetingDB;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class NotificationController implements ControlledScreen, Initializable{
	MainController myController;
	@FXML ListView<String> notificationListView;
	@FXML Button emptyButton;
	@FXML Button backButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	
	}
	@FXML
	public void emptyButtonAction(ActionEvent e){
		MeetingDB.resetChanged(CalendarClient.getCurrentUser());
		notificationListView.setItems(FXCollections.observableArrayList(MeetingDB.getAllChanges(CalendarClient.getCurrentUser())));
	}

	@FXML
	public void backButtonAction(ActionEvent e){
		myController.setView(CalendarClient.CALENDAR_VIEW);
	}
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
		
	}

	@Override
	public void viewRefresh() {
		notificationListView.setItems(FXCollections.observableArrayList(MeetingDB.getAllChanges(CalendarClient.getCurrentUser())));
	}

    @Override
    public void clearView() {

    }
}
