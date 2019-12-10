package com.kotmw.invader.entity;

import com.kotmw.invader.entity.missile.InvaderMissile;
import com.kotmw.invader.entity.missile.Missile;
import com.kotmw.invader.entity.missile.Shooter;
import javafx.scene.paint.Color;

public class Invader extends Enemy implements Shooter {

    private boolean active = false;
    private Invader abobeInvader;


    public Invader(double x, double y, double w, double h, Color color) {
        this(x, y, w, h, color, null, false);
    }

    public Invader(double x, double y, double w, double h, Color color, Invader abobeInvader) {
        this(x, y, w, h, color, abobeInvader, false);
    }

    public Invader(double x, double y, double w, double h, Color color, Invader abobeInvader, boolean active) {
        super(x, y, w, h, EntityType.Invader, color);
        this.abobeInvader = abobeInvader;
        this.active = active;
    }

    @Override
    public void moveRight() {
        setX( getX() + 5 );
    }

    @Override
    public void moveLeft() {
        setX( getX() - 5 );
    }

    @Override
    public void moveDown() {
        setY( getY() + 20 );
    }

    @Override
    public Missile shoot() {
        return new InvaderMissile(getX() + 12, getY() + 20, 5, 20, Color.WHITE);
    }

    @Override
    public boolean turnDead() {
        if (abobeInvader != null && active) abobeInvader.changeActive();
        return super.turnDead();
    }

    public boolean isActive() { return active; }

    private void changeActive() {
        if (isDead()) {
            if (abobeInvader != null) abobeInvader.changeActive();
        } else active = true;
    }
}
/*
メモ :
    8 : 12  x2
    8 : 11  x2
    8 : 8   x1

    O O O O A : 初期状態
    O A X X X : 先頭から順番にやられてる状態
    O A X O A : 途中がやられるとその後ろのがアクティブになってしまう
    O O X O A : こうなって
    O A X X X : こうなるようにしたい
 */
