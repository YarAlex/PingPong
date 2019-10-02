package com.yar.pingpong.element;

import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractElement implements Element {

    protected Logger log = Logger.getLogger(getClass().getSimpleName());
    protected Pane shape = new Pane();
    protected Element parent;
    protected List<Coordinate> matrix = new CopyOnWriteArrayList<>();

    public AbstractElement(Element parent) {
        this.parent = parent;
        if (parent != null) {
            this.parent.addChild(this);
        }
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

    public void calculateMatrix() {
        log.debug("Calculate matrix");
        matrix.clear();
        for (int c = 0; c < getWidth(); c++) {
            int x = getAbsolutePositionX() + c;
            for (int cc = 0; cc < getHeight(); cc++) {
                int y = getAbsolutePositionY() + cc;
                Coordinate coordinate = new Coordinate(x, y);
                log.trace("Coordinate: "+coordinate);
                matrix.add(coordinate);
            }
        }
        log.debug("Matrix size = " + matrix.size());
    }

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

    public List<Coordinate> getMatrix() {
        if (matrix.isEmpty()) {
            calculateMatrix();
        }
        return matrix;
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
