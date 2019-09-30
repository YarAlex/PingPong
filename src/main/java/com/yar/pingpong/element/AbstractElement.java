package com.yar.pingpong.element;

import javafx.scene.layout.Pane;

public abstract class AbstractElement implements Element {

    protected Pane shape = new Pane();

    @Override
    public Double getPositionX() {
        return shape.getLayoutX();
    }

    @Override
    public void setPositionX(Double x) {
        shape.setLayoutX(x);
    }

    @Override
    public Double getPositionY() {
        return shape.getLayoutY();
    }

    @Override
    public void setPositionY(Double y) {
        shape.setLayoutY(y);
    }

    @Override
    public void show() {
        shape.setVisible(true);
    }

    @Override
    public void hide() {
        shape.setVisible(false);
    }

    @Override
    public void setPosition(Double x, Double y) {
        if (x != null) {
            shape.setTranslateX(x);
        }
        if (y != null) {
            shape.setTranslateY(y);
        }
    }
}
