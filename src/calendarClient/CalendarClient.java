package calendarClient;

import gui.ControlledScreen;
import model.LoginUser;
import gui.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.util.List;

public class CalendarClient extends Application{

    // Important note: View consists of multiple screens.
    // It is the screens that has controllers
    // It is the views that is used to change the whole application view

	// Name and ResourcePath to all screens
	public static final String LOG_IN_SCREEN = "LogIN";
	private static final String LOG_IN_SCREEN_FXML = "/gui/LogInGUI2.fxml";
	public static final String NEW_USER_SCREEN = "NewUser";
	private static final String NEW_USER_SCREEN_FXML = "/gui/NewUserGUI2.fxml";
	public static final String CALENDAR_SCREEN = "Calendar";
	private static final String CALENDAR_SCREEN_FXML = "/gui/CalendarGUI2.fxml";
	public static final String ADD_MEETING_SCREEN = "AddMeeting";
	private static final String ADD_MEETING_SCREEN_FXML = "/gui/AddMeetingGUI2.fxml";
	public static final String GLOBAL_MENU_SCREEN = "GlobalMenu";
	private static final String GLOBAL_MENU_SCREEN_FXML = "/gui/GlobalMenuGUI2.fxml";
	public static final String CALENDAR_LEFT_MENU_SCREEN = "CalendarLeftMenu";
	private static final String CALENDAR_LEFT_MENU_SCREEN_FXML = "/gui/CalendarLeftMenuGUI2.fxml";
	public static final String CHOOSE_CALENDARS_SCREEN = "ChooseCalendars";
	private static final String CHOOSE_CALENDARS_SCREEN_FXML = "/gui/ChooseCalendarsGUI2.fxml";
	public static final String MEETING_OVERVIEW_SCREEN = "MeetingOverview";
	private static final String MEETING_OVERVIEW_SCREEN_FXML = "/gui/MeetingOverviewGUI2.fxml";
	public static final String MY_GROUPS_SCREEN = "MyGroups";
	private static final String MY_GROUPS_SCREEN_FXML = "/gui/MyGroupsGUI2.fxml";
	private static final String ADD_GROUP_SCREEN = "AddGroup";
	private static final String ADD_GROUP_SCREEN_FXML = "/gui/AddGroupGUI2.fxml";
	public static final String MEETING_ROOM_OVERVIEW_SCREEN ="MeetingRoomOverview";
	private static final String MEETING_ROOM_OVERVIEW_SCREEN_FXML ="/gui/MeetingRoomOverviewGUI2.fxml";
	public static final String NOTIFICATION_SCREEN = "Notification";
	private static final String NOTIFICATION_SCREEN_FXML = "/gui/Notification.fxml";
	public static final String INVITATION_MEETING_SCREEN = "InvitationMeeting";
	private static final String INVITATION_MEETING_SCREEN_FXML = "/gui/InvitationMeetingGUI2.fxml";
	public static final String CHANGE_ATTENDENCE_SCREEN = "ChangeAttendence";
	private static final String CHANGE_ATTENDENCE_SCREEN_FXML = "/gui/ChangeAttendenceGUI2.fxml";
    public static final String EDIT_MEETING_SCREEN = "EditMeeting";
    private static final String EDIT_MEETING_SCREEN_FXML = "/gui/EditMeetingGUI2.fxml";
    public static final String MEETING_ATTENDENCE_SCREEN = "MeetingAttendence";
    private static final String MEETING_ATTENDENCE_FXML = "/gui/MeetingAttendenceGUI2.fxml";




    public static final String LOG_IN_VIEW = "LogIN";
    public static final String NEW_USER_VIEW = "NewUser";
    public static final String CALENDAR_VIEW = "Calendar";
    public static final String ADD_MEETING_VIEW = "AddMeeting";
    public static final String CHOOSE_CALENDARS_VIEW = "ChooseCalendars";
    public static final String MEETING_OVERVIEW_VIEW = "MeetingOverview";
    public static final String MY_GROUPS_VIEW = "MyGroups";
    public static final String NEW_MEETING_VIEW = "NewMeeting";
    public static final String ADD_GROUP_VIEW = "AddGroup";
    public static final String MEETING_ROOM_OVERVIEW_VIEW = "MeetingRoomOverview";
    public static final String NOTIFICATION_VIEW = "Notification";
    public static final String INVITATION_MEETING_VIEW = "InvitationMeeting";
    public static final String CHANGE_ATTENDENCE_VIEW = "ChangeAttendence";
    public static final String EDIT_MEETING_VIEW = "EditMeeting";
    public static final String MEETING_ATTENDENCE_VIEW = "MeetingAttendence	";

	private static LoginUser currentUser;

	public static MainController mainController;

    public List<User> calendars;

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = new AnchorPane();
		dbconnection.DatabaseConnection.startCon();
		
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
        mainController.loadScreen(ADD_GROUP_SCREEN, ADD_GROUP_SCREEN_FXML);
        mainController.loadScreen(MEETING_ROOM_OVERVIEW_SCREEN, MEETING_ROOM_OVERVIEW_SCREEN_FXML);
        mainController.loadScreen(NOTIFICATION_SCREEN, NOTIFICATION_SCREEN_FXML);
        mainController.loadScreen(INVITATION_MEETING_SCREEN, INVITATION_MEETING_SCREEN_FXML);
        mainController.loadScreen(CHANGE_ATTENDENCE_SCREEN, CHANGE_ATTENDENCE_SCREEN_FXML);
        mainController.loadScreen(EDIT_MEETING_SCREEN, EDIT_MEETING_SCREEN_FXML);
        mainController.loadScreen(MEETING_ATTENDENCE_SCREEN, MEETING_ATTENDENCE_FXML);

        // Make view form loaded screens
		mainController.makeView(ADD_MEETING_VIEW, ADD_MEETING_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(LOG_IN_VIEW, LOG_IN_SCREEN);
        mainController.makeView(NEW_USER_VIEW, NEW_USER_SCREEN);
        mainController.makeView(CALENDAR_VIEW, CALENDAR_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(CHOOSE_CALENDARS_VIEW, CHOOSE_CALENDARS_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(MEETING_OVERVIEW_VIEW, MEETING_OVERVIEW_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(MY_GROUPS_VIEW, MY_GROUPS_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(ADD_GROUP_VIEW, ADD_GROUP_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(MEETING_ROOM_OVERVIEW_VIEW, MEETING_ROOM_OVERVIEW_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(NOTIFICATION_VIEW, NOTIFICATION_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(INVITATION_MEETING_VIEW, INVITATION_MEETING_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(CHANGE_ATTENDENCE_VIEW, CHANGE_ATTENDENCE_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(EDIT_MEETING_VIEW, EDIT_MEETING_SCREEN, GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);
        mainController.makeView(MEETING_ATTENDENCE_VIEW, MEETING_ATTENDENCE_SCREEN,GLOBAL_MENU_SCREEN, CALENDAR_LEFT_MENU_SCREEN);

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
		
		//Set up DB connection;
		
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

    public static void ClearAllViews(){
        mainController.getAllControllers().forEach(gui.ControlledScreen::clearView);
    }
}
