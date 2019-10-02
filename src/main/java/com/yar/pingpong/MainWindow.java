package com.yar.pingpong;

import com.yar.pingpong.element.Ball;
import com.yar.pingpong.element.Platform;
import com.yar.pingpong.element.RectangularWall;
import com.yar.pingpong.element.Root;
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
        ball.setPosition(platform.getPositionX()+platform.getWidth()/2, platform.getPositionY()-ball.getHeight()/2);

        RectangularWall rectangularWall = new RectangularWall(root, 1);
        rectangularWall.draw();
        rectangularWall.moveToCenter();

        gameHandler = new GameHandler();
        gameHandler.setBall(ball);
        gameHandler.setPlatform(platform);
        gameHandler.setRoom(root);
        gameHandler.setWall(rectangularWall);

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
