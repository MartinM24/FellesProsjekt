package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import calendarClient.CalendarClient;
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
	private List<String> memberList = new ArrayList<>();
	private List<String> usernames = new ArrayList<>();
	private List<String> groupNames = new ArrayList<>();
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
			chooseParentComboBox.getSelectionModel().clearSelection();
			chooseParentComboBox.setVisible(false);
		}
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
	private void cancelButtonClick(ActionEvent e){
		clearView();
		myController.setView(CalendarClient.MY_GROUPS_VIEW);
	}
	
	@FXML
	private void okButtonClick(ActionEvent event) {
		String groupName = nameTextField.getText();
		// Check if groupName is a valid one 
		if(groupName.matches(GROUPNAME_REGEX)){
			//Check if groupName is registrated
			if (!GroupDB.groupExist(groupName)) {
				@SuppressWarnings("unused")
				Group group = new Group(groupName, getParent(), getMembers());
				clearView();
				myController.setView(CalendarClient.MY_GROUPS_VIEW);
				//GroupDB.addGroup(groupName);
				
			} else {
				//Wrong groupName
			}
		} else {
			//Wrong groupName format
			//wrongGroupNameFeedback();
		}
	}

	private Group getParent(){
        if(chooseParentComboBox.getValue() == null)
            return null;
        if (chooseParentComboBox.getValue().trim().length() > 0)
		    return new Group(chooseParentComboBox.getValue());
        return null;
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
	
	
	private void initAll(){
		memberList.clear();
		
		
		groupNameStatus.wrapTextProperty().set(true);
		chooseParentComboBox.setVisible(false);
		chooseParentComboBox.setEditable(true);
		removeMemberListView.setEditable(true);
		groupNames = GroupDB.getallGroups();
		chooseParentComboBox.setItems(FXCollections.observableArrayList(groupNames));
		hasParentCheckBox.setSelected(false);
		nameTextField.setText("");
		addMemberComboBox.setEditable(true);
		chooseParentComboBox.getSelectionModel().clearSelection();
		List<LoginUser> users = UserDB.getAllUsers();
		
		for (int i = 0 ; i < users.size(); i++){
			//if(users.get(i).getUsername() == CalendarClient.getCurrentUser().getUsername()){
			if(users.get(i).getUsername() == ""){
				//nothing
			}
			else{
				usernames.add(users.get(i).getUsername());
			}
		}
		
		addMemberComboBox.setItems(FXCollections.observableArrayList(usernames));
		removeMemberListView.setItems(null);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
//		chooseParentComboBox.getSelectionModel().clearSelection();
//		memberList.clear();
		initAll();
	}

    @Override
    public void clearView() {
    	memberList.clear();
    	usernames.clear();
    	groupNames.clear();
    	hasParentCheckBox.setSelected(false);
    	chooseParentComboBox.setItems(null);
    	addMemberComboBox.setItems(null);
    	removeMemberListView.setItems(null);
    	nameTextField.setText("");
    	groupNameStatus.setText("");
    }
}
