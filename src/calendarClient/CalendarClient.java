package calendarClient;

import gui.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarClient extends Application{
	// References to all screens
	public static final String LOG_IN_SCREEN = "LogIN";
	public static final String LOG_IN_SCREEN_FXML = "/gui/LogInGUI.fxml";
	public static final String NEW_USER_SCREEN = "NewUser";
	public static final String NEW_USER_SCREEN_FXML = "/gui/NewUserGUI.fxml";
	public static final String CALENDAR_SCREEN = "Calendar";
	public static final String CALENDAR_SCREEN_FXML = "/gui/CalendarGUI.fxml";
	
	ScreensController mainController; 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainController = new ScreensController();
		// Load all Screens 
		mainController.loadScreen(LOG_IN_SCREEN, LOG_IN_SCREEN_FXML);
		mainController.loadScreen(NEW_USER_SCREEN, NEW_USER_SCREEN_FXML);
		mainController.loadScreen(CALENDAR_SCREEN, CALENDAR_SCREEN_FXML);
		
		// Set first screen
		mainController.setScreen(LOG_IN_SCREEN);
		
		// Show first screen		
		Group root = new Group();
		root.getChildren().addAll(mainController);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//Set up DB conection;
		dbconnection.DatabaseConnection.startCon();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
