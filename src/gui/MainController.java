package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.util.HashMap;

public class MainController extends BorderPane {
	/**
	 * Controller to control multiple screens
	 */
	private HashMap<String, View> views = new HashMap<String, View>();
    private HashMap<String, Node> screens = new HashMap<>();
	/**
	 * Adds view
	 * @param name - Name of the view
	 * @param view - Root node of the view
	 */

	private void addView(String name, View view) {
	       views.put(name, view);
	}

    private void addScreen(String name, Node screen) {
        screens.put(name,screen);
    }

    private Node getScreen(String name) {
        return screens.get(name);
    }

    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen controller =  myLoader.getController();
            controller.setScreenParent(this);
            addScreen(name, loadScreen);
            System.out.println("Loading " + name + " from " + resource);
            return true;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

	public void makeView(String name, String centerName, String bottomName, String leftName) {
        Node center = getScreen(centerName);
        Node bottom = getScreen(bottomName);
        Node left = getScreen(leftName);
        if (center != null & bottom != null & left != null) {
            View view = new View(center, bottom, left);
            addView(name, view);
        } else {
            throw new IllegalArgumentException("One or more screens has't been loaded");
        }
	}

    public void makeView(String name, String centerName, String bottomName) {
        Node center = getScreen(centerName);
        Node bottom = getScreen(bottomName);
        if (center != null & bottom != null) {
            View view = new View(center, bottom);
            addView(name, view);
        } else {
            throw new IllegalArgumentException("One or more screens has't been loaded");
        }
	}

    public void makeView(String name, String centerName) {
        if (centerName != null) {
            View view = new View(getScreen(centerName));
            addView(name, view);
        } else {
            throw new IllegalArgumentException("One or more screens has't been loaded");
        }
	}

	public boolean setView(final String name) {
	     View view = views.get(name);
		if(view != null) { //view loaded
            setCenter(view.getCenter());
            setBottom(view.getBottom());
            setLeft(view.getLeft());
            return true;
	     } else {
            System.out.println("View hasn't been loaded!\n");
            //TODO add fail to load screen?
	        return false;
	     }
	 }

	 public boolean unloadScreen(String name) {
	     if(views.remove(name) == null) {
	       System.out.println("View didn't exist");
	       return false;
	     } else {
             return true;
         }
	 }


}
