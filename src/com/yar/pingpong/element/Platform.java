package com.yar.pingpong.element;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Platform extends AbstractElement {

    private Pane parent;
    private Timeline timeline = new Timeline();
    private Integer MOVE_TIME = 500;

    public void moveRight() {
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.translateXProperty(), parent.getWidth()-shape.getWidth());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(MOVE_TIME), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void moveLeft() {
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.translateXProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(MOVE_TIME), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void stopMove() {
        timeline.stop();
    }

    @Override
    public void draw(Object parent) {
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 10.0,
                10.0, 10.0,
                10.0, 0.);
        polygon_1.setFill(Color.RED);

        Polygon polygon_2 = new Polygon();
        polygon_2.getPoints().addAll(0.0, 15.0,
                0.0, 25.0,
                10.0, 25.0,
                10.0, 15.0);
        polygon_2.setFill(Color.GREEN);

        shape.getChildren().addAll(polygon_1, polygon_2);
        if (parent instanceof Pane) {
            this.parent = (Pane) parent;
        }
        if (this.parent != null) {
            this.parent.getChildren().add(shape);
        }
    }
}
