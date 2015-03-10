package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import model.Group;
import model.LoginUser;
import model.User;
import calendarClient.CalendarClient;
import dbconnection.GroupDB;
import dbconnection.UserDB;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddGroupController implements ControlledScreen, Initializable{

	private static final String GROUPNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
	MainController myController;
	
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
	
	
	public void hasParentChange(ActionEvent e){
		if(hasParentCheckBox.isSelected()){
			chooseParentComboBox.setVisible(true);
		}
		else{
			chooseParentComboBox.setValue("");
			chooseParentComboBox.setVisible(false);
		}
		System.out.println("CheckBox: "+hasParentCheckBox.isSelected());
	}
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		String groupName = nameTextField.getText();
		// Check if groupName is a valid one 
		if(groupName.matches(GROUPNAME_REGEX)){
			//Check if groupName is registrated
			if (GroupDB.groupExist(groupName)) {
				@SuppressWarnings("unused")
				Group group = new Group(groupName, getParent(), getMembers());
				
				//Check if password is correct
			} else {
				//Wrong groupName
				System.out.println("GroupName " + groupName + " does not exist");
				//wrongLoginFeedback();
			}
		} else {
			//Wrong groupName format
			System.out.println("groupName " + groupName + " is in the worng format");
			//wrongGroupNameFeedback();
		}
	}

	private Group getParent(){
		return null;
	}
	
	private List<User> getMembers(){
		return null;
	}
	
	private <T> void filterItems(String filter, ComboBox<T> comboBox,List<T> items) {
		List<T> filteredItems = new ArrayList<>();
		for (T item : items) {
			if (item.toString().toLowerCase().startsWith(filter.toLowerCase())) {
				filteredItems.add(item);
			}
		}
		comboBox.setItems(FXCollections.observableArrayList(filteredItems));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chooseParentComboBox.setVisible(false);
		chooseParentComboBox.setEditable(true);
		
		//HashMap<String, List<String>> groups = GroupDB.getAllGroups();
		List<String> groupNames = new ArrayList<String>();
		//groupNames.addAll(groups.keySet());
		
		groupNames.add("TestGroup");
		chooseParentComboBox.setItems(FXCollections.observableArrayList(groupNames));
		
		
		addMemberComboBox.setEditable(true);
		/*
		List<LoginUser> users = UserDB.getAllUsers();
		List<String> usernames = new ArrayList<String>();
		for (int i = 0 ; i < users.size(); i++){
			//if(users.get(i).getUsername() == CalendarClient.getCurrentUser().getUsername()){
			if(users.get(i).getUsername() == ""){
				//nothing
			}
			else{
				usernames.add(users.get(i).getUsername());
			}
		}
		*/
		List<String> usernames = new ArrayList<String>();
		usernames.add("Yay");
		usernames.add("trav");
		addMemberComboBox.setItems(FXCollections.observableArrayList(usernames));
		
		chooseParentComboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            final TextField editor = chooseParentComboBox.getEditor();
            final String selected = chooseParentComboBox.getSelectionModel().getSelectedItem();
            if (selected == null || !selected.equals(editor.getText())) {
              filterItems(newValue, chooseParentComboBox, groupNames);
              chooseParentComboBox.show();
            }
          }
        });
		
    chooseParentComboBox.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // Reset so all options are available:
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            String selected = chooseParentComboBox.getSelectionModel().getSelectedItem();
            if (chooseParentComboBox.getItems().size() < groupNames.size()) {
              chooseParentComboBox.setItems(FXCollections.observableArrayList(groupNames));
              String newSelected = chooseParentComboBox.getSelectionModel()
                  .getSelectedItem();
              if (newSelected == null || !newSelected.equals(selected)) {
                chooseParentComboBox.getSelectionModel().select(selected);
              }
            }
          }
        });
      }
    });

    addMemberComboBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          final TextField editor = addMemberComboBox.getEditor();
          final String selected = addMemberComboBox.getSelectionModel().getSelectedItem();
          if (selected == null || !selected.equals(editor.getText())) {
            filterItems(newValue, addMemberComboBox, usernames);
            addMemberComboBox.show();
          }
        }
      });
    
    addMemberComboBox.setOnAction(new EventHandler<ActionEvent>() {
    	@Override
    	public void handle(ActionEvent event) {
    		// Reset so all options are available:
    		Platform.runLater(new Runnable() {
    			@Override
    			public void run() {
    				String selected = addMemberComboBox.getSelectionModel().getSelectedItem();
    				if (addMemberComboBox.getItems().size() < usernames.size()) {
    					addMemberComboBox.setItems(FXCollections.observableArrayList(usernames));
    					String newSelected = chooseParentComboBox.getSelectionModel()
    							.getSelectedItem();
    					if (newSelected == null || !newSelected.equals(selected)) {
    						addMemberComboBox.getSelectionModel().select(selected);
    					}
    				}
    			}
    		});
    	}
    });
	}
	
	

	@Override
	public void setScreenParent(MainController screenPage) {
		this.myController = screenPage;
	}
}
