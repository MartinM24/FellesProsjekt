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

public class ChangeAttendenceController implements ControlledScreen, Initializable {
	MainController myController;
	
	@FXML Button done;
	@FXML Button cancel;
	@FXML Button agree;
	
	@FXML TableView<InvitationVeiw> table;
	@FXML TableColumn<InvitationVeiw , String> sendColumn;
	@FXML TableColumn<InvitationVeiw , String> dateColumn;
	@FXML TableColumn<InvitationVeiw , String> timeFromColumn;
	@FXML TableColumn<InvitationVeiw , String> timeToColumn;
	@FXML TableColumn<InvitationVeiw , String> descColumn;
	@FXML TableColumn<InvitationVeiw , String> yourColumn;
	private InvitationVeiw inv;
	
	private ObservableList<InvitationVeiw> data = FXCollections.observableArrayList();


	public void doneButtonClick(ActionEvent e){
		myController.setView(CalendarClient.CALENDAR_SCREEN);
	}
	
	public void agreeButtonClick(ActionEvent e){
		if (!(table.getSelectionModel().isEmpty())){
    		
		this.inv = (InvitationVeiw)table.getSelectionModel().getSelectedItem();
		MeetingDB.updateInvitation(inv.getMeetingID(), 1);
		tableSetup();
	}}
	
	public void cancelButtonClick(ActionEvent e){
		if (!(table.getSelectionModel().isEmpty())){
		this.inv = (InvitationVeiw)table.getSelectionModel().getSelectedItem();
		MeetingDB.updateInvitation(inv.getMeetingID(), -1);
		tableSetup();
	}}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		table.setEditable(true);
		sendColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("sender"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("date"));   		
		timeFromColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("timeFrom"));   		
		timeToColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("timeTo"));   		
		descColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("invitationName"));
		yourColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("yourStatus"));
	}
	
	
	private void tableSetup(){
		data = FXCollections.observableArrayList();
		table.getSelectionModel().clearSelection();
		ArrayList<InvitationVeiw> inviteDB = (ArrayList<InvitationVeiw>) MeetingDB.getAllInvitationViewsWithAttendence(calendarClient.CalendarClient.getCurrentUser());
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

    }
}