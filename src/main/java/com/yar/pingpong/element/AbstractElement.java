package com.yar.pingpong.element;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public abstract class AbstractElement implements Element {

    protected Logger log = Logger.getLogger(getClass().getSimpleName());
    protected Pane shape = new Pane();
    protected Element parent;

    public AbstractElement(Element parent) {
        this.parent = parent;
        if (parent != null) {
            this.parent.addChild(this);
        }
    }

    public Bounds getBounds() {
        return parent.getShape().localToParent(shape.getBoundsInParent());
    }

    @Override
    public void addChild(Element child) {
        shape.getChildren().addAll(child.getShape());
    }

    @Override
    public void draw() {
        log.debug("Start draw "+getClass().getSimpleName());
        drawInit();
        shape.autosize();
    }

    abstract void drawInit();

    protected int getAbsolutePositionX() {
        int ret = getPositionX();
        if (parent != null) {
            ret += ((AbstractElement)parent).getAbsolutePositionX();
        }
        return ret;
    }

    protected int getAbsolutePositionY() {
        int ret = getPositionY();
        if (parent != null) {
            ret += ((AbstractElement)parent).getAbsolutePositionY();
        }
        return ret;
    }

    @Override
    public int getPositionX() {
        return (int) shape.getLayoutX();
    }

    @Override
    public void setPositionX(int x) {
        shape.setLayoutX(x);
    }

    @Override
    public int getPositionY() {
        return (int) shape.getLayoutY();
    }

    @Override
    public void setPositionY(int y) {
        shape.setLayoutY(y);
    }

    @Override
    public void setPosition(double x, double y) {
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    @Override
    public double getWidth() {
        double w = shape.getWidth();
        log.debug("getWidth = "+ w);
        return w;
    }

    @Override
    public double getHeight() {
        double h = shape.getHeight();
        log.debug("getHeight = "+ h);
        return h;
    }

    @Override
    public Pane getShape() {
        return shape;
    }

    @Override
    public void show() {
        shape.setVisible(true);
    }

    @Override
    public void hide() {
        shape.setVisible(false);
    }
}
