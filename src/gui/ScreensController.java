package gui;

import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ScreensController extends StackPane {
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
	
	
	 public boolean setScreen(final String name) { 
	     if(screens.get(name) != null) { //screen loaded 
	       //Is there is more than one screen 
	       if(!getChildren().isEmpty()){ 
	    	   //remove displayed screen 
	           getChildren().remove(0); 
	           //add new screen 
	           getChildren().add(0, screens.get(name));  
	       } else { 
	         //no one else been displayed, then just show 
	         getChildren().add(screens.get(name)); 
	       } 
	       return true; 
	     } else { 
	         System.out.println("screen hasn't been loaded!\n"); 
	         return false; 
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
