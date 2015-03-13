package gui;

import calendarClient.CalendarClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.Checkbox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.border.EmptyBorder;

import model.LoginUser;
import model.User;
import dbconnection.GroupDB;
import dbconnection.UserDB;


/**
 * Created by Anna on 09.03.15.
 */

public class ChooseCalendarsController implements ControlledScreen, Initializable {
	MainController myController;

	@FXML Button okButton;
	@FXML Button cancelButton;
	//	@FXML Button saveButton;	
	@FXML Checkbox myCalendarCheckBox;
	//ComboBox
	@FXML ComboBox<String> groupComboBox;
	@FXML ComboBox<String> employeeComboBox;
	
	//ListViews
	@FXML ListView<String> employeeList;
	@FXML ListView<String> groupList;
	
	private List<String> usernames = new ArrayList<String>();
	private List<String> selectedGroupnames = new ArrayList<String>();
	private List<String> allGroupnames = new ArrayList<String>();
	@Override
	public void viewRefresh() {
	}

	private void addGroupToList(String group){
		selectedGroupnames.add(group);	
		groupComboBox.setItems(FXCollections.observableArrayList(selectedGroupnames));
	}
	private void addUsernameToList(String username){
		usernames.add(username);
		
		employeeList.setItems(FXCollections.observableArrayList(usernames));
	}
	
	
	private void initAll(){
	usernames.clear();
	selectedGroupnames.clear();
	
	//Fill usernameComboBox
	List<LoginUser> users = UserDB.getAllUsers();
	
	for (int i = 0 ; i < users.size(); i++){
		
			usernames.add(users.get(i).getUsername());
		}
	
	
	employeeComboBox.setItems(FXCollections.observableArrayList(usernames));
	
	//Fill groupnameComboBox
	
	allGroupnames = GroupDB.getallGroups();
	groupComboBox.setItems(FXCollections.observableArrayList(allGroupnames));
	}
	
		

		@FXML
		public void okButtonClick(ActionEvent e){
			//Move user back to calendar. 
			myController.setView(CalendarClient.CALENDAR_VIEW);
		}

		@FXML
		public void cancelButtonClick(ActionEvent e){
			//Move user back to calendar. 
			myController.setView(CalendarClient.CALENDAR_VIEW);
		}


		@Override
		public void setScreenParent(MainController screenPage) {
			this.myController = screenPage;

		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
			
			
		}

	}
