package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class CalendarController implements ControlledScreen, Initializable {
	ScreensController myController; 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScreenParent(ScreensController screenPage) {
		this.myController = screenPage; 
	}

}
