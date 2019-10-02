package com.yar.pingpong.element;

public interface Element {

    public int getPositionX();

    public void setPositionX(int x);

    public int getPositionY();

    public void setPositionY(int y);

    public void show();

    public void hide();

    public void setPosition(double x, double y);

    public void draw();

    public double getWidth();

    public double getHeight();

    /*public Drawing getShape();

    public void setShape(Drawing shape);

    public void setParent(element parent);

    public element getParent();*/
}
