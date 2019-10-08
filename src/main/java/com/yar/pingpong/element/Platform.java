package com.yar.pingpong.element;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Platform extends AbstractElement {

    private Timeline timeline = new Timeline();
    private double speed = 0.250;
    private double w;
    private double h;

    public Platform(Element parent) {
        super(parent);
        //KeyHandler.getInstance().addKeyHandlerEvent(event -> processKey(event));
    }

    /*private void processKey(KeyEvent event) {
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
    }*/

    /*private void moveRight() {
        if (isMoving()) {
            return;
        }
        double space = parent.getWidth() - getWidth() - shape.getLayoutX();
        double time = space / speed;
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.layoutXProperty(), parent.getWidth() - getWidth());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void moveLeft() {
        if (isMoving()) {
            return;
        }
        double space = shape.getLayoutX();
        double time = space / speed;
        timeline.getKeyFrames().clear();
        KeyValue keyValue = new KeyValue(shape.layoutXProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }*/

    public void stopMove() {
        timeline.stop();
    }

    public boolean isMoving() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    @Override
    protected void drawInit() {
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 10.0,
                50.0, 10.0,
                50.0, 0.);
        polygon_1.setFill(Color.RED);
        w = 50;
        h = 10;
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

    public Timeline getTimeline() {
        return timeline;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
