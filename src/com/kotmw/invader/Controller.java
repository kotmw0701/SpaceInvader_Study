package com.kotmw.invader;

import com.kotmw.invader.entity.*;
import com.kotmw.invader.entity.missile.CannonMissile;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private Pane root, container;

    @FXML
    private VBox debugMonitor;

    @FXML
    private Label frameBox, ptimeBox, livesBox, locationBox;

    private Cannon player = new Cannon(580, 500, 40, 40, Color.GREEN);

    private boolean right, left;
    private double centerX, centerY;

    private long startTime;
    private int secondSeparator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        container.getChildren().add(player);
        centerX = container.getPrefWidth()/2;
        centerY = container.getHeight()/2;
        debugMonitor.setOpacity(0);

        createLevel(1);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };

        startTime = System.nanoTime();
        timer.start();
    }

    private List<Entity> entities() {
        return container.getChildren().stream()
                .filter(e -> e instanceof Entity)
                .map(e -> (Entity)e).collect(Collectors.toList());
    }


    private void update(long now) {
        double second = (now - startTime) / 1_000_000_000.0;
        setDebugMonitor(second);
        if (((int) second) != secondSeparator) { //1秒ごとの処理
            secondSeparator = (int) second;
            if (secondSeparator%25 == 0)
                container.getChildren().add(new UFO(container.getPrefWidth(), 10, 40, 20, Color.PURPLE));
        }

        if (right && player.getTranslateX() + player.getWidth() < container.getPrefWidth()) player.moveRight();
        if (left && player.getTranslateX() > 0) player.moveLeft();

        entities().forEach(entity -> {
            switch (entity.getEntityType()) {
                case Invader:
                    break;
                case UFO:
                    ((UFO)entity).move();
                    if (!isObjectInWindow(entity)) {
                        entity.dead();
                        break;
                    }
                    break;
                case CannonMissile:
                    entity.moveUp();
                    if (!isObjectInWindow(entity)) {
                        entity.dead();
                        break;
                    }
                    entities().stream().filter(e -> e instanceof Enemy).forEach(others -> {
                        if (entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                            others.dead();
                            entity.dead();
                        }
                    });
                    break;
                case InvaderMissile:
                    entity.moveDown();
                    if (!isObjectInWindow(entity)) {
                        entity.dead();
                        break;
                    }
                    if (entity.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead();
                        entity.dead();
                    }
                    break;
                default:
                    break;
            }
        });

        container.getChildren().removeIf(e -> (e instanceof Entity) && ((Entity) e).isDead());
    }

    /**
     * Entityがcontainerの範囲に居るかをチェック
     *
     * @param entity 確認したい対象のEntity
     * @return 含まれるならtrue、含まれないならfalse
     */
    private boolean isObjectInWindow(Entity entity) {
        double maxX = container.getPrefWidth(), maxY = container.getPrefHeight();

        return entity.getTranslateX() > 0
                && entity.getTranslateY() > 0
                && entity.getTranslateX() < maxX
                && entity.getTranslateY() < maxY;
    }

    private void createLevel(int level) {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 11; x++) {
                Invader invader;
                int yPoint = y*40+50*level;
                if (y == 0) {       //最上段
                    invader = new Invader((centerX*0.58)+x*50, yPoint, 30, 20, Color.CYAN);
                } else if (y < 3) { //中段2段
                    invader = new Invader((centerX*0.58)+x*50, yPoint, 30, 20, Color.GREEN);
                } else {            //下段2段
                    invader = new Invader((centerX*0.58)+x*50, yPoint, 30, 20, Color.YELLOW);
                }
                container.getChildren().add(invader);
            }
        }
    }

    private void setDebugMonitor(double secound) {
        ptimeBox.setText(String.format("%.1f", secound));
        livesBox.setText(String.valueOf(entities().size()));
        locationBox.setText(String.format("%.0f/%.0f", player.getTranslateX(), player.getTranslateY()));
    }

    @FXML
    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case RIGHT:
                right = true;
                break;
            case LEFT:
                left = true;
                break;
            case SPACE:
                if(container.getChildren().stream().anyMatch(e -> e instanceof CannonMissile)) return;
                container.getChildren().add(player.shoot());
                break;
            case F3:
                debugMonitor.setOpacity(debugMonitor.getOpacity() == 1 ? 0 : 1);
        }
    }

    @FXML
    private void onKeyReleased(KeyEvent keyEvent) {
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
