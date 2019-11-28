package com.kotmw.invader.entity;

import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tochica extends TilePane {

    private Rectangle[][] blocks;

    public Tochica(double x, double y, double w, double h) {
        this.setPrefWidth(w);
        this.setPrefHeight(h);
        int xCount = (int) (w/5), yCount = (int) (h/5);
        blocks = new Rectangle[xCount][yCount];
        for (int yBlock = 0; yBlock < yCount; yBlock++ ) {
            for (int xBlock = 0; xBlock < xCount; xBlock++ ) {
                Rectangle block = new Rectangle(5, 5, Color.RED);
                int finalXBlock = xBlock, finalYBlock = yBlock;
                block.setOnMouseClicked(e -> System.out.println(finalXBlock + ":" + finalYBlock));
                blocks[xBlock][yBlock] = block;
                this.getChildren().add(block);
            }
        }

        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void hitTochica(Entity entity) {

    }

}
