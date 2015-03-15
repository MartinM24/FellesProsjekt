package gui;

import calendarClient.CalendarClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
	@FXML
    CheckBox myCalendarCheckBox;
	//ComboBox
	@FXML ChoiceBox<String> groupChoiceBox;
	@FXML ChoiceBox<String> employeeChoiceBox;
	
	//ListViews
	@FXML ListView<String> employeeList;
	@FXML ListView<String> groupList;
	
	private List<String> usernames = new ArrayList<>();
	private List<String> selectedGroupnames = new ArrayList<>();
	private List<String> allGroupnames = new ArrayList<>();

    @Override
	public void viewRefresh() {
        initAll();
    }
	
	private void initAll(){
        usernames.clear();
        selectedGroupnames.clear();

        //Fill usernameComboBox
        List<LoginUser> users = UserDB.getAllUsers();

        for (int i = 0 ; i < users.size(); i++){
            System.out.println(users.get(i).getUsername());
            usernames.add(users.get(i).getUsername());
        }

        employeeChoiceBox.getItems().addAll(usernames);

        //Fill groupnameComboBox

        allGroupnames = GroupDB.getallGroups();
        groupChoiceBox.getItems().addAll(allGroupnames);
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

    @Override
    public void clearView() {

    }
}
