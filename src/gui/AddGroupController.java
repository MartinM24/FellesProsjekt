package gui;

import model.Group;
import model.LoginUser;
import calendarClient.CalendarClient;
import dbconnection.GroupDB;
import dbconnection.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddGroupController implements ControlledScreen, Initializable{

	private static final String GROUPNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
	
	//Button
	@FXML Button okButton;
	@FXML Button cancelButton;
	
	//CheckBox
	@FXML CheckBox hasParentCheckBox;
	
	//ComboBox
	@FXML ComboBox<String> chooseParentComboBox;
	@FXML ComboBox<String> addMemberComboBox;
	
	//ListView
	@FXML ListView<String> removeMemberListView;
	
	//TextField
	@FXML TextField nameTextField;
	
	@FXML
	public void initialize() {	
	}
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		String groupName = nameTextField.getText();
		// Check if groupName is a valid one 
		if(groupName.matches(GROUPNAME_REGEX)){
			//Check if groupName is registrated
			if (GroupDB.groupExist(groupName)) {
				//Fetch user with username form DB
				Group group = new Group(groupName);
				//Check if password is correct
				if (user.checkPassword(passwordField.getText())){
					//Correct password send to calendar screen
					CalendarClient.setCurrentUser(user);
					myController.setView(CalendarClient.CALENDAR_VIEW);
				} else {
					//Wrong password
					System.out.println("User exist, but worng password");
					wrongLoginFeedback();
				}
			} else {
				//Wrong groupName
				System.out.println("GroupName " + groupName + " does not exict");
				wrongLoginFeedback();
			}
		} else {
			//Wrong groupName format
			System.out.println("groupName " + groupName + " is in the worng format");
			wrongGroupNameFeedback();
		}
	}
}
