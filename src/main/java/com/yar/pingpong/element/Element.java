package com.yar.pingpong.element;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;

public interface Element {

    void draw();

    void addChild(Element child);

    int getPositionX();

    void setPositionX(int x);

    int getPositionY();

    void setPositionY(int y);

    void setPosition(double x, double y);

    double getWidth();

    double getHeight();

    Pane getShape();

    void show();

    void hide();

    Bounds getBounds();
}
