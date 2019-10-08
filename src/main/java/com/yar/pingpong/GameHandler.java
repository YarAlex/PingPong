package com.yar.pingpong;

import com.yar.pingpong.element.AbstractWall;
import com.yar.pingpong.element.Ball;
import com.yar.pingpong.element.Element;
import com.yar.pingpong.element.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private Logger log = Logger.getLogger(getClass().getSimpleName());

    private Platform platform;
    private Ball ball;
    private Element room;
    private AbstractWall wall;
    private List<Element> roomWalls = new ArrayList<>();
    boolean stop = false;


    public GameHandler() {
        KeyHandler.getInstance().addKeyHandlerEvent(event -> processKey(event));
        new Thread(() -> platformHandler()).start();
    }

    private void processKey(KeyEvent event) {
        boolean keyReleased = event.getEventType().toString().equals(KeyHandler.KEY_RELEASED);
        switch (event.getCode()) {
            case SPACE:
                if (!keyReleased && !ball.isFly()) {
                    ball.setOnPlatform(false);
                    ball.setPosition(ball.getPositionX(), /*ball.getPositionY()-.1*/26.);
                    lastDestX = ball.getPositionX();
                    lastDestY = ball.getPositionY();
                    ball.fly(lastDestX+199, 235);
                    new Thread(() -> intersectsHandler()).start();
                }
                break;
            case LEFT:
                moveLeft(keyReleased);
                break;
            case RIGHT:
                moveRight(keyReleased);
                break;
        }
    }

    public void intersectsHandler() {
        while (!stop) {
            List<Element> barriers = new ArrayList<>();
            barriers.addAll(wall.getBricks());
            barriers.add(platform);
            //barriers.addAll(roomWalls);
            for (Element barrier : barriers) {
                if (intersects(barrier)) {
                    ball.stopFly();
                    log.debug("Intersects with ball, PositionX = "+ball.getPositionX()+", PositionY = "+ball.getPositionY());
                    log.debug("Barrier bounds: "+barrier.getBounds());
                    String intersectPosition = getIntersectPosition(barrier);
                    log.debug("New ball, PositionX = "+ball.getPositionX()+", PositionY = "+ball.getPositionY());
                    //calculateBallRebound(intersectPosition);
                    stopped();
                }
            }
        }
    }

    private boolean intersects(Element element) {
        double x = ball.getPositionX();
        double y = ball.getPositionY();
        double w = ball.getWidth();
        double h = ball.getHeight();

        return (x + w >= element.getPositionX() &&
                y + h >= element.getPositionY() &&
                x <= element.getPositionX()+element.getWidth() &&
                y <= element.getPositionY()+element.getHeight());
    }

    double destX = 0;
    double destY = 0;

    double lastDestY = 0;
    double lastDestX = 0;

    private void calculateBallRebound(String intersectPosition) {
        double tanAngel= 0;
        double a;
        double b;

        b = Math.abs(lastDestX - ball.getPositionX());
        a = Math.abs(lastDestY - ball.getPositionY());
        if ("y".equals(intersectPosition)) {
            double b2;
            double a2;
            if (ball.getPositionX() > lastDestX) {
                b2 = 300-ball.getPositionX();
                destX = 300;
            } else {
                b2 = ball.getPositionX();
                destX = 0;
            }
            tanAngel = a/b;
            a2 = b2*tanAngel;
            if (ball.getPositionY() > lastDestY) {
                destY = ball.getPositionY() - a2;
            } else {
                destY = ball.getPositionY() + a2;
            }
        }

        /*if ("x".equals(intersectPosition)) {
            double b2;
            double a2;
            if (ball.getPositionX() > lastDestX) {
                b2 = 300-ball.getPositionX();
                destX = 300;
            } else {
                b2 = ball.getPositionX();
                destX = 0;
            }
            tanAngel = a/b;
            a2 = b2*tanAngel;
            if (ball.getPositionY() > lastDestY) {
                destY = ball.getPositionY() - a2;
            } else {
                destY = ball.getPositionY() + a2;
            }
        }*/

        log.trace("Angel = "+ Math.atan(tanAngel));
        lastDestX = ball.getPositionX();
        lastDestY = ball.getPositionY();

        ball.fly(destX, destY);
    }

    private String getIntersectPosition(Element barrier) {
        String intersectsPosition = "";

        double a = Math.abs(lastDestX - ball.getPositionX());
        double b = Math.abs(lastDestY - ball.getPositionY());
        double ratio = a/b;
        double a2 = 0;
        double b2 = 0;
        double newX = 0;
        double newY = 0;

        //down -> up,  left -> right
        if (lastDestY > ball.getPositionY() && lastDestX < ball.getPositionX()) {
            b2 = lastDestY - (barrier.getPositionY()+barrier.getHeight());
            a2 = lastDestX + b2*ratio + 12;
            if (a2 < barrier.getPositionX()) {
                intersectsPosition = "+x";
                newX = barrier.getPositionX() - 12 - .1;
                newY = lastDestY - (newX-lastDestX)/ratio;
            } else {
                intersectsPosition = "-y";
                newY = barrier.getPositionY() + barrier.getHeight() + .1;
                newX = lastDestX + b2*ratio;
            }
        }
        //down - > up,  right -> left
        if (lastDestY > ball.getPositionY() && lastDestX > ball.getPositionX()) {
            b2 = lastDestY - (barrier.getPositionY()+barrier.getHeight());
            a2 = lastDestX - b2*ratio;
            if (a2 > barrier.getPositionX()+barrier.getWidth()) {
                intersectsPosition = "-x";
                newX = barrier.getPositionX()+barrier.getWidth() + .1;
                newY = lastDestY - (lastDestX-newX)/ratio;
            } else {
                intersectsPosition = "-y";
                newY = barrier.getPositionY()+barrier.getHeight()+.1;
                newX = lastDestX - b2*ratio;
            }
        }
        //up -> down,  left -> right
        if (lastDestY < ball.getPositionY() && lastDestX < ball.getPositionX()) {
            b2 = barrier.getPositionY() - lastDestY - 12;
            a2 = lastDestX + (b2*ratio + 12);
            if (a2 < barrier.getPositionX()) {
                intersectsPosition = "+x";
                newX = barrier.getPositionX() - 12 - .1;
                newY = lastDestY + (newX-lastDestX)/ratio;
            } else {
                intersectsPosition = "+y";
                newY = barrier.getPositionY() - 12 - .1;
                newX = lastDestX + b2*ratio;
            }
        }
        //up -> down,  right -> left
        if (lastDestY < ball.getPositionY() && lastDestX > ball.getPositionX()) {
            b2 = barrier.getPositionY() - lastDestY - 12;
            a2 = lastDestX - b2*ratio;
            if (a2 > barrier.getPositionX()+barrier.getWidth()) {
                intersectsPosition = "-x";
                newX = barrier.getPositionX()+barrier.getWidth() + .1;
                newY = lastDestY + (lastDestX-newX)/ratio;
            } else {
                intersectsPosition = "+y";
                newY = barrier.getPositionY() - 12 - .1;
                newX = lastDestX - b2*ratio;
            }
        }
        ball.setPosition(newX, newY);
        log.trace("a = "+a+", a = "+b+", ratio = "+ratio+", a2 = "+a2+", b2 = "+b2);
        log.trace("Intersects position = " + intersectsPosition);
        return intersectsPosition;
    }


    private void moveRight(boolean keyReleased) {
        if (keyReleased && platform.isMoving()) {
            platform.stopMove();
            if (ball.isOnPlatform()) {
                ball.stopFly();
            }
        } else if (!platform.isMoving()) {
            double space = room.getWidth() - platform.getWidth() - platform.getPositionX();
            double time = space / platform.getSpeed();
            KeyValue keyValue = new KeyValue(platform.getShape().layoutXProperty(), room.getWidth() - platform.getWidth());
            KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
            platform.getTimeline().getKeyFrames().clear();
            platform.getTimeline().getKeyFrames().add(keyFrame);
            platform.getTimeline().play();
            if (ball.isOnPlatform()) {
                moveBallRight();
            }
        }
    }

    private void moveLeft(boolean keyReleased) {
        if (keyReleased && platform.isMoving()) {
            platform.stopMove();
            if (ball.isOnPlatform()) {
                ball.stopFly();
            }
        } else if (!platform.isMoving()) {
            double space = platform.getPositionX();
            double time = space / platform.getSpeed();
            KeyValue keyValue = new KeyValue(platform.getShape().layoutXProperty(), 0);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
            platform.getTimeline().getKeyFrames().clear();
            platform.getTimeline().getKeyFrames().add(keyFrame);
            platform.getTimeline().play();
            if (ball.isOnPlatform()) {
                moveBallLeft();
            }
        }
    }

    private void moveBallRight() {
        double xOnPlatform = (ball.getPositionX() - platform.getPositionX());
        double endValue = room.getWidth() - platform.getWidth() + xOnPlatform;
        double space = endValue - ball.getPositionX();
        if (space <= 0)
        {
            return;
        }
        double time = space / platform.getSpeed();
        KeyValue keyValue = new KeyValue(ball.getShape().layoutXProperty(), endValue);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        ball.getTimeline().getKeyFrames().clear();
        ball.getTimeline().getKeyFrames().add(keyFrame);
        ball.getTimeline().play();
    }

    private void moveBallLeft() {
        double xOnPlatform = (ball.getPositionX() - platform.getPositionX());
        double endValue = xOnPlatform;
        double space = ball.getPositionX() - xOnPlatform;
        if (space <= 0)
        {
            return;
        }
        double time = space / platform.getSpeed();
        KeyValue keyValue = new KeyValue(ball.getShape().layoutXProperty(), endValue);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(time), keyValue);
        ball.getTimeline().getKeyFrames().clear();
        ball.getTimeline().getKeyFrames().add(keyFrame);
        ball.getTimeline().play();
    }

    private void platformHandler() {
        /*platform.getShape().autosize();
        //double lastX = platform.getPositionX();
        while (!stop) {
            if (platform.isMoving() && ball.isOnPlatform()) {
                log.info("2");
                //double currentX = platform.getPositionX();
                //double ballX = ball.getPositionX() - (lastX - currentX);
                ball.setPosition(platform.getPositionX(), ball.getPositionY());
                //lastX = currentX;
            }
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void stopped() {
        stop = true;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Element getRoom() {
        return room;
    }

    public void setRoom(Element room) {
        this.room = room;
    }

    public AbstractWall getWall() {
        return wall;
    }

    public void setWall(AbstractWall wall) {
        this.wall = wall;
    }

    public List<Element> getRoomWalls() {
        return roomWalls;
    }
}
