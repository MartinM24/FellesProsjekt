package gui;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MainController extends BorderPane {
	/**
	 * Controller to control multiple screens
	 */
	HashMap<String, Node> screens = new HashMap<String, Node>();

	/**
	 * Adds screen
	 * @param name - Name of the screen
	 * @param screen - Root node of the screen
	 */
	private void addScreen(String name, Node screen) {
	       screens.put(name, screen);
	}

	public boolean loadScreen(String name, String resource) {
	     try {
	       FXMLLoader myLoader = new
	               FXMLLoader(getClass().getResource(resource));
	       Parent loadScreen = (Parent) myLoader.load();
	       ControlledScreen myScreenControler =
	              ((ControlledScreen) myLoader.getController());
	       myScreenControler.setScreenParent(this);
	       addScreen(name, loadScreen);
	       return true;
	     }catch(Exception e) {
             System.out.println(e.getMessage());
             e.printStackTrace();
             return false;
         }
	}

    public void setCenter(final String name) {
        setCenter(getScreen(name));
    }

    public void setBottom(final String name) {
        setLeft(getScreen(name));
    }

    public void setLeft(final String name) {
        setLeft(getScreen(name));
    }



	 private Node getScreen(final String name) {
	     Node node = screens.get(name);
		if(node != null) { //screen loaded
            return node;
	     } else {
            System.out.println("screen hasn't been loaded!\n");
            //TODO add fail to load screen?
	        return null;
	     }
	 }

	 public boolean unloadScreen(String name) {
	     if(screens.remove(name) == null) {
	       System.out.println("Screen didn't exist");
	       return false;
	     } else {
             return true;
         }
	 }


}
