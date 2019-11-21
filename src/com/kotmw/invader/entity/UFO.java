package com.kotmw.invader.entity;

import javafx.scene.paint.Color;

public class UFO extends Enemy {

    public UFO(double x, double y, double w, double h, Color color) {
        super(x, y, w, h, EntityType.UFO, color);
    }

    @Override
    public void moveRight() {
        setTranslateX( getTranslateX() - 2);
    }


}
