package com.kotmw.invader.entity;

import javafx.scene.paint.Color;

public class Block extends Entity {

    public Block(double x, double y, double w, double h, Color color) {
        super(x, y, w, h, EntityType.Block, color);
    }
}
