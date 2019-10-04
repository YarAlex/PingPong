package com.yar.pingpong;

import com.yar.pingpong.element.AbstractWall;
import com.yar.pingpong.element.Ball;
import com.yar.pingpong.element.Element;
import com.yar.pingpong.element.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
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
    List<Polygon> roomBorders = new ArrayList<>();
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
                    ball.fly(ball.getPositionX(), 0);
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

    double deltaX = 0;
    double deltaY = .9;
    double destY = 200;

    public void intersectsHandler() {
        while (!stop) {
            List<Element> barriers = new ArrayList<>();
            barriers.addAll(wall.getBricks());
            barriers.add(platform);
            for (Element barrier : barriers) {
                if (ball.getBounds().intersects(barrier.getBounds())) {
                    ball.stopFly();
                    log.debug("Intersects with ball, bounds:"+ball.getBounds());
                    log.debug("Barrier bounds: "+barrier.getBounds());

                    ball.setPosition(ball.getPositionX()+deltaX, ball.getPositionY()+deltaY);
                    ball.fly(ball.getPositionX(), destY);
                    deltaY *= -1;
                    if (destY == 0) {
                        destY = 200;
                    } else {
                        destY = 0;
                    }
                }
            }
        }
    }

    private void moveRight(boolean keyReleased) {
        if (keyReleased && platform.isMoving()) {
            log.info("TEST!");
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
            log.info("TEST!");
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
        Polygon polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                0.0, 1.0,
                300.0, 1.0,
                300.0, 0.);
        polygon_1.setFill(Color.BLACK);
        room.getShape().getChildren().add(polygon_1);
        roomBorders.add(polygon_1);

        polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 0.0,
                1.0, 0.0,
                1.0, 300.0,
                0.0, 300.0);
        polygon_1.setFill(Color.BLACK);
        room.getShape().getChildren().add(polygon_1);
        roomBorders.add(polygon_1);

        polygon_1 = new Polygon();
        polygon_1.getPoints().addAll(0.0, 215.0,
                300.0, 215.0,
                300.0, 250.0,
                0.0, 250.0);
        polygon_1.setFill(Color.BLACK);
        room.getShape().getChildren().add(polygon_1);
        roomBorders.add(polygon_1);
    }

    public AbstractWall getWall() {
        return wall;
    }

    public void setWall(AbstractWall wall) {
        this.wall = wall;
    }
}
