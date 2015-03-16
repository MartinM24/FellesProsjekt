package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;

import com.sun.corba.se.spi.ior.MakeImmutable;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import dbconnection.MeetingDB;
import dbconnection.RoomDB;
import model.Invitation;
import model.InvitationVeiw;
import model.Room;
import model.RoomVeiw;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvitationMeetingController implements ControlledScreen, Initializable {
	MainController myController;
	
	@FXML Button done;
	@FXML Button cancel;
	@FXML Button agree;
	
	@FXML TableView<InvitationVeiw> table;
	@FXML TableColumn<InvitationVeiw , String> idColumn;
	@FXML TableColumn<InvitationVeiw , String> sendColumn;
	@FXML TableColumn<InvitationVeiw , String> descColumn;
	private InvitationVeiw inv;
	
	private ObservableList<InvitationVeiw> data = FXCollections.observableArrayList();


	public void doneButtonClick(ActionEvent e){
		myController.setView(CalendarClient.CALENDAR_SCREEN);
	}
	
	public void agreeButtonClick(ActionEvent e){
		this.inv = (InvitationVeiw)table.getSelectionModel().getSelectedItem();
		MeetingDB.updateInvitation(inv.getMeetingID(), 1);
		tableSetup();
	}
	
	public void cancelButtonClick(ActionEvent e){
		this.inv = (InvitationVeiw)table.getSelectionModel().getSelectedItem();
		MeetingDB.updateInvitation(inv.getMeetingID(), -1);
		tableSetup();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		table.setEditable(true);
		idColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("meetingID"));   		
		sendColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("sender"));   		
		descColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("invitationName"));   
	}
	
	
	private void tableSetup(){
		data = FXCollections.observableArrayList();
		table.getSelectionModel().clearSelection();
		ArrayList<InvitationVeiw> inviteDB = (ArrayList<InvitationVeiw>) MeetingDB.getAllInvitationViews(calendarClient.CalendarClient.getCurrentUser());
		data.addAll(inviteDB);
		table.setItems(data);
	}
	
	
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}


	@Override
	public void viewRefresh() {
		data = FXCollections.observableArrayList();
		tableSetup();
	}

    @Override
    public void clearView() {
    	table.setItems(null);
    }
}
