package com.financify;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.financify.views.LoginView;
import com.financify.views.SignUpView;
 
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Database.init();

        String version = System.getProperty("app.version", "dev");
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1366, 768);

        Boolean userExists = Database.lookForUser();
        if (userExists) {
            root.setCenter(new LoginView(root, version));
        } else {
            root.setCenter(new SignUpView(root, version));
        }

        stage.setScene(scene);
        stage.setTitle("Financify");
        stage.getIcons().add(
            new Image(getClass().getResourceAsStream("/images/icon.png"))
        );
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}