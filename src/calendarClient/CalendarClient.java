package calendarClient;

import model.LoginUser;
import gui.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CalendarClient extends Application{
	// References to all screens
	private static final String LOG_IN_SCREEN = "LogIN";
	private static final String LOG_IN_SCREEN_FXML = "/gui/LogInGUI2.fxml";
	private static final String NEW_USER_SCREEN = "NewUser";
	private static final String NEW_USER_SCREEN_FXML = "/gui/NewUserGUI2.fxml";
	private static final String CALENDAR_SCREEN = "Calendar";
	private static final String CALENDAR_SCREEN_FXML = "/gui/CalendarGUI2.fxml";
	private static final String ADD_MEETING_SCREEN = "AddMeeting";
	private static final String ADD_MEETING_SCREEN_FXML = "/gui/AddMeetingGUI2.fxml";
	private static final String GLOBAL_MENU_SCREEN = "GlobalMenu";
	private static final String GLOBAL_MENU_SCREEN_FXML = "/gui/GlobalMenuGUI2.fxml";
	private static final String CALENDAR_LEFT_MENU_SCREEN = "CalendarLeftMenu";
	private static final String CALENDAR_LEFT_MENU_SCREEN_FXML = "/gui/CalendarLeftMenuGUI2.fxml";
	private static final String CHOOSE_CALENDARS_SCREEN = "ChooseCalendars";
	private static final String CHOOSE_CALENDARS_SCREEN_FXML = "/gui/ChooseCalendarsGUI2.fxml";
	private static final String MEETING_OVERVIEW_SCREEN = "MeetingOverview";
	private static final String MEETING_OVERVIEW_SCREEN_FXML = "/gui/MeetingOverviewGUI2.fxml";
	private static final String MY_GROUPS_SCREEN = "MyGroups";
	private static final String MY_GROUPS_SCREEN_FXML = "/gui/MyGroupsGUI2.fxml";

    public static final String LOG_IN_VIEW = "LogIN";
    public static final String NEW_USER_VIEW = "NewUser";
    public static final String CALENDAR_VIEW = "Calendar";
    public static final String ADD_MEETING_VIEW = "AddMeeting";
    public static final String CHOOSE_CALENDARS_VIEW = "ChooseCalendars";
    public static final String MEETING_OVERVIEW_VIEW = "MeetingOverview";
    public static final String MY_GROUPS_VIEW = "MyGroups";
	private static LoginUser currentUser;

	private MainController mainController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = new AnchorPane();
		
		mainController = new MainController();
		// Load all Screens 
		mainController.loadScreen(LOG_IN_SCREEN, LOG_IN_SCREEN_FXML);
		mainController.loadScreen(NEW_USER_SCREEN, NEW_USER_SCREEN_FXML);
		mainController.loadScreen(CALENDAR_SCREEN, CALENDAR_SCREEN_FXML);
        mainController.loadScreen(ADD_MEETING_SCREEN, ADD_MEETING_SCREEN_FXML);
        mainController.loadScreen(GLOBAL_MENU_SCREEN, GLOBAL_MENU_SCREEN_FXML);
        mainController.loadScreen(CALENDAR_LEFT_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN_FXML);
        mainController.loadScreen(CHOOSE_CALENDARS_SCREEN, CHOOSE_CALENDARS_SCREEN_FXML);
        mainController.loadScreen(MEETING_OVERVIEW_SCREEN, MEETING_OVERVIEW_SCREEN_FXML);
        mainController.loadScreen(MY_GROUPS_SCREEN, MY_GROUPS_SCREEN_FXML);

        // Make view form loaded screens
		System.out.println("Load screens");
		mainController.makeView(ADD_MEETING_VIEW, ADD_MEETING_SCREEN, GLOBAL_MENU_SCREEN);
        mainController.makeView(LOG_IN_VIEW, LOG_IN_SCREEN);
        mainController.makeView(NEW_USER_VIEW, NEW_USER_SCREEN);
        mainController.makeView(CALENDAR_VIEW, CALENDAR_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(CHOOSE_CALENDARS_VIEW, CHOOSE_CALENDARS_SCREEN, GLOBAL_MENU_SCREEN);
        mainController.makeView(MEETING_OVERVIEW_VIEW, MEETING_OVERVIEW_SCREEN, GLOBAL_MENU_SCREEN);
        mainController.makeView(MY_GROUPS_VIEW, MY_GROUPS_SCREEN, GLOBAL_MENU_SCREEN);
		// Set first screen
		mainController.setView(LOG_IN_VIEW);

		// Show first screen
		root.getChildren().addAll(mainController);

        AnchorPane.setTopAnchor(mainController, 0.0);
        AnchorPane.setBottomAnchor(mainController, 0.0);
        AnchorPane.setRightAnchor(mainController, 2.0);
        AnchorPane.setLeftAnchor(mainController, 10.0);
        root.setPrefSize(900,600);
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
