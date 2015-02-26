package login;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends LogIn{
	
	public void start(Stage stage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("GUI Logg inn.fxml"));
	    stage.setTitle("Logg Inn");
	    stage.setScene(new Scene(root, 900, 600)); //eksempelstørrelser
	    stage.show();
	    }
	
	//public static void main(String[] args) {
	//	launch(args);
	//}

}
