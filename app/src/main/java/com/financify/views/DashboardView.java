package com.financify.views;

import com.financify.Database;
import com.financify.models.DashboardStat;
import com.financify.models.NetWorthModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DashboardView extends VBox{
    public DashboardView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

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
        Label title = new Label("Dashboard");
        title.setStyle(title_styles);

        Label situation_title = new Label("Current Situation");
        situation_title.setStyle(words_styles);

        TableView<DashboardStat> currentSituationTable = new TableView<>();
        TableColumn<DashboardStat, String> metricColumn = new TableColumn<>("Metric");
        TableColumn<DashboardStat, String> valueColumn = new TableColumn<>("Value");
        metricColumn.setCellValueFactory(new PropertyValueFactory<>("Metric"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("Value"));
        currentSituationTable.getColumns().addAll(metricColumn, valueColumn);
        currentSituationTable.setMaxHeight(200);

        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """;
        currentSituationTable.setStyle(table_style);
        currentSituationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        NetWorthModel netWorth = Database.getNetWorthExact();
        double selfMoney = 0;
        Double totalEarned = Database.getTotalIncome();
        int currentLoans = 0;
        if (netWorth != null) {
            selfMoney = netWorth.getNetWorth();
            currentLoans = netWorth.getLoans();
        }
        Double totalSpent = Database.getTotalSpent();
        Double montlySaving = totalEarned - totalSpent;
        Double savingRate = 0.00;
        if (totalEarned > 0) {
            savingRate = (montlySaving / totalEarned) * 100;
        }

        currentSituationTable.getItems().addAll(
            new DashboardStat("Current Savings", selfMoney + " MAD"),
            new DashboardStat("Monthly Salary",  totalEarned + " MAD"),
            new DashboardStat("Current Loans", currentLoans + " MAD"),
            new DashboardStat("This Month's Expenses", totalSpent + " MAD"),
            new DashboardStat("This Month's Savings", montlySaving + " MAD"),
            new DashboardStat("Savings Rate", savingRate + " %")
        );

        VBox currentSituation = new VBox();
        currentSituation.getChildren().addAll(situation_title, currentSituationTable);
        currentSituation.setAlignment(Pos.TOP_CENTER);

        VBox monthlySummary = new VBox();
        VBox expenseBreakdown = new VBox();
        VBox netWorthGrowth = new VBox();

        GridPane main_content = new GridPane();
        main_content.add(currentSituation, 0, 0);
        main_content.add(monthlySummary, 1, 0);
        main_content.add(expenseBreakdown, 0, 1);
        main_content.add(netWorthGrowth, 1, 1);

        content.getChildren().addAll(
            title,
            main_content
            
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}