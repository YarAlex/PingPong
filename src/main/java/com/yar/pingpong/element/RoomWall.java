package com.yar.pingpong.element;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class RoomWall extends AbstractElement{

    private boolean floor = false;
    private Type type;
    private Color color = Color.BLACK;

    public RoomWall(Element parent, Type type) {
        super(parent);
        this.type = type;
    }

    @Override
    void drawInit() {
        switch (type) {
            case TOP:
                drawTop();
                break;
            case LEFT:
                drawLeft();
                break;
            case RIGHT:
                drawRight();
                break;
            case FLOOR:
                drawFloor();
                break;
        }
    }

    private void drawFloor() {
        floor = true;
        double h = parent.getShape().getScene().getWindow().getHeight()-32;
        double w = parent.getShape().getScene().getWindow().getWidth();
        drawTop();
        this.setPosition(0.0, h);
    }

    private void drawTop() {
        double h = parent.getShape().getScene().getWindow().getHeight();
        double w = parent.getShape().getScene().getWindow().getWidth();
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 1.0,
                w, 1.0,
                w, 0.0);
        polygon_1.setFill(color);
        shape.getChildren().addAll(polygon_1);
    }

    private void drawRight() {
        double h = parent.getShape().getScene().getWindow().getHeight();
        double w = parent.getShape().getScene().getWindow().getWidth()-1;
        drawLeft();
        this.setPosition(w,0.0);
    }

    private void drawLeft() {
        double h = parent.getShape().getScene().getWindow().getHeight();
        double w = parent.getShape().getScene().getWindow().getWidth();
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                1.0, 0.0,
                1.0, h,
                0.0, h);
        polygon_1.setFill(color);
        shape.getChildren().addAll(polygon_1);
    }

    public boolean isFloor() {
        return floor;
    }

    public enum Type {
        LEFT, RIGHT, TOP, FLOOR
    }
}
