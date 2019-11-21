package com.kotmw.invader.entity;

import com.kotmw.invader.entity.missile.InvaderMissile;
import com.kotmw.invader.entity.missile.Missile;
import com.kotmw.invader.entity.missile.Shooter;
import javafx.scene.paint.Color;

public class Invader extends Entity implements Shooter {

    public Invader(double x, double y, double w, double h, Color color) {
        super(x, y, w, h, EntityType.Invader, color);
    }

    @Override
    public void moveRight() {
        setTranslateX( getTranslateX() + 2 );//敵の残り数、下に行った回数に寄って可変
    }

    @Override
    public void moveLeft() {
        setTranslateX( getTranslateX() - 2 );//敵の残り数、下に行った回数によって可変
    }

    @Override
    public void moveDown() {
        setTranslateY( getTranslateY() + 2 );
    }

    @Override
    public Missile shoot() {
        return new InvaderMissile(getTranslateX(), getTranslateY() + 20, 5, 20, Color.WHITE);
    }
}
