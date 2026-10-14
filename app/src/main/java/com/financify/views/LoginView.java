
package com.financify.views;

import com.financify.Database;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
        String btn_styles = """
            -fx-background-color: #156082;
            -fx-text-fill: #ffffff;
            -fx-font-size: 12px;
            -fx-padding: 4 8;
            -fx-background-radius: 4;
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

        Button resetPassword = new Button("Forgot password?");
        resetPassword.setMaxWidth(Double.MAX_VALUE);
        resetPassword.setStyle("""
            -fx-background-color: transparent;
            -fx-text-fill: #782170;
            -fx-font-size: 13px;
            -fx-cursor: hand;
        """);

        resetPassword.setOnMouseEntered(e ->
            resetPassword.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #65205F;
                -fx-font-size: 13px;
                -fx-underline: true;
                -fx-cursor: hand;
            """)
        );

        resetPassword.setOnMouseExited(e ->
            resetPassword.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #782170;
                -fx-font-size: 13px;
                -fx-cursor: hand;
            """)
        );

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
                if (isLogged) {
                    root.setLeft(new Sidebar(root, version));
                    root.setCenter(new DashboardView());
                } else {
                    errorLabel.setText("Incorrect credentials");
                    errorLabel.setVisible(true);
                    errorLabel.setManaged(true);
                }
            } catch (Exception f) {
                errorLabel.setText("Could not log in account. Check your details.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
            }
        });

        resetPassword.setOnAction(e -> {
            GridPane tokenSaved = new GridPane();
            tokenSaved.setHgap(10);
            tokenSaved.setVgap(10);
            tokenSaved.setPadding(new Insets(20));
            tokenSaved.setAlignment(Pos.CENTER);

            Label tokenAsk = new Label("Enter your token to reset your password");
            TextField userSavedToken = new TextField();
            Button submitBtn = new Button("Submit");
            submitBtn.setStyle(btn_styles);
            submitBtn.setAlignment(Pos.CENTER);
            
            Label errorLabelReset = new Label();
            errorLabelReset.setStyle("""
                -fx-text-fill: #D70652;
                -fx-font-size: 12px;
            """);
            errorLabelReset.setVisible(false);
            errorLabelReset.setManaged(false);
                
            VBox dataCombine = new VBox(10);
            dataCombine.setAlignment(Pos.CENTER);
            dataCombine.setSpacing(10);
            dataCombine.getChildren().addAll(tokenAsk, userSavedToken, errorLabelReset, submitBtn);

            tokenSaved.add(dataCombine, 1, 0);

            Stage stage = new Stage();
            stage.setTitle("Password reset");
            stage.setScene(new Scene(tokenSaved, 400, 200));
            stage.show();

            submitBtn.setOnAction(f -> {
                boolean isToken = Database.resetPassword(userSavedToken.getText());

                if (isToken) {
                    stage.close();
                    GridPane newPassword = new GridPane();
                    newPassword.setHgap(10);
                    newPassword.setVgap(10);
                    newPassword.setPadding(new Insets(20));
                    newPassword.setAlignment(Pos.CENTER);

                    Label newPasswordLabel = new Label("Password");
                    newPasswordLabel.setStyle(words_styles);
                    newPasswordLabel.setMaxWidth(Double.MAX_VALUE);

                    PasswordField newPasswordField = new PasswordField();
                    newPasswordField.setPromptText("Enter your password");
                    newPasswordField.setPrefHeight(40);
                    newPasswordField.setStyle(table_style);

                    Button submitPassword = new Button("Submit");
                    submitPassword.setMaxWidth(Double.MAX_VALUE);
                    submitPassword.setStyle(btn_styles);

                    VBox newDataCombine = new VBox(10);
                    newDataCombine.setAlignment(Pos.CENTER);
                    newDataCombine.setSpacing(10);
                    newDataCombine.getChildren().addAll(newPasswordLabel, newPasswordField, submitPassword);

                    newPassword.add(newDataCombine, 1, 0);

                    Stage stageReset = new Stage();
                    stageReset.setTitle("Password reset");
                    stageReset.setScene(new Scene(newPassword, 400, 200));
                    stageReset.show();

                    submitPassword.setOnAction(y -> {

                    });
                }

                errorLabelReset.setText("Token is incorrect");
                errorLabelReset.setVisible(true);
                errorLabelReset.setManaged(true);
            });
        });

        content.getChildren().addAll(
            title,
            usernameLabel,
            usernameField,
            passwordLabel,
            passwordField,
            resetPassword,
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