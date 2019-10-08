package com.yar.pingpong.element;

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

    public boolean intersects(Element element) {
        double x = getPositionX();
        double y = getPositionY();
        double w = getWidth();
        double h = getHeight();

        return (x + w >= element.getPositionX() &&
                y + h >= element.getPositionY() &&
                x <= element.getPositionX()+element.getWidth() &&
                y <= element.getPositionY()+element.getHeight());
    }

    @Override
    public void draw() {
        log.debug("Start draw "+getClass().getSimpleName());
        drawInit();
        shape.autosize();
    }

    @Override
    public void addChild(Element child) {
        shape.getChildren().addAll(child.getShape());
    }

    @Override
    public Pane getShape() {
        return shape;
    }

    @Override
    public void setPosition(Double x, Double y) {
        shape.relocate(x, y);
    }

    @Override
    public double getPositionX() {
        return shape.getLayoutX();
    }

    @Override
    public double getPositionY() {
        return shape.getLayoutY();
    }


    @Override
    public double getWidth() {
        double w = shape.getBoundsInLocal().getWidth();
        log.debug("getWidth = "+ w);
        return w;
    }

    @Override
    public double getHeight() {
        double h = shape.getBoundsInLocal().getHeight();
        log.debug("getHeight = "+ h);
        return h;
    }

    @Override
    public void show() {
        shape.setVisible(true);
    }

    @Override
    public void hide() {
        shape.setVisible(false);
    }

    abstract void drawInit();
}
