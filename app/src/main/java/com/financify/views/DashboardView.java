package com.financify.views;

import java.util.List;

import com.financify.Database;
import com.financify.models.DashboardStat;
import com.financify.models.ExpenseCategoryModel;
import com.financify.models.MonthlySummaryModel;
import com.financify.models.NetWorthGrowthModel;
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
        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
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

        Label monthly_title = new Label("Monthly summary");
        monthly_title.setStyle(words_styles);

        TableView<MonthlySummaryModel> monthlySummaryTable = new TableView<>();
        TableColumn<MonthlySummaryModel, String> monthColumn = new TableColumn<>("Month");
        TableColumn<MonthlySummaryModel, String> incomeColumn = new TableColumn<>("Income");
        TableColumn<MonthlySummaryModel, String> espensesColumn = new TableColumn<>("Expenses");
        TableColumn<MonthlySummaryModel, String> savedColumn = new TableColumn<>("Saved");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        espensesColumn.setCellValueFactory(new PropertyValueFactory<>("expenses"));
        savedColumn.setCellValueFactory(new PropertyValueFactory<>("saved"));
        monthlySummaryTable.getColumns().addAll(monthColumn, incomeColumn, espensesColumn, savedColumn);
        monthlySummaryTable.setMaxHeight(200);
        monthlySummaryTable.setStyle(table_style);
        monthlySummaryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        List<MonthlySummaryModel> summary = Database.getMonthsIncomesEspeneses();
        monthlySummaryTable.getItems().addAll(summary);

        VBox monthlySummary = new VBox();
        monthlySummary.getChildren().addAll(monthly_title, monthlySummaryTable);
        monthlySummary.setAlignment(Pos.TOP_CENTER);

        Label breakdown_title = new Label("Expense breakdown");
        breakdown_title.setStyle(words_styles);

        TableView<ExpenseCategoryModel> breakdown_table = new TableView<>();
        TableColumn<ExpenseCategoryModel, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<ExpenseCategoryModel, String> amountColumn = new TableColumn<>("Amount");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        breakdown_table.getColumns().addAll(categoryColumn, amountColumn);
        breakdown_table.setMaxHeight(200);
        breakdown_table.setStyle(table_style);
        breakdown_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        List<ExpenseCategoryModel> breakdown_expenses = Database.getExpensesByCategory();
        breakdown_table.getItems().addAll(breakdown_expenses);
        
        VBox expenseBreakdown = new VBox();
        expenseBreakdown.getChildren().addAll(breakdown_title, breakdown_table);
        expenseBreakdown.setAlignment(Pos.TOP_CENTER);

        Label growth_title = new Label("Net worth breakdown");
        growth_title.setStyle(words_styles); 

        TableView<NetWorthGrowthModel> netWorthGrowrthTable = new TableView<>();
        TableColumn<NetWorthGrowthModel, String> monthGrowthColumn = new TableColumn<>("Month");
        TableColumn<NetWorthGrowthModel, String> savingsColumn = new TableColumn<>("Savings");
        monthGrowthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        savingsColumn.setCellValueFactory(new PropertyValueFactory<>("netWorth"));
        netWorthGrowrthTable.getColumns().addAll(monthGrowthColumn, savingsColumn);
        netWorthGrowrthTable.setMaxHeight(200);
        netWorthGrowrthTable.setStyle(table_style);
        netWorthGrowrthTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        List<NetWorthGrowthModel> growth = Database.getNetWorthGrowth();
        netWorthGrowrthTable.getItems().addAll(growth);

        VBox netWorthGrowth = new VBox();
        netWorthGrowth.getChildren().addAll(growth_title, netWorthGrowrthTable);
        netWorthGrowth.setAlignment(Pos.TOP_CENTER);

        GridPane main_content = new GridPane();
        main_content.add(currentSituation, 0, 0);
        main_content.add(monthlySummary, 1, 0);
        main_content.add(expenseBreakdown, 0, 1);
        main_content.add(netWorthGrowth, 1, 1);
        main_content.setAlignment(Pos.CENTER);
        main_content.setHgap(20);
        main_content.setVgap(20);

        Label zaid = new Label("Made with ♥ by zaid");
        zaid.setStyle(words_styles);
        content.getChildren().addAll(
            title,
            main_content,
            zaid
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}