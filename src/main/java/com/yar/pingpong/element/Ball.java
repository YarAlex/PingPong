package com.yar.pingpong.element;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Ball extends AbstractElement {

    private Timeline timeline = new Timeline();
    private boolean onPlatform = true;
    private double speed = .5;
    private double directionX = 0;
    private double directionY = 1;

    public Ball(Element parent) {
        super(parent);
    }

    public void fly(double destinationX, double destinationY) {
        log.debug("Start fly: ["+destinationX+","+destinationY+"]");
        double spaceX = Math.abs(shape.getLayoutX() - destinationX);
        double spaceY = Math.abs(shape.getLayoutY() - destinationY);
        log.trace("Space : ["+spaceX+","+spaceY+"]");

        double timeX = spaceX / speed;
        double timeY = spaceY / speed;
        double time = timeX > timeY ? timeX : timeY;

        KeyValue keyValueY = new KeyValue(shape.layoutYProperty(), destinationY);
        KeyFrame keyFrameY = new KeyFrame(Duration.millis(time), keyValueY);

        KeyValue keyValueX = new KeyValue(shape.layoutXProperty(), destinationX);
        KeyFrame keyFrameX = new KeyFrame(Duration.millis(time), keyValueX);

        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(keyFrameY);
        timeline.getKeyFrames().add(keyFrameX);
        timeline.play();
    }

    public void stopFly() {
        timeline.stop();
    }

    public boolean isFly() {
        return timeline.getStatus() == Animation.Status.RUNNING;
    }

    public void pauseFly() {
        timeline.pause();
    }

    public void continueFly() {
        timeline.play();
    }

    @Override
    protected void drawInit() {
        //Circle circle = new Circle(0, 0, 6);  //todo
        Polygon circle = new Polygon();
        circle.getPoints().addAll(0.0, 0.0,
                0.0, 12.0,
                12.0, 12.0,
                12.0, 0.0);
        circle.setFill(Color.BLUE);
        shape.getChildren().addAll(circle);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isOnPlatform() {
        return onPlatform;
    }

    public void setOnPlatform(boolean onPlatform) {
        this.onPlatform = onPlatform;
    }

    public Timeline getTimeline() {
        return timeline;
    }
}
