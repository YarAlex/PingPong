package com.yar.pingpong;

import com.yar.pingpong.element.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;


public class MainWindow extends Application {

    private Logger log = Logger.getLogger(MainWindow.class.getSimpleName());

    private double WINDOW_WIDTH = 300;
    private double WINDOW_HEIGHT = 250;
    private GameHandler gameHandler;

    @Override
    public void start(Stage primaryStage) {
        Root root = new Root(null);
        Scene scene = new Scene(root.getShape());
        scene.setOnKeyPressed(KeyHandler.getInstance().getKeyPressed());
        scene.setOnKeyReleased(KeyHandler.getInstance().getKeyReleased());
        primaryStage.setScene(scene);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        Platform platform = new Platform(root);
        platform.draw();
        platform.setPosition((WINDOW_WIDTH - 50) / 2, WINDOW_HEIGHT - 50.0);

        Ball ball = new Ball(root);
        ball.draw();
        ball.setPosition(platform.getPositionX()+platform.getWidth()/2-ball.getWidth()/2, platform.getPositionY()-ball.getHeight()-.1);
        ball.getShape().autosize();

        RectangularWall rectangularWall = new RectangularWall(root, 10);
        rectangularWall.draw();
        rectangularWall.moveToCenter();

        gameHandler = new GameHandler();
        gameHandler.setBall(ball);
        gameHandler.setPlatform(platform);
        gameHandler.setRoom(root);
        gameHandler.setWall(rectangularWall);
        for (RoomWall.Type type : RoomWall.Type.values()) {
            RoomWall wall = new RoomWall(root, type);
            wall.draw();
            gameHandler.getRoomWalls().add(wall);
        }
        primaryStage.show();
    }

    public void show() {
        launch();
    }

    @Override
    public void stop() throws Exception {
        log.info("Game exit!");
        gameHandler.stopped();
        super.stop();
    }
}
