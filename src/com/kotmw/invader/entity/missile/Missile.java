package com.kotmw.invader.entity.missile;

import com.kotmw.invader.entity.Entity;
import com.kotmw.invader.entity.EntityType;
import javafx.scene.paint.Color;

public abstract class Missile extends Entity {

    public Missile(double x, double y, double w, double h, EntityType entityType, Color color) {
        super(x, y, w, h, entityType, color);
    }
}
