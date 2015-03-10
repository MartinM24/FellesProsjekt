package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import model.Group;
import model.LoginUser;
import model.User;
import dbconnection.DatabaseConnection;
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
import javafx.scene.input.MouseEvent;

public class AddGroupController implements ControlledScreen, Initializable{

	private static final String GROUPNAME_REGEX = "^[a-zA-Z0-9_-]{3,16}$";
	private List<String> memberList = new ArrayList<String>();
	private List<String> usernames = new ArrayList<String>();
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
	
	//Status- or feedback labels 
	@FXML Label groupNameStatus;
	
	@FXML
	public void initialize() {	
	}
	
	
	
	@FXML
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
	
	
	public void groupNameFocusChange(ObservableValue<String> o,  boolean oldValue, boolean newValue){
		if(oldValue){
			nameTextField.setText(nameTextField.getText().trim());
			String groupName = nameTextField.getText();
			if(groupName.matches(GROUPNAME_REGEX)){
				if(!GroupDB.groupExist(groupName)){
					groupNameStatus.setText("");
				}
				else{
					groupNameStatus.setText("Gruppenavn er tatt");
				}					
			}
			else{
				groupNameStatus.setText("Feil format");
			}
			
		}
	}
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		System.out.println("OK");
		String groupName = nameTextField.getText();
		// Check if groupName is a valid one 
		if(groupName.matches(GROUPNAME_REGEX)){
			//Check if groupName is registrated
			if (!GroupDB.groupExist(groupName)) {
				//@SuppressWarnings("unused")
				//Group group = new Group(groupName, getParent(), getMembers());
				GroupDB.addGroup(groupName);
				
			} else {
				//Wrong groupName
				System.out.println("GroupName " + groupName + " does not exist");
			}
		} else {
			//Wrong groupName format
			System.out.println("groupName " + groupName + " is in the worng format");
			//wrongGroupNameFeedback();
		}
	}

	private Group getParent(){
		return new Group(chooseParentComboBox.getValue());
	}
	
	private List<User> getMembers(){
		List<User> userList = new ArrayList<User>();
		for(String str : memberList){
			userList.add(UserDB.getUser(str));
		}
		return userList;
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
	
	private void addMember(String member){
		memberList.add(member);
		usernames.remove(member);
		removeMemberListView.setItems(FXCollections.observableList(memberList));
		addMemberComboBox.setItems(FXCollections.observableArrayList(usernames));
	}
	
	private void removeMember(String member){
		memberList.remove(member);
		usernames.add(member);
		removeMemberListView.setItems(FXCollections.observableList(memberList));
		addMemberComboBox.setItems(FXCollections.observableArrayList(usernames));
	}
	
	
	@FXML
	public void handleMouseClick(MouseEvent e){
		removeMember(removeMemberListView.getSelectionModel().getSelectedItem());
		removeMemberListView.getSelectionModel().clearSelection();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Remove when done
		DatabaseConnection.startCon();
		//
		
		memberList.clear();
		
		//textProperty().bind(
		  //      comboBox.getSelectionModel().selectedItemProperty());
		
		groupNameStatus.wrapTextProperty().set(true);
		chooseParentComboBox.setVisible(false);
		chooseParentComboBox.setEditable(true);
		removeMemberListView.setEditable(true);
		
		List<String> groupNames = GroupDB.getallGroups();
		
		//groupNames.add("TestGroup");
		chooseParentComboBox.setItems(FXCollections.observableArrayList(groupNames));
		
		
		addMemberComboBox.setEditable(true);
		
		List<LoginUser> users = UserDB.getAllUsers();
		for(User u: users){
			System.out.println(u.getUsername());
		}
		
		for (int i = 0 ; i < users.size(); i++){
			//if(users.get(i).getUsername() == CalendarClient.getCurrentUser().getUsername()){
			if(users.get(i).getUsername() == ""){
				//nothing
			}
			else{
				usernames.add(users.get(i).getUsername());
			}
		}
		
		//usernames = new ArrayList<String>();
		//usernames.add("Yay");
		//usernames.add("trav");
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
    				if(selected!=null){
    					addMember(selected);
    				}
    				
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



	@Override
	public void viewRefresh() {
		memberList.clear();
	}
}
