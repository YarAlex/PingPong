package com.yar.pingpong.element;

public class RectangularWall extends AbstractWall{

    public RectangularWall(Element parent, int countBricks) {
        super(parent, countBricks);
    }

    @Override
    public void drawInit() {
        double x = 0;
        double y = 0;
        for (int c = 0; c < countBricks; c++) {
            Brick brick = new Brick(this);
            brick.draw();
            x = c*50.0;
            if (c%2 == 0) {
                y = 0;
            } else {
                y = 50.0;
            }
            brick.setPosition(x, y);
            brick.setIndex(c);
            bricks.add(brick);
        }
    }

    public void moveToCenter() {
        setPosition((shape.getScene().getWindow().getWidth())/2-getWidth()/2, 5.0);
    }
}
