package com.financify.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

import com.financify.Database;

public class SignUpView extends VBox {
    private final BorderPane root;
    private final String version;
    
    public SignUpView(BorderPane root, String version) {
        this.root = root;
        this.version = version;

        VBox content = new VBox(12);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(450);

        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #782170;
        """;
        String words_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #0B3040;
        """;
        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """;
        String btn_styles = """
            -fx-background-color: #156082;
            -fx-text-fill: #ffffff;
            -fx-font-size: 12px;
            -fx-padding: 4 8;
            -fx-background-radius: 4;
        """;

        Label title = new Label("Create your account to start");
        title.setStyle(title_styles);

        Label username = new Label("Username");
        username.setStyle(words_styles);
        username.setMaxWidth(Double.MAX_VALUE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your Ussername");
        usernameField.setPrefHeight(40);
        usernameField.setStyle(table_style);

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle(words_styles);
        passwordLabel.setMaxWidth(Double.MAX_VALUE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle(table_style);

        Button signUpBtn = new Button("Sign up");
        signUpBtn.setPrefHeight(40);
        signUpBtn.setMaxWidth(Double.MAX_VALUE);
        signUpBtn.setStyle("""
            -fx-background-color: #782170;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-cursor: hand;
        """);

        signUpBtn.setOnMouseEntered(e ->
            signUpBtn.setStyle("""
                -fx-background-color: #65205F;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-cursor: hand;
            """)
        );

        signUpBtn.setOnMouseExited(e ->
            signUpBtn.setStyle("""
                -fx-background-color: #782170;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-cursor: hand;
            """)
        );

        Label errorLabel = new Label();
        errorLabel.setStyle("""
            -fx-text-fill: #D70652;
            -fx-font-size: 12px;
        """);
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        
        signUpBtn.setOnAction(e -> {
            try {
                boolean isCreated = Database.createUser(usernameField.getText(), passwordField.getText());
                
                if (isCreated) {
                    GridPane tokenSave = new GridPane();
                    tokenSave.setHgap(10);
                    tokenSave.setVgap(10);
                    tokenSave.setPadding(new Insets(20));

                    String token = Database.saveToken();
                    Label tokenTitle = new Label("Save this token to retreive your password in case you lose it.");
                    TextField tokenToSave = new TextField(token);
                    tokenToSave.setEditable(false);
                    tokenToSave.setFocusTraversable(true);
                    Button nextBtn = new Button("Close and save");
                    nextBtn.setStyle(btn_styles);
                    nextBtn.setAlignment(Pos.CENTER);

                    VBox saveData = new VBox(10);
                    saveData.setAlignment(Pos.CENTER);
                    saveData.setSpacing(10);
                    saveData.getChildren().addAll(tokenTitle, tokenToSave, nextBtn);
                    
                    tokenSave.add(saveData, 1, 0);

                    Stage stage = new Stage();
                    stage.setTitle("Token for password reset");
                    stage.setScene(new Scene(tokenSave, 400, 200));
                    stage.show();

                    nextBtn.setOnAction(f -> {
                        root.setLeft(new Sidebar(root, version));
                        root.setCenter(new DashboardView());
                        stage.close();
                    });

                } else {
                    errorLabel.setText("Account creation failed.");
                    errorLabel.setVisible(true);
                }
            } catch (Exception f) {
                errorLabel.setText("Could not create account. Check your details.");
                errorLabel.setVisible(true);
            }
        });

        content.getChildren().addAll(
            title,
            username,
            usernameField,
            passwordLabel,
            passwordField,
            errorLabel,
            signUpBtn
        );

        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: white;");

        StackPane wrapper = new StackPane(content);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setStyle("-fx-background-color: white;");

        ScrollPane scrollPane = new ScrollPane(wrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: white; -fx-background-color: white;");

        getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }
}