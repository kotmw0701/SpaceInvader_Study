/*
  @Author kotmw0701
 */
package com.kotmw.invader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Main.fxml")));//スクリーンのデータを読み込む
        scene.getRoot().requestFocus();//キー入力を受け付けるようにする
        stage.setTitle("Invader");//タイトルを設定する
        stage.setResizable(false);//サイズ変更を不可能にする
        stage.setScene(scene);//読み込んだデータを設定する
        stage.sizeToScene();//サイズ変更を不可能にしたときに左右に生ずる10pxのバグの余白を削除する
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
