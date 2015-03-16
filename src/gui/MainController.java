package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainController extends BorderPane {
	/**
	 * Controller to control multiple screens
	 */
	private HashMap<String, View> views = new HashMap<>();
    private HashMap<String, Node> screens = new HashMap<>();
    private HashMap<String, ControlledScreen> controllers = new HashMap<>();
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

    private void addController(String name, ControlledScreen ctrl ) {
        controllers.put(name, ctrl);
    }

    private Node getScreen(String name) {
        return screens.get(name);
    }

    private ControlledScreen getController(String name){
        return controllers.get(name);
    }

    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen controller =  myLoader.getController();
            controller.setScreenParent(this);
            addScreen(name, loadScreen);
            addController(name, controller);
            return true;
        }catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

	public void makeView(String name, String centerName, String bottomName, String leftName) {
        View view = new View(centerName, bottomName, leftName);
        addView(name, view);
	}

    public void makeView(String name, String centerName, String bottomName) {
        View view = new View(centerName, bottomName);
        addView(name, view);
	}

    public void makeView(String name, String centerName) {
        View view = new View(centerName);
        addView(name, view);
	}

	public boolean setView(final String name) {
	     View view = views.get(name);
		if(view != null) { //view loaded
            setCenterScreen(view.getCenter());
            setBottomScreen(view.getBottom());
            setLeftScreen(view.getLeft());
            return true;
	     } else {
	        return false;
	     }
	 }

    private void setCenterScreen(String name){
        if (name != null) {
            setCenter(getScreen(name));
            getController(name).viewRefresh();
        } else {
            setCenter(null);
        }
    }

    private void setBottomScreen(String name){
        if (name != null) {
            setBottom(getScreen(name));
            getController(name).viewRefresh();
        } else {
            setBottom(null);
        }
    }

    private void setLeftScreen(String name){
        if (name != null) {
            setLeft(getScreen(name));
            getController(name).viewRefresh();
        } else {
            setLeft(null);
        }
    }

    /**
     * Gets controller for screen with name
      * @param name of the screen
     * @return Controller for screen
     */

    public ControlledScreen getControllerForScreen(String name) {
        ControlledScreen ctrl = controllers.get(name);
        if(ctrl != null) {
            return ctrl;
        } else {
            throw new IllegalArgumentException("Screen with string name does't excict");
        }
    }

    public List<ControlledScreen> getAllControllers() {
        List<ControlledScreen> res = controllers.values().stream().collect(Collectors.toList());
        return res;
    }

}
