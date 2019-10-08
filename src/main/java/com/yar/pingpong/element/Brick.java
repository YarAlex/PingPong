package com.yar.pingpong.element;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Brick extends AbstractElement {

    int index;
    private double w;
    private double h;

    public Brick(Element parent) {
        super(parent);
    }

    @Override
    protected void drawInit() {
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 20.0,
                20.0, 20.0,
                20.0, 0.0);
        polygon_1.setFill(Color.GREEN);
        w = 20;
        h = 20;
        shape.getChildren().addAll(polygon_1);
    }

    @Override
    public double getWidth() {
        return w;
    }

    @Override
    public double getHeight() {
        return h;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public double getPositionX() {
        return super.getPositionX()+parent.getPositionX();
    }

    @Override
    public double getPositionY() {
        return super.getPositionY()+parent.getPositionY();
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
