package com.yar.pingpong;

import com.yar.pingpong.element.*;
import javafx.scene.input.KeyEvent;
import org.apache.log4j.Logger;

public class GameHandler {

    private Logger log = Logger.getLogger(getClass().getSimpleName());

    private Platform platform;
    private Ball ball;
    private Element room;
    private AbstractWall wall;
    boolean stop = false;


    public GameHandler() {
        KeyHandler.getInstance().addKeyHandlerEvent(event -> processKey(event));
    }

    private void processKey(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            return;
        }
        switch (event.getCode()) {
            case SPACE:
                ball.fly(ball.getPositionX(), 0);
                new Thread(() -> process()).start();
                break;
        }
    }

    public void process() {
        while (!stop) {
            for (Brick brick : wall.getBricks()) {=
                if (ball.getBounds().intersects(brick.getBounds())) {
                    ball.stopFly();
                    stopped();
                    log.debug("Intersects with ball, bounds:"+ball.getBounds());
                    log.debug("Wall bounds: "+brick.getBounds());
                }
            }
        }
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
}
