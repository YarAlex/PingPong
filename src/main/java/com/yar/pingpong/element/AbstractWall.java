package com.yar.pingpong.element;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWall extends AbstractElement implements Wall{

    protected List<Brick> bricks;
    protected int countBricks;

    public AbstractWall(Pane parent, int countBricks) {
        super(parent);
        this.countBricks = countBricks;
        this.bricks = new ArrayList<>(countBricks);
    }

    @Override
    public void calculateMatrix() {
        log.debug("Calculate matrix");
        matrix.clear();
        for (Brick brick : bricks) {
            brick.calculateMatrix();
            List<Coordinate> newChildCoord = recalculateChildMatrix(brick.getMatrix());
            matrix.addAll(newChildCoord);
        }
    }

    private List<Coordinate> recalculateChildMatrix(List<Coordinate> matrix) {
        log.debug("Recalculate child matrix");
        List<Coordinate> ret = new ArrayList<>();
        for (Coordinate coordinate : matrix) {
            Coordinate newCoord = new Coordinate(getPositionX()+coordinate.getX(), getPositionY()+coordinate.getY());
            log.trace("New coordinate: " + newCoord);
            ret.add(newCoord);
        }
        return ret;
    }

    public List<Brick> getBricks() {
        return bricks;
    }
}
