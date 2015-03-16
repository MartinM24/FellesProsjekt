package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SetAlarmController implements ControlledScreen, Initializable {
	
	@FXML Button saveButton;
	@FXML Button cancelButton;
	@FXML DatePicker fromDatePicker;
	@FXML TextField fromtimeField;
	
	
	public void saveButtonClick(ActionEvent e){
		//TODO stuff
	}
	
	public void cancelButtonClick(ActionEvent e){
		//TODO stuff
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenParent(MainController screenPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearView() {
		// TODO Auto-generated method stub
		
	}

}
