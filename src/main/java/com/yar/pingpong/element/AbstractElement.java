package com.yar.pingpong.element;

import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractElement implements Element {

    protected Logger log = Logger.getLogger(getClass().getSimpleName());
    protected Pane shape = new Pane();
    protected Pane parent;
    protected List<Coordinate> matrix = new ArrayList<>();

    public AbstractElement(Pane parent) {
        this.parent = parent;
        this.parent.getChildren().addAll(shape);
    }

    public void calculateMatrix() {
        log.debug("Calculate matrix");
        matrix.clear();
        shape.autosize();
        for (int c = 0; c < getWidth(); c++) {
            int x = (int) getPositionX() + c;
            for (int cc = 0; cc < getHeight(); cc++) {
                int y = (int) getPositionY() + cc;
                Coordinate coordinate = new Coordinate(x, y);
                log.trace("Coordinate: "+coordinate);
                matrix.add(coordinate);
            }
        }
        log.debug("Matrix size = " + matrix.size());
    }

    public List<Coordinate> getMatrix() {
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
    public void show() {
        shape.setVisible(true);
    }

    @Override
    public void hide() {
        shape.setVisible(false);
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
}
