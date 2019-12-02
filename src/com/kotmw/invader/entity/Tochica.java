package com.kotmw.invader.entity;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Tochica extends Pane {

    private Block[][] blocks;

    public Tochica(double x, double y, double w, double h) {
        this.setPrefWidth(w);
        this.setPrefHeight(h);
        int xCount = (int) (w/5), yCount = (int) (h/5);
        blocks = new Block[xCount][yCount];
        for (int yBlock = 0; yBlock < yCount; yBlock++ ) {
            for (int xBlock = 0; xBlock < xCount; xBlock++ ) {
                Block block = new Block(xBlock*5, yBlock*5, 5, 5, Color.RED);
                blocks[xBlock][yBlock] = block;
                this.getChildren().add(block);
            }
        }

        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public boolean hitTochica(Entity entity) {
        getChildren().forEach(block -> {
            if (entity.getBoundsInParent().intersects(block.getBoundsInParent())) {
                ((Block)block).setFill(Color.GREEN);
            }
        });
        return true;
    }

}
