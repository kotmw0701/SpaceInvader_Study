package com.kotmw.invader.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Rectangle {

    private boolean dead = false;
    private EntityType entityType;

    public Entity(double x, double y, double w, double h, EntityType entityType, Color color) {
        super(w, h, color);

        this.entityType = entityType;
        setTranslateX(x);
        setTranslateY(y);
    }

    public void moveLeft() {}

    public void moveRight() {}

    public void moveUp() {}

    public void moveDown() {}

    public boolean dead() {
        if(dead) return false;
        return dead = true;
    }

    public boolean isDead() { return dead; }

    public EntityType getEntityType() { return entityType; }
}
