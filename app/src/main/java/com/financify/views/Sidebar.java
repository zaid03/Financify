package com.financify.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

        dashboardBtn.setStyle(buttonStyle);
        dashboardBtn.setOnMouseEntered(e -> dashboardBtn.setStyle(hoverStyle));
        dashboardBtn.setOnMouseExited(e -> dashboardBtn.setStyle(buttonStyle));
        transactionsBtn.setStyle(buttonStyle);
        transactionsBtn.setOnMouseEntered(e -> transactionsBtn.setStyle(hoverStyle));
        transactionsBtn.setOnMouseExited(e -> transactionsBtn.setStyle(buttonStyle));
        goalsBtn.setStyle(buttonStyle);
        goalsBtn.setOnMouseEntered(e -> goalsBtn.setStyle(hoverStyle));
        goalsBtn.setOnMouseExited(e -> goalsBtn.setStyle(buttonStyle));
        netWorthBtn.setStyle(buttonStyle);
        netWorthBtn.setOnMouseEntered(e -> netWorthBtn.setStyle(hoverStyle));
        netWorthBtn.setOnMouseExited(e -> netWorthBtn.setStyle(buttonStyle));

        setAlignment(Pos.CENTER);
        getChildren().addAll(
            dashboardBtn,
            transactionsBtn,
            netWorthBtn,
            goalsBtn
        );

        dashboardBtn.setOnAction(e -> {
            root.setCenter(new DashboardView());
        });
        transactionsBtn.setOnAction(e -> {
            root.setCenter(new TransactionsView());
        });
        netWorthBtn.setOnAction(e -> {
            root.setCenter(new NetWorthView());
        });
        goalsBtn.setOnAction(e -> {
            root.setCenter(new GoalsView());
        });
    }
}
