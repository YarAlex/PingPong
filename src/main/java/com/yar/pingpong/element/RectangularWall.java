package com.yar.pingpong.element;

public class RectangularWall extends AbstractWall{

    public RectangularWall(Element parent, int countBricks) {
        super(parent, countBricks);
    }

    /*@Override
    public void drawInit() {
        for (int c = 0; c < countBricks; c++) {
            Brick brick = new Brick(this);
            brick.draw();
            brick.setPosition(c*25.0, 0.0);
            brick.setIndex(c);
            bricks.add(brick);
        }
    }

    public void moveToCenter() {
        setPosition((shape.getScene().getWindow().getWidth())/2-getWidth()/2, 5.0);
    }*/

    @Override
    public void drawInit() {
        for (int c = 0; c < countBricks; c++) {
            Brick brick = new Brick(this);
            brick.draw();
            brick.setPosition(00.0, c*25.0);
            brick.setIndex(c);
            bricks.add(brick);
        }
    }

    public void moveToCenter() {
        setPosition(280., 50.0);
    }
}
