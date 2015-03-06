package calendarClient;

import model.LoginUser;
import gui.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CalendarClient extends Application{
	// References to all screens
	public static final String LOG_IN_SCREEN = "LogIN";
	public static final String LOG_IN_SCREEN_FXML = "/gui/LogInGUI2.fxml";
	public static final String NEW_USER_SCREEN = "NewUser";
	public static final String NEW_USER_SCREEN_FXML = "/gui/NewUserGUI2.fxml";
	public static final String CALENDAR_SCREEN = "Calendar";
	public static final String CALENDAR_SCREEN_FXML = "/gui/CalendarGUI2.fxml";
	public static final String ADD_MEETING_SCREEN = "AddMeeting";
	public static final String ADD_MEETING_SCREEN_FXML = "/gui/AddMeetingGUI2.fxml";
	private static LoginUser currentUser;
	
	ScreensController mainController; 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = new AnchorPane();
		
		mainController = new ScreensController();
		// Load all Screens 
		mainController.loadScreen(LOG_IN_SCREEN, LOG_IN_SCREEN_FXML);
		mainController.loadScreen(NEW_USER_SCREEN, NEW_USER_SCREEN_FXML);
		mainController.loadScreen(CALENDAR_SCREEN, CALENDAR_SCREEN_FXML);
		mainController.loadScreen(ADD_MEETING_SCREEN, ADD_MEETING_SCREEN_FXML);
		
		// Set first screen
		mainController.setScreen(LOG_IN_SCREEN);
		
		// Show first screen
		root.getChildren().addAll(mainController);		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
//		primaryStage.setFullScreen(true);
		primaryStage.show();
		
		//Set up DB conection;
		dbconnection.DatabaseConnection.startCon();
		
	}
	
	
	
	public static LoginUser getCurrentUser() {
		return currentUser;
	}



	public static void setCurrentUser(LoginUser currentUser) {
		CalendarClient.currentUser = currentUser;
	}



	public static void main(String[] args) {
		launch(args);
	}

}
