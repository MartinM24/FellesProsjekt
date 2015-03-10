package gui;

import javafx.scene.Node;

/**
 * Created by Martin on 06.03.15.
 */

public class View {

    private String center = null;
    private String bottom = null;
    private String left = null;

    public View(String center, String bottom, String left) {
        this.center = center;
        this.bottom = bottom;
        this.left = left;
    }

    public View(String center, String bottom) {
        this.center = center;
        this.bottom = bottom;
    }

    public View(String center) {
        this.center = center;
    }

    public String getCenter() {
        return center;
    }

    public String getBottom() {
        return bottom;
    }

    public String getLeft() {
        return left;
    }

}

