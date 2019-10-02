package com.yar.pingpong.element;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Brick extends AbstractElement {

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
        shape.getChildren().addAll(polygon_1);
    }
}
