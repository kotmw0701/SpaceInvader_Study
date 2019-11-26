package com.kotmw.invader.entity;

import javafx.scene.paint.Color;

public class Enemy extends Entity {

    private int score;

    public Enemy(double x, double y, double w, double h, EntityType entityType, Color color) {
        super(x, y, w, h, entityType, color);
    }
}
