package com.yar.pingpong.element;

public interface Element {

    public Double getPositionX();

    public void setPositionX(Double x);

    public Double getPositionY();

    public void setPositionY(Double y);

    public void show();

    public void hide();

    public void setPosition(Double x, Double y);

    public void draw(Object parent);

    /*public Drawing getShape();

    public void setShape(Drawing shape);

    public void setParent(element parent);

    public element getParent();*/
}
