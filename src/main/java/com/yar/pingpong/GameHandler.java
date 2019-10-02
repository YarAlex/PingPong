package com.yar.pingpong;

import com.yar.pingpong.element.AbstractWall;
import com.yar.pingpong.element.Ball;
import com.yar.pingpong.element.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

import java.util.Collections;

public class GameHandler {

    private Logger log = Logger.getLogger(getClass().getSimpleName());

    private Platform platform;
    private Ball ball;
    private Pane room;
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
            ball.calculateMatrix();
            wall.calculateMatrix();
            log.debug("Wall matrix size = "+wall.getMatrix().size());
            if (!Collections.disjoint(wall.getMatrix(), ball.getMatrix())) {
                log.info("Cross, ball coordinate :" +ball.getMatrix());
                log.info("Cross, wall coordinate :" +wall.getMatrix());
                ball.stopFly();
                stopped();
            }
            //stopped();
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

    public Pane getRoom() {
        return room;
    }

    public void setRoom(Pane room) {
        this.room = room;
    }

    public AbstractWall getWall() {
        return wall;
    }

    public void setWall(AbstractWall wall) {
        this.wall = wall;
    }
}
