package gui;

import calendarClient.CalendarClient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import model.Group;
import model.User;
import dbconnection.GroupDB;


/**
 * Created by Anna on 09.03.15.
 */

public class MyGroupsController implements ControlledScreen, Initializable {
	List<String> groups = new ArrayList<String>();
	MainController myController;
	@FXML Button okButton;
	@FXML Button newGroupButton;
	
	@FXML ListView<String> groupsListView;
	@FXML ListView<String> membersListView;

	@FXML CheckBox showAllGroupsCheckBox;
	
	
    @Override
    public void viewRefresh() {
    	//groups = GroupDB.getallGroups();
    	update();
    }

	private void update() {
		groups = GroupDB.getAllGroups(CalendarClient.getCurrentUser().getUsername());
    	groupsListView.setItems(FXCollections.observableArrayList(groups));
	}
	
	@FXML
	public void showAllGroupsCheck(ActionEvent e){
		membersListView.setItems(null);
		if(showAllGroupsCheckBox.isSelected()){
			groups = GroupDB.getallGroups();
		}
		else{
			groups = GroupDB.getAllGroups(CalendarClient.getCurrentUser().getUsername());
		}
		groupsListView.setItems(FXCollections.observableArrayList(groups));
	}

    @FXML
    public void okButtonClick(ActionEvent e){
		//Move user back to calendars.
		myController.setView(CalendarClient.CALENDAR_VIEW);
	}

	@FXML
    public void newGroupButtonClick(ActionEvent e){
		//Move user to new group. 
		myController.setView(CalendarClient.ADD_GROUP_VIEW);
	}
	
	@FXML
	public void deleteGroupAction(ActionEvent e){
		String groupName = groupsListView.getSelectionModel().getSelectedItem();
		GroupDB.deleteGroup(new Group(groupName));
		update();
	}
	
	@FXML
	public void handleMouseClick(MouseEvent e){
		String groupName = groupsListView.getSelectionModel().getSelectedItem();
		List<User> members = new ArrayList<User>();
		Group group = new Group(groupName);
		members = group.getAllMembers();
		List<String> memberNames = new ArrayList<String>();
		for(int i = 0; i < members.size(); i++){
			if(!memberNames.contains(members.get(i).getUsername())){
				memberNames.add(members.get(i).getUsername());
			}
		}
		
		membersListView.setItems(FXCollections.observableArrayList(memberNames));
	}


	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;

	}

	@Override
    public void initialize(URL location, ResourceBundle resources) {
		groups = GroupDB.getallGroups();
		System.out.println("Initialize MyGroups");
    }

    @Override
    public void clearView() {

    }

    // onAction="#newGroupButtonClick"

}
