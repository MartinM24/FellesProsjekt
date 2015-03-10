package gui;

public interface ControlledScreen {
	/**
	 * Sets parent controller
	 */
	public void setScreenParent(MainController screenPage);

    /**
     * Method runs every time a screen is shown
     */
    public void viewRefresh();

}
