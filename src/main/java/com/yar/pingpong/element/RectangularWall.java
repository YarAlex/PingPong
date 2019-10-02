package com.yar.pingpong.element;

import javafx.scene.layout.Pane;

public class RectangularWall extends AbstractWall{

    public RectangularWall(Pane parent, int countBricks) {
        super(parent, countBricks);
    }

    @Override
    public void draw() {
        log.info("Draw wall");
        for (int c = 0; c < countBricks; c++) {
            Brick brick = new Brick(shape);
            brick.draw();
            brick.setPosition(c*25.0, 0.0);
            bricks.add(brick);
        }
        shape.autosize();
        moveToCenter();
    }

    private void moveToCenter() {
        setPosition((shape.getScene().getWindow().getWidth())/2-getWidth()/2, 5.0);
    }
}
