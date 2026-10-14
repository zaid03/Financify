package com.financify.views;

import java.io.File;
import java.io.IOException;

import com.financify.Database;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class Sidebar extends VBox {
    public Sidebar(BorderPane  root, String version) {
        setSpacing(10);
        setPadding(new Insets(15));
        setPrefWidth(200);
        setStyle("-fx-background-color: #E2E2E2;");

        String buttonStyle = """
            -fx-background-color: transparent;
            -fx-text-fill: #782170;
            -fx-border-color: #782170;
            -fx-border-width: 2;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 12 16;
            -fx-cursor: hand;
        """;
        String hoverStyle = """
            -fx-border-color: #782170;
            -fx-border-width: 2;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-padding: 12 16;
            -fx-cursor: hand;
            -fx-background-color: #923089;
            -fx-text-fill: white;
        """;
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

        Label app_name = new Label("Financify");
        app_name.setStyle(title_styles);
        Label app_version = new Label("V" + version);
        app_version.setStyle(words_styles);

        VBox app_titles = new VBox(10);
        app_titles.setAlignment(Pos.TOP_LEFT);
        app_titles.getChildren().addAll(app_name, app_version);

        Button dashboardBtn = new Button("Dashboard");
        Button bigPurchases = new Button("Major Expenses");
        Button transactionsBtn = new Button("Transactions");
        Button goalsBtn = new Button("Goals");
        Button netWorthBtn = new Button("Net Worth");
        Button loansBtn = new Button("Loans");

        Button backUpBtn = new Button("Back up database");

        dashboardBtn.setStyle(buttonStyle);
        dashboardBtn.setOnMouseEntered(e -> dashboardBtn.setStyle(hoverStyle));
        dashboardBtn.setOnMouseExited(e -> dashboardBtn.setStyle(buttonStyle));
        transactionsBtn.setStyle(buttonStyle);
        transactionsBtn.setOnMouseEntered(e -> transactionsBtn.setStyle(hoverStyle));
        transactionsBtn.setOnMouseExited(e -> transactionsBtn.setStyle(buttonStyle));
        bigPurchases.setStyle(buttonStyle);
        bigPurchases.setOnMouseEntered(e -> bigPurchases.setStyle(hoverStyle));
        bigPurchases.setOnMouseExited(e -> bigPurchases.setStyle(buttonStyle));
        goalsBtn.setStyle(buttonStyle);
        goalsBtn.setOnMouseEntered(e -> goalsBtn.setStyle(hoverStyle));
        goalsBtn.setOnMouseExited(e -> goalsBtn.setStyle(buttonStyle));
        netWorthBtn.setStyle(buttonStyle);
        netWorthBtn.setOnMouseEntered(e -> netWorthBtn.setStyle(hoverStyle));
        netWorthBtn.setOnMouseExited(e -> netWorthBtn.setStyle(buttonStyle));
        loansBtn.setStyle(buttonStyle);
        loansBtn.setOnMouseEntered(e -> loansBtn.setStyle(hoverStyle));
        loansBtn.setOnMouseExited(e -> loansBtn.setStyle(buttonStyle));
        backUpBtn.setStyle(buttonStyle);
        backUpBtn.setOnMouseEntered(e -> backUpBtn.setStyle(hoverStyle));
        backUpBtn.setOnMouseExited(e -> backUpBtn.setStyle(buttonStyle));


        dashboardBtn.setMaxWidth(Double.MAX_VALUE);
        transactionsBtn.setMaxWidth(Double.MAX_VALUE);
        bigPurchases.setMaxWidth(Double.MAX_VALUE);
        goalsBtn.setMaxWidth(Double.MAX_VALUE);
        netWorthBtn.setMaxWidth(Double.MAX_VALUE);
        loansBtn.setMaxWidth(Double.MAX_VALUE);
        backUpBtn.setMaxWidth(Double.MAX_VALUE);

        dashboardBtn.setOnAction(e -> {root.setCenter(new DashboardView());});
        transactionsBtn.setOnAction(e -> {root.setCenter(new TransactionsView());});
        bigPurchases.setOnAction(e -> {root.setCenter(new MajorPurchasesView());});
        netWorthBtn.setOnAction(e -> {root.setCenter(new NetWorthView());});
        loansBtn.setOnAction(e -> {root.setCenter(new LoansView());});
        goalsBtn.setOnAction(e -> {root.setCenter(new GoalsView());});
        backUpBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Financify Backup");
            fileChooser.setInitialFileName("financify-backup.db");

            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("SQLite Database", "*.db")
            );

            File selectedFile = fileChooser.showSaveDialog(getScene().getWindow());

            if (selectedFile != null) {
                try {
                    Database.backupDatabase(selectedFile.toPath());

                    System.out.println("Backup created successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        VBox btns = new VBox(10);
        VBox.setVgrow(btns, Priority.ALWAYS);
        btns.setMaxHeight(Double.MAX_VALUE);
        btns.setAlignment(Pos.CENTER);
        btns.getChildren().addAll(dashboardBtn, transactionsBtn, bigPurchases, netWorthBtn, loansBtn, goalsBtn);

        VBox backup = new VBox(10);
        VBox.setVgrow(backup, Priority.ALWAYS);
        backup.setMaxHeight(Double.MAX_VALUE);
        backup.setAlignment(Pos.BOTTOM_LEFT);
        backup.getChildren().addAll(backUpBtn);

        getChildren().addAll(
            app_titles,
            btns,
            backup
        );
    }
}
