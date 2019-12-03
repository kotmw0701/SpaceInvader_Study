package com.kotmw.invader.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity extends Rectangle {

    private boolean dead = false;
    private EntityType entityType;

    /**
     * Entityを生成する
     * @param x 初期x座標
     * @param y 初期y座標
     * @param w 横幅
     * @param h 高さ
     * @param entityType Entityのタイプ
     * @param color 色(仮)
     */
    public Entity(double x, double y, double w, double h, EntityType entityType, Color color) {
        super(w, h, color);

        this.entityType = entityType;

        this.setX(x);
        this.setY(y);
    }

    /**
     * 対象のEntityを左に移動する<br>HINT : X - 移動量(px)
     */
    public void moveLeft() {}

    /**
     * 対象のEntityを右に移動する<br>HINT : X + 移動量(px)
     */
    public void moveRight() {}

    /**
     * 対象のEntityを上に移動する<br>HINT : Y - 移動量(px)
     */
    public void moveUp() {}

    /**
     * 対象のEntityを下に移動する<br>HINT : Y + 移動量(px)
     */
    public void moveDown() {}

    /**
     * 対象のEntityを死亡状態に変更する
     * @return 既に死亡状態だったらfalse、死亡状態になったらtrue
     */
    public boolean dead() {
        if(dead) return false;
        return dead = true;
    }

    /**
     * 死亡状態かどうか
     * @return 死亡状態かどうか
     */
    public boolean isDead() { return dead; }

    /**
     * Entityがどのタイプかを取得
     * @return 対象のEntityType
     */
    public EntityType getEntityType() { return entityType; }

    @Override
    public String toString() {
        return String.valueOf(entityType);
    }
}
