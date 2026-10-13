
package com.financify.views;

import com.financify.Database;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;

public class LoginView extends VBox {
    private final BorderPane root;
    private final String version;

    public LoginView(BorderPane root, String version) {
        this.root = root;
        this.version = version;

        VBox content = new VBox(12);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(380);

        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #782170;
        """;

        String words_styles = """
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-text-fill: #0B3040;
        """;

        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-padding: 0 12;
            -fx-font-size: 14px;
        """;

        Label title = new Label("Login to Financify");
        title.setStyle(title_styles);

        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle(words_styles);
        usernameLabel.setMaxWidth(Double.MAX_VALUE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle(table_style);

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle(words_styles);
        passwordLabel.setMaxWidth(Double.MAX_VALUE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle(table_style);

        Button loginButton = new Button("Login");
        loginButton.setPrefHeight(40);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("""
            -fx-background-color: #782170;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-cursor: hand;
        """);

        loginButton.setOnMouseEntered(e ->
            loginButton.setStyle("""
                -fx-background-color: #65205F;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 8;
                -fx-cursor: hand;
            """)
        );

        loginButton.setOnMouseExited(e ->
            loginButton.setStyle("""
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

        loginButton.setOnAction(e -> {
            try {
                boolean isLogged = Database.logUser(usernameField.getText(), passwordField.getText());
                System.out.println(isLogged);
                if (isLogged) {
                    root.setLeft(new Sidebar(root, version));
                    root.setCenter(new DashboardView());
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
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            errorLabel,
            loginButton
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