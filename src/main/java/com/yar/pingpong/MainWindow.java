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

        scene.setOnKeyPressed(KeyHandler.getInstance().getKeyPressed());
        scene.setOnKeyReleased(KeyHandler.getInstance().getKeyReleased());

        Platform platform = new Platform(root);
        platform.draw();
    }

    public void show() {
        launch();
    }
}
