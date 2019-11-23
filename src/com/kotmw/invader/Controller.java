package com.kotmw.invader;

import com.kotmw.invader.entity.Cannon;
import com.kotmw.invader.entity.Entity;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    public Pane root;
    
    @FXML
    public Pane container;

    private Cannon player = new Cannon(560, 450, 40, 40, Color.GREEN);

    private boolean right, left;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        container.getChildren().add(player);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();
    }

    private List<Entity> entities() {
        return container.getChildren().stream()
                .filter(e -> e instanceof Entity)
                .map(e -> (Entity)e).collect(Collectors.toList());
    }

    private void update() {

        if (right) player.moveRight();
        if (left) player.moveLeft();

        entities().forEach(e -> {
            switch (e.getEntityType()) {
                case Cannon:
                    break;
                case Invader:
                    break;
                case UFO:
                    break;
                case CannonMissile:
                    e.moveUp();
                    if (isObjectInWindow(e)) e.dead();
                    break;
                case InvaderMissile:
                    e.moveDown();
                    if (isObjectInWindow(e)) e.dead();
                    break;
            }
        });

        container.getChildren().removeIf(e -> (e instanceof Entity) && ((Entity) e).isDead());
    }

    private boolean isObjectInWindow(Entity entity) {
        double maxX = container.getPrefWidth(), maxY = container.getPrefHeight();

        return entity.getTranslateX() < 0
                || entity.getTranslateY() < 0
                || entity.getTranslateX() > maxX
                || entity.getTranslateY() > maxY;
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case RIGHT:
                right = true;
                break;
            case LEFT:
                left = true;
                break;
            case SPACE:
                container.getChildren().add(player.shoot());
                break;
        }
    }

    @FXML
    public void onKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case RIGHT:
                right = false;
                break;
            case LEFT:
                left = false;
                break;
        }
    }
}
