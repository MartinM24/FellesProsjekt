package gui;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.corba.se.spi.ior.MakeImmutable;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

import dbconnection.MeetingDB;
import dbconnection.RoomDB;
import model.Invitation;
import model.InvitationVeiw;
import model.Room;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InvitationMeetingController implements ControlledScreen, Initializable {
	MainController myController;
	
	@FXML TableView<InvitationVeiw> table;
	@FXML TableColumn<InvitationVeiw , String> sendColumn;
	@FXML TableColumn<InvitationVeiw , String> descColumn;


	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sendColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("Sender"));   		
		descColumn.setCellValueFactory(new PropertyValueFactory<InvitationVeiw, String>("Beskrivelse"));   
	}
	
	
	@SuppressWarnings("unused")
	private void tableSetup(){
		//TODO aktive brukeren er objektet user
		ArrayList<InvitationVeiw> inviteDB = (ArrayList<InvitationVeiw>) MeetingDB.getAllInvitations(calendarClient.CalendarClient.getCurrentUser());
		table.setItems(FXCollections.observableArrayList(inviteDB));
	}
	
	
	
	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}


	@Override
	public void viewRefresh() {
		// TODO Auto-generated method stub
		
	}


}
