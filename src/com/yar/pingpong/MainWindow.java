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
        Pane root = new Pane();
        Platform platform = new Platform();
        platform.draw(root);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.show();
    }

    public void show() {
        launch();
    }
}
