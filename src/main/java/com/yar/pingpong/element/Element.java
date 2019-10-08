package com.yar.pingpong.element;

import javafx.scene.layout.Pane;

public interface Element {

    void draw();

    void addChild(Element child);

    Pane getShape();

    double getPositionX();

    double getPositionY();

    void setPosition(Double x, Double y);

    double getWidth();

    double getHeight();

    void show();

    void hide();
}
