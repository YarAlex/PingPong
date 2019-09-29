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
    private double SPEED = 0.5;

    public Platform(Pane parent) {
        this.parent = parent;
        this.parent.getChildren().add(shape);
    }

    public void moveRight() {
        double space = parent.getWidth() - shape.getWidth() - shape.getTranslateX();
        double time = space / SPEED;
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.translateXProperty(), parent.getWidth() - shape.getWidth());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void moveLeft() {
        double space = shape.getTranslateX();
        double time = space / SPEED;
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.translateXProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void stopMove() {
        timeline.stop();
    }

    @Override
    public void draw() {
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 10.0,
                50.0, 10.0,
                50.0, 0.);
        polygon_1.setFill(Color.RED);

        shape.getChildren().addAll(polygon_1);
        setPosition((shape.getScene().getWindow().getWidth() - 50) / 2, shape.getScene().getWindow().getHeight() - 100.0);
    }
}
