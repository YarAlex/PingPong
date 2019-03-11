package com.yar.pingpong;

import com.yar.pingpong.element.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private double WINDOW_WIDTH = 300;
    private double WINDOW_HEIGHT = 250;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        KeyHandler keyHandler = new KeyHandler();
        scene.setOnKeyPressed(keyHandler.getKeyPressed());
        scene.setOnKeyReleased(keyHandler.getKeyReleased());

        Platform platform = new Platform();
        keyHandler.setPlatform(platform);
        platform.draw(root);
    }

    public void show() {
        launch();
    }
}
