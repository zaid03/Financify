package com.financify.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {
    public Sidebar(BorderPane  root) {
        setSpacing(10);
        setPadding(new Insets(15));
        setPrefWidth(200);
        setStyle("-fx-background-color: #E2E2E2;");

        Button dashboardBtn = new Button("Dashboard");
        Button transactionsBtn = new Button("Transactions");
        Button goalsBtn = new Button("Goals");
        Button netWorthBtn = new Button("Net Worth");

        dashboardBtn.setMaxWidth(Double.MAX_VALUE);
        transactionsBtn.setMaxWidth(Double.MAX_VALUE);
        goalsBtn.setMaxWidth(Double.MAX_VALUE);
        netWorthBtn.setMaxWidth(Double.MAX_VALUE);

        String buttonStyle = """
            -fx-background-color: #abbaab;
            -fx-text-fill: #000000;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
            -fx-background-radius: 8;
            -fx-padding: 10 12;
            """;
        dashboardBtn.setStyle(buttonStyle);
        transactionsBtn.setStyle(buttonStyle);
        goalsBtn.setStyle(buttonStyle);
        netWorthBtn.setStyle(buttonStyle);

        setAlignment(Pos.CENTER);
        getChildren().addAll(
            dashboardBtn,
            transactionsBtn,
            goalsBtn,
            netWorthBtn
        );

        dashboardBtn.setOnAction(e -> {
            root.setCenter(new Label("dashboard"));
        });
        transactionsBtn.setOnAction(e -> {
            root.setCenter(new TransactionsView());
        });
        goalsBtn.setOnAction(e -> {
            root.setCenter(new Label("goals"));
        });
        netWorthBtn.setOnAction(e -> {
            root.setCenter(new Label("net Worth"));
        });
    }
}
