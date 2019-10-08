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
    private double speed = .1;
    private double w;
    private double h;
    private double lastPosY = 0;
    private double lastPosX = 0;
    private boolean onPlatform = true;

    public Ball(Element parent) {
        super(parent);
    }

    public void fly(double destinationX, double destinationY) {
        log.debug("Start fly to: ["+destinationX+","+destinationY+"]");

        lastPosX = getPositionX();
        lastPosY = getPositionY();

        double spaceX = Math.abs(shape.getLayoutX() - destinationX);
        double spaceY = Math.abs(shape.getLayoutY() - destinationY);
        double timeX = spaceX / speed;
        double timeY = spaceY / speed;
        double time = timeX > timeY ? timeX : timeY;

        log.trace("Space : ["+spaceX+","+spaceY+"]");

        KeyValue keyValueY = new KeyValue(shape.layoutYProperty(), destinationY);
        KeyFrame keyFrameY = new KeyFrame(Duration.millis(time), keyValueY);

        KeyValue keyValueX = new KeyValue(shape.layoutXProperty(), destinationX);
        KeyFrame keyFrameX = new KeyFrame(Duration.millis(time), keyValueX);

        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(keyFrameY);
        timeline.getKeyFrames().add(keyFrameX);
        timeline.play();
    }

    public void calculateBallRebound(Element barrier) {
        stopFly();
        log.trace("Intersects with ball, positionX = "+getPositionX()+", positionY = "+getPositionY());
        log.trace("Barrier bounds: positionX = "+barrier.getPositionX()+", positionY = "+barrier.getPositionY());
        double lastPosX = getLastPosX();
        double lastPosY = getLastPosY();
        double a = Math.abs(lastPosX - getPositionX());
        double b = Math.abs(lastPosY - getPositionY());
        double tanAngel = a/b;
        double a2 = 0;
        double b2 = 0;
        double destX = 0;
        double destY = 0;
        String intersectPosition = getIntersectPosition(barrier, tanAngel);

        if ("+y".equals(intersectPosition) || "-y".equals(intersectPosition)) {
            if (getPositionX() > lastPosX) {   // left -> right
                destX = 300;
                a2 = destX - getPositionX();
            } else {                                //right -> left
                a2 = getPositionX();
                destX = 0;
            }
            b2 = a2/tanAngel;
            if (getPositionY() > lastPosY) {   //up -> down
                destY = getPositionY() - b2;
            } else {                                //down -> up
                destY = getPositionY() + b2;
            }
        }
        if ("+x".equals(intersectPosition) || "-x".equals(intersectPosition)) {
            if (getPositionY() > lastPosY) {   //up -> down
                destY = 235;
                b2 = destY - getPositionY();
            } else {                                //down -> up
                destY = 0;
                b2 = getPositionY();
            }
            a2 = b2*tanAngel;
            if (getPositionX() > lastPosX) {   // left -> right
                destX = getPositionX() - a2;
            } else {                                //right -> left
                destX = getPositionX() + a2;
            }
        }

        log.trace("Angel = "+ Math.atan(tanAngel));
        log.trace("a2 = "+a2+", b2 = "+b2);
        log.trace("DestX = "+destX+", destY = "+destY);

        fly(destX, destY);
    }

    private String getIntersectPosition(Element barrier, double ratio) {
        String intersectsPosition = "";
        double lastPosX = getLastPosX();
        double lastPosY = getLastPosY();
        double a2 = 0;
        double b2 = 0;
        double newX = 0;
        double newY = 0;

        //down -> up,  left -> right
        if (lastPosY > getPositionY() && lastPosX < getPositionX()) {
            b2 = lastPosY - (barrier.getPositionY()+barrier.getHeight());
            a2 = lastPosX + b2*ratio + 12;
            if (a2 < barrier.getPositionX()) {
                intersectsPosition = "+x";
                newX = barrier.getPositionX() - 12 - .1;
                newY = lastPosY - Math.abs(newX- lastPosX)/ratio;
            } else {
                intersectsPosition = "-y";
                newY = barrier.getPositionY() + barrier.getHeight() + .1;
                newX = a2 - 12;
            }
        }
        //down - > up,  right -> left
        if (lastPosY > getPositionY() && lastPosX > getPositionX()) {
            b2 = lastPosY - (barrier.getPositionY()+barrier.getHeight());
            a2 = lastPosX - b2*ratio;
            if (a2 > barrier.getPositionX()+barrier.getWidth()) {
                intersectsPosition = "-x";
                newX = barrier.getPositionX()+barrier.getWidth() + .1;
                newY = lastPosY - Math.abs(lastPosX -newX)/ratio;
            } else {
                intersectsPosition = "-y";
                newY = barrier.getPositionY()+barrier.getHeight()+.1;
                newX = a2;
            }
        }
        //up -> down,  left -> right
        if (lastPosY < getPositionY() && lastPosX < getPositionX()) {
            b2 = barrier.getPositionY() - lastPosY - 12;
            a2 = lastPosX + b2*ratio + 12;
            if (a2 < barrier.getPositionX()) {
                intersectsPosition = "+x";
                newX = barrier.getPositionX() - 12 - .1;
                newY = lastPosY + Math.abs(newX- lastPosX)/ratio;
            } else {
                intersectsPosition = "+y";
                newY = barrier.getPositionY() - 12 - .1;
                newX = a2 - 12;
            }
        }
        //up -> down,  right -> left
        if (lastPosY < getPositionY() && lastPosX > getPositionX()) {
            b2 = barrier.getPositionY() - lastPosY - 12;
            a2 = lastPosX - b2*ratio;
            if (a2 > barrier.getPositionX()+barrier.getWidth()) {
                intersectsPosition = "-x";
                newX = barrier.getPositionX()+barrier.getWidth() + .1;
                newY = lastPosY + Math.abs(lastPosX -newX)/ratio;
            } else {
                intersectsPosition = "+y";
                newY = barrier.getPositionY() - 12 - .1;
                newX = a2;
            }
        }
        setPosition(newX, newY);

        log.trace("New ball, PositionX = "+getPositionX()+", PositionY = "+getPositionY());
        log.trace("ratio = "+ratio+", a2 = "+a2+", b2 = "+b2);
        log.trace("Intersects position = " + intersectsPosition);

        return intersectsPosition;
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
        if (timeline.getStatus().equals(Animation.Status.PAUSED)) {
            timeline.play();
        }
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
        w = 12;
        h = 12;
        shape.getChildren().addAll(circle);
    }

    @Override
    public double getWidth() {
        return w;
    }

    @Override
    public double getHeight() {
        return h;
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

    private double getLastPosY() {
        return lastPosY;
    }

    private double getLastPosX() {
        return lastPosX;
    }
}
