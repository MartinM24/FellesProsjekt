package gui;

import javafx.scene.Node;

/**
 * Created by Martin on 06.03.15.
 */

public class View {
    private Node center = null;
    private Node bottom = null;
    private Node left = null;

    public View(Node center, Node bottom, Node left) {
        this.center = center;
        this.bottom = bottom;
        this.left = left;
    }

    public View(Node center, Node bottom) {
        this.center = center;
        this.bottom = bottom;
    }

    public View(Node center) {
        this.center = center;
    }

    public Node getCenter() {
        return center;
    }

    public Node getBottom() {
        return bottom;
    }

    public Node getLeft() {
        return left;
    }

    public void setCenter(Node center) {
        this.center = center;
    }

    public void setBottom(Node bottom) {
        this.bottom = bottom;
    }

    public void setLeft(Node left) {
        this.left = left;
    }
}

