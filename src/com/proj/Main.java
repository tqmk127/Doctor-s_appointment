package com.proj;

import com.proj.UI.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new NotationUI(), 1000.0D, 800.0D));
        stage.setTitle("Лабораторна робота №10. БД Довідник лікаря");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
