package com.financify.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {
    public Sidebar() {
        setSpacing(10);
        setPadding(new Insets(15));
        setPrefWidth(220);

        Button dashboardBtn = new Button("Dashboard");
        Button transactionsBtn = new Button("Transactions");
        Button goalsBtn = new Button("Goals");
        Button netWorthBtn = new Button("Net Worth");

        dashboardBtn.setMaxWidth(Double.MAX_VALUE);
        transactionsBtn.setMaxWidth(Double.MAX_VALUE);
        goalsBtn.setMaxWidth(Double.MAX_VALUE);
        netWorthBtn.setMaxWidth(Double.MAX_VALUE);

        getChildren().addAll(
            dashboardBtn,
            transactionsBtn,
            goalsBtn,
            netWorthBtn
        );
    }
}
