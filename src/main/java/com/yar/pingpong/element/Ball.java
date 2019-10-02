package com.yar.pingpong.element;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball extends AbstractElement {

    private Timeline timeline = new Timeline();
    private double SPEED = .5;
    private boolean fly = false;
    private double directionX = 0;
    private double directionY = 1;

    public Ball(Element parent) {
        super(parent);
    }

    public void fly(double destinationX, double destinationY) {
        double spaceX = shape.getLayoutX() - destinationX;
        double spaceY = shape.getLayoutY() - destinationY;

        double timeX = spaceX / SPEED;
        double timeY = spaceY / SPEED;
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

    @Override
    protected void drawInit() {
        Circle circle = new Circle(0, 0, 7);
        circle.setFill(Color.BLUE);
        shape.getChildren().addAll(circle);
    }
}
