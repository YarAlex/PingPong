package com.yar.pingpong.element;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWall extends AbstractElement implements Wall{

    protected List<Brick> bricks;
    protected int countBricks;

    public AbstractWall(Element parent, int countBricks) {
        super(parent);
        this.bricks = new ArrayList<>(countBricks);
        this.countBricks = countBricks;
    }

    public List<Brick> getBricks() {
        return bricks;
    }
}
