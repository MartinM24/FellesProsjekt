package calendarClient;

import gui.ScreensController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarClient extends Application{
	private static final String LOG_IN_SCREEN_FXML = "/gui/LogInGUI.fxml";
	private static final String LOG_IN_SCREEN = "LogIN";
	ScreensController mainController; 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		mainController = new ScreensController();
		// Load all Screens 
		mainController.loadScreen(LOG_IN_SCREEN, LOG_IN_SCREEN_FXML);
		
		// Set first screen
		mainController.setScreen(LOG_IN_SCREEN);
		
		// Show first screen
	    
    
		Group root = new Group();
		root.getChildren().addAll(mainController);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
