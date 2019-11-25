package com.kotmw.invader.entity;

import com.kotmw.invader.entity.missile.CannonMissile;
import com.kotmw.invader.entity.missile.Missile;
import com.kotmw.invader.entity.missile.Shooter;
import javafx.scene.paint.Color;

public class Cannon extends Entity implements Shooter {

    public Cannon(double x, double y, double w, double h, Color color) {
        super(x, y, w, h, EntityType.Cannon, color);
    }

    @Override
    public void moveRight() {
        setTranslateX( getTranslateX() + 5);
    }

    @Override
    public void moveLeft() {
        setTranslateX( getTranslateX() - 5);
    }

    @Override
    public Missile shoot() {
        return new CannonMissile(getTranslateX() + 20, getTranslateY() - 10, 5, 20, Color.WHITE);
    }
}
