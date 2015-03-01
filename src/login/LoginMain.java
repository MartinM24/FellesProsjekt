package login;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginMain extends LogIn {
	
	public void start(Stage stage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("GUI Logg inn.fxml"));
	    stage.setTitle("Logg Inn");
	    stage.setScene(new Scene(root, 900, 600)); //eksempelstørrelser
	    stage.show();
	    }
	
	
	//Can't have this method here, bc this class does not extend Application
	public static void main(String[] args) {
		launch(args);
	}

}
