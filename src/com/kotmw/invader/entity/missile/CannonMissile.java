package com.kotmw.invader.entity.missile;

import com.kotmw.invader.entity.EntityType;
import javafx.scene.paint.Color;

public class CannonMissile extends Missile {

    public CannonMissile(double x, double y, double w, double h, Color color) {
        super(x, y, w, h, EntityType.CannonMissile, color);
    }

    @Override
    public void moveUp() {
        setTranslateY( getTranslateY() - 15);
    }
}
