package com.kotmw.invader.entity.missile;

import com.kotmw.invader.entity.EntityType;
import javafx.scene.paint.Color;

public class InvaderMissile extends Missile {

    public InvaderMissile(double x, double y, double w, double h, Color color) {
        super(x, y, w, h, EntityType.InvaderMissile, color);
    }

    @Override
    public void moveDown() {
        setY( getY() + 5);
    }
}
