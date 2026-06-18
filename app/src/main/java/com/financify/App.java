package com.financify;

import com.financify.views.Sidebar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Database.init();

        BorderPane root = new BorderPane();

        root.setLeft(new Sidebar());
        root.setCenter(new Label("Dashboard"));

        Scene scene = new Scene(root, 1366, 768);

        stage.setScene(scene);
        stage.setTitle("Financify");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}