package com.yar.pingpong.element;

import com.yar.pingpong.KeyHandler;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Platform extends AbstractElement {

    private Timeline timeline = new Timeline();
    private double SPEED = 0.5;
    private boolean moving = false;

    public Platform(Element parent) {
        super(parent);
        KeyHandler.getInstance().addKeyHandlerEvent(event -> processKey(event));
    }

    private void processKey(KeyEvent event) {
        if (event.getEventType().toString().equals(KeyHandler.KEY_RELEASED)) {
            stopMove();
            return;
        }
        switch (event.getCode()) {
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    private void moveRight() {
        if (moving) {
            return;
        }
        moving = true;
        double space = parent.getWidth() - getWidth() - shape.getLayoutX();
        double time = space / SPEED;
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.layoutXProperty(), parent.getWidth() - getWidth());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void moveLeft() {
        if (moving) {
            return;
        }
        moving = true;
        double space = shape.getLayoutX();
        double time = space / SPEED;
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.layoutXProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void stopMove() {
        timeline.stop();
        moving = false;
    }

    @Override
    protected void drawInit() {
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 10.0,
                50.0, 10.0,
                50.0, 0.);
        polygon_1.setFill(Color.RED);
        shape.getChildren().addAll(polygon_1);
    }
}
