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
    public void draw(Object parent) {
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 10.0,
                50.0, 10.0,
                50.0, 0.);
        polygon_1.setFill(Color.RED);

        shape.getChildren().addAll(polygon_1);
        if (parent instanceof Pane) {
            this.parent = (Pane) parent;
        }
        if (this.parent != null) {
            this.parent.getChildren().add(shape);
        }
        setPosition((this.parent.getWidth() - 50) / 2, this.parent.getHeight() - 10);
    }
}
