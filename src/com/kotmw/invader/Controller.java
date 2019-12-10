package com.kotmw.invader;

import com.kotmw.invader.entity.*;
import com.kotmw.invader.entity.missile.CannonMissile;
import com.kotmw.invader.entity.missile.InvaderMissile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.File;
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
    private Label javaBox, frameBox, ptimeBox, livesBox, locationBox, invadersBox;

    private Cannon player = new Cannon(580, 500, 35, 20, Color.CYAN);
    private Invader[][] invaders = new Invader[11][5];

    private boolean right, left, invaderRight, down;
    private double centerX, centerY;

    private long startTime, previous;
    private int frameCount;

    private Line leftLine, rightLine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        javaBox.setText(System.getProperty("java.version"));
        container.getChildren().add(player);
        centerX = container.getPrefWidth()/2;
        centerY = container.getHeight()/2;
        debugMonitor.setOpacity(0);

        leftLine = new Line(0, 0, 0, 600);
        leftLine.setStroke(Color.LIGHTGREEN);

        rightLine = new Line(0, 0, 0, 600);
        rightLine.setStroke(Color.PINK);

        Line sampleLeft = new Line(250, 0, 250, 600), sampleRight = new Line(950, 0, 950, 600);
        sampleLeft.setStroke(Color.RED);
        sampleRight.setStroke(Color.RED);

        container.getChildren().addAll(leftLine, rightLine, sampleLeft, sampleRight);

        createLevel(1);

        Timeline timeline = new Timeline(
                new KeyFrame(
                        new Duration(16),
                        event -> update(0)
                )
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * 存在するEntityを全部返す
     * @return Entityのリスト
     */
    private List<Entity> entities() {
        return container.getChildren().stream()
                .filter(e -> e instanceof Entity)
                .map(e -> (Entity)e).collect(Collectors.toList());
    }

    /**
     * フレームごとの処理
     * @param now 今の時間(ナノ秒)
     */
    private void update(long now) {
        frameCount++;
//        double second = (now - startTime) / 1_000_000_000.0;
        setDebugMonitor(frameCount/60);
        if (frameCount%(60*25) == 0) container.getChildren().add(new UFO(container.getPrefWidth(), 10, 50, 20, Color.PURPLE));

        if (right && player.getX() + player.getWidth() < container.getPrefWidth()) player.moveRight();
        if (left && player.getX() > 0) player.moveLeft();
        entities().forEach(entity -> {
            switch (entity.getEntityType()) {
                case Invader:
//                    if (frameCount%120 == 0 && Math.random() < 0.1) {
//                        Invader invader = (Invader) entity;
//                        if (invader.isActive()) container.getChildren().add(invader.shoot());
//                    }
                    if (frameCount%30 == 0) {
                        if (down) entity.moveDown();
                        else {
                            if (invaderRight) entity.moveRight();
                            else entity.moveLeft();
                        }
                    }
                    break;
                case UFO:
                    ((UFO)entity).move();
                    if (!isObjectInWindow(entity)) {
                        entity.turnDead();
                        break;
                    }
                    break;
                case CannonMissile:
                    entity.moveUp();
                    if (!isObjectInWindow(entity)) {
                        entity.turnDead();
                        break;
                    }
                    entities().stream().filter(e -> e instanceof Enemy || e instanceof InvaderMissile).forEach(others -> {
                        if (entity.getBoundsInParent().intersects(others.getBoundsInParent())) {
                            others.turnDead();
                            entity.turnDead();
                        }
                    });
                    break;
                case InvaderMissile:
                    entity.moveDown();
                    if (!isObjectInWindow(entity)) {
                        entity.turnDead();
                        break;
                    }
                    if (entity.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.turnDead();
                        entity.turnDead();
                    }
                    break;
                default:
                    break;
            }
            container.getChildren().stream().filter(e -> e instanceof Tochica).forEach(tochica -> {
                if (entity.getBoundsInParent().intersects(tochica.getBoundsInParent())
                        && ((Tochica) tochica).hitTochica(entity)) entity.turnDead();
            });
        });

        /*
        一番外側に居るインベーダーを左右それぞれ取得
        もし外側に居るインベーダーのどちらかが規定のX座標より外側に居る場合は次の更新時に折り返しを実行するように
         */
        if (frameCount%30 == 0) {
            double rightMost = 250, leftMost = 950;
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 11; x++) {
                    Invader invader = invaders[x][y];
                    if (invader.isDead()) continue;
                    double invaderX = invader.getX();
                    rightMost = Math.max(rightMost, invaderX);
                    leftMost = Math.min(leftMost, invaderX);
                }
            }
            leftLine.setTranslateX(leftMost);
            rightLine.setTranslateX(rightMost);

            if (!down) {
                if (down = (250 > leftMost || 950 < rightMost)) invaderRight = !invaderRight;
            } else down = false;

        }

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

        return entity.getX() > 0
                && entity.getY() > 0
                && entity.getX() < maxX
                && entity.getY() < maxY;
    }


    private void createLevel(int level) {
//        Tochica tochica = new Tochica(325, 400, 100, 60);
//        Tochica tochica2 = new Tochica(475, 400, 100, 60);
//        Tochica tochica3 = new Tochica(625, 400, 100, 60);
//        Tochica tochica4 = new Tochica(775, 400, 100, 60);
//        container.getChildren().addAll(tochica, tochica2, tochica3, tochica4);
        for (int x = 0; x < 11; x++) {
            Invader abobeInvader = null;
            for (int y = 0; y < 5; y++) {
                Invader invader;
                int yPoint = y*40+50*level;
                if (y == 0) {       //最上段
                    invader = new Invader((centerX*0.63)+x*40, yPoint, 30, 20, Color.CYAN);
                } else if (y < 3) { //中段2段
                    invader = new Invader((centerX*0.63)+x*40, yPoint, 30, 20, Color.GREEN, abobeInvader);
                } else {            //下段2段
                    invader = new Invader((centerX*0.63)+x*40, yPoint, 30, 20, Color.YELLOW, abobeInvader);
                    if (y == 4) invader = new Invader((centerX*0.63)+x*40, yPoint, 30, 20, Color.RED, abobeInvader, true);
                }
                abobeInvader = invader;
                invaders[x][y] = invader;
                container.getChildren().add(invader);
            }
        }
    }

    private void setDebugMonitor(double second) {
        ptimeBox.setText(String.format("%.1f", second));
        livesBox.setText(String.valueOf(entities().size()));
        locationBox.setText(String.format("%.0f/%.0f", player.getX(), player.getY()));
        invadersBox.setText(getInvaderStatus());
    }

    private String getInvaderStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 11; x++)
                stringBuilder.append(invaders[x][y].isDead() ? "X" : invaders[x][y].isActive() ? "A" : "O");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private void bomb() {
        File file = new File("C:\\Users\\G018C1131\\Videos\\nc142777.mp4");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
        mediaPlayer.setOnEndOfMedia(() -> root.getChildren().removeIf(e -> e instanceof MediaView));
        mediaPlayer.play();
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
                if (container.getChildren().stream().anyMatch(e -> e instanceof CannonMissile)) return;
                container.getChildren().add(player.shoot());
                break;
            case F3:
                debugMonitor.setOpacity(debugMonitor.getOpacity() == 1 ? 0 : 1);
                break;
            default:
                break;
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
            default:
                break;
        }
    }
}
