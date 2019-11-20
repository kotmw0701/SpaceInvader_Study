package com.kotmw.invader.entity;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

public abstract class Entity {

    private int id;
    private boolean dead;
    private Point2D point2D;
    private BoundingBox boundingBox;

    public int getId() {
        return id;
    }

    public boolean isDead() {
        return dead;
    }

    public Point2D getPoint2D() {
        return point2D;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
