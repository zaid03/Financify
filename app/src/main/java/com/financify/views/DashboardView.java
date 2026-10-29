package com.financify.views;

import java.time.Year;
import java.util.List;

import com.financify.Database;
import com.financify.models.DashboardStat;
import com.financify.models.ExpenseCategoryModel;
import com.financify.models.MonthlySummaryModel;
import com.financify.models.NetWorthGrowthModel;
import com.financify.models.NetWorthModel;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
        currentSituationTable.setMaxHeight(245);
        currentSituationTable.setStyle(table_style);
        currentSituationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        NetWorthModel netWorth = Database.getNetWorthExact();
        double selfMoney = 0;
        Double totalEarned = Database.getTotalIncome();
        Double currentLoans = 0.00;
        if (netWorth != null) {
            selfMoney = netWorth.getNetWorth();
            currentLoans = netWorth.getLoans();
        }
        Double totalSpent = Database.getTotalSpent();
        Double montlySaving = totalEarned - totalSpent;
        double totalSpentMajor = Database.getTotalSpentMajorPurchases();
        Double cashFlow = totalEarned - (totalSpentMajor + totalSpent);
        Double savingRate = 0.00;
        Double totalSavingRate = 0.00;
        if (totalEarned > 0) {
            savingRate = (montlySaving / totalEarned) * 100;
            totalSavingRate = ((montlySaving - totalSpentMajor) / totalEarned) * 100;
        }
        currentSituationTable.getItems().addAll(
            new DashboardStat("Current Savings", selfMoney + " MAD"),
            new DashboardStat("Monthly Earning",  totalEarned + " MAD"),
            new DashboardStat("Current Loans", currentLoans + " MAD"),
            new DashboardStat("Month's Expenses", totalSpent + " MAD"),
            new DashboardStat("Month's Savings", montlySaving + " MAD"),
            new DashboardStat("Month's Major Expenses", totalSpentMajor + " MAD"),
            new DashboardStat("Cash Flow", cashFlow + " MAD"),
            new DashboardStat("Monthly Savings Rate ", String.format("%.2f%%", savingRate)),
            new DashboardStat("Total Savings Rate ", String.format("%.2f%%", totalSavingRate))
        );

        VBox currentSituation = new VBox();
        currentSituation.getChildren().addAll(situation_title, currentSituationTable);
        currentSituation.setAlignment(Pos.TOP_CENTER);

        Label monthly_title = new Label("Monthly summary");
        monthly_title.setStyle(words_styles);

        TableView<MonthlySummaryModel> monthlySummaryTable = new TableView<>();
        TableColumn<MonthlySummaryModel, String> monthColumn = new TableColumn<>("Month");
        TableColumn<MonthlySummaryModel, Double> incomeColumn = new TableColumn<>("Income");
        TableColumn<MonthlySummaryModel, Double> espensesColumn = new TableColumn<>("Expenses");
        TableColumn<MonthlySummaryModel, Double> espensesMajorColumn = new TableColumn<>("Major Expenses");
        TableColumn<MonthlySummaryModel, Double> cashFlowColumn = new TableColumn<>("Cash Flow");
        TableColumn<MonthlySummaryModel, Double> savedColumn = new TableColumn<>("Saved");
        TableColumn<MonthlySummaryModel, Double> monthRateColumn = new TableColumn<>("Month Rate");
        TableColumn<MonthlySummaryModel, Double> globalRateColumn = new TableColumn<>("Global Rate");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        incomeColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double income, boolean empty) {
            super.updateItem(income, empty);
                if (empty || income == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", income));
                }
            }
        });
        espensesColumn.setCellValueFactory(new PropertyValueFactory<>("expenses"));
        espensesColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double expenses, boolean empty) {
            super.updateItem(expenses, empty);
                if (empty || expenses == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", expenses));
                }
            }
        });
        espensesMajorColumn.setCellValueFactory(new PropertyValueFactory<>("majorExpenses"));
        espensesMajorColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double majorExpenses, boolean empty) {
            super.updateItem(majorExpenses, empty);
                if (empty || majorExpenses == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", majorExpenses));
                }
            }
        });
        cashFlowColumn.setCellValueFactory(new PropertyValueFactory<>("cashFlow"));
        cashFlowColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double cashFlow, boolean empty) {
            super.updateItem(cashFlow, empty);
                if (empty || cashFlow == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", cashFlow));
                }
            }
        });
        savedColumn.setCellValueFactory(new PropertyValueFactory<>("saved"));
        savedColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double saved, boolean empty) {
            super.updateItem(saved, empty);
                if (empty || saved == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", saved));
                }
            }
        });
        monthRateColumn.setCellValueFactory(new PropertyValueFactory<>("savingRate"));
        monthRateColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double savingRate, boolean empty) {
            super.updateItem(savingRate, empty);
                if (empty || savingRate == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f %%", savingRate));
                }
            }
        });
        globalRateColumn.setCellValueFactory(new PropertyValueFactory<>("globalRate"));
        globalRateColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double globalRate, boolean empty) {
            super.updateItem(globalRate, empty);
                if (empty || globalRate == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f %%", globalRate));
                }
            }
        });
        monthlySummaryTable.getColumns().addAll(monthColumn, incomeColumn, espensesColumn, espensesMajorColumn, cashFlowColumn, savedColumn, monthRateColumn, globalRateColumn);
        monthlySummaryTable.setMaxHeight(220);
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
        breakdown_table.setMaxHeight(245);
        breakdown_table.setStyle(table_style);
        breakdown_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        List<ExpenseCategoryModel> breakdown_expenses = Database.getExpensesByCategory();
        breakdown_table.getItems().addAll(breakdown_expenses);
        
        VBox expenseBreakdown = new VBox();
        expenseBreakdown.getChildren().addAll(breakdown_title, breakdown_table);
        expenseBreakdown.setAlignment(Pos.TOP_CENTER);

        Label growth_title = new Label("Net worth breakdown");
        growth_title.setStyle(words_styles); 

        int currentYearStat = Year.now().getValue();
        Double[][] stats = Database.getNetWorthStats(currentYearStat);
        Double[] CurrentMonth = stats[0];
        Double CurentBankBalance = CurrentMonth[0] != null ? CurrentMonth[0] : 0.0;
        Double CurrentLoan = CurrentMonth[1] != null ? CurrentMonth[1] : 0.0;
        Double[] lastMonth = stats[1];
        Double lastBankBalance = lastMonth[0] != null ? lastMonth[0] : 0.0;
        Double lastLoan = lastMonth[1] != null ? lastMonth[1] : 0.0;
        Double curentNetWorth = CurentBankBalance - CurrentLoan;
        Double lastNetWorth = lastBankBalance - lastLoan;
        Double netWorthGrowthDash = lastNetWorth != 0 ? ((curentNetWorth - lastNetWorth) / lastNetWorth) * 100 : 0;
        
        TableView<NetWorthGrowthModel> netWorthGrowrthTable = new TableView<>();
        TableColumn<NetWorthGrowthModel, String> monthGrowthColumn = new TableColumn<>("Month");
        TableColumn<NetWorthGrowthModel, String> savingsColumn = new TableColumn<>("Savings");
        TableColumn<NetWorthGrowthModel, String> growthColumn = new TableColumn<>("Net worth growth");
        monthGrowthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        savingsColumn.setCellValueFactory(new PropertyValueFactory<>("netWorth"));
        growthColumn.setCellValueFactory(cellData -> {
            ObservableList<NetWorthGrowthModel> items = netWorthGrowrthTable.getItems();
            int index = items.indexOf(cellData.getValue());

            if (index <= 0) {
                return new SimpleObjectProperty<>(null);
            }

            double currentSavings = cellData.getValue().getNetWorth();
            double previousSavings = items.get(index - 1).getNetWorth();

            double growth = previousSavings != 0 ? ((currentSavings - previousSavings) / previousSavings) * 100 : 0;

            return new SimpleStringProperty(String.format("%.2f%%", growth));
        });      
        netWorthGrowrthTable.getColumns().addAll(monthGrowthColumn, savingsColumn, growthColumn);
        netWorthGrowrthTable.setMaxHeight(245);
        netWorthGrowrthTable.setStyle(table_style);
        netWorthGrowrthTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        List<NetWorthGrowthModel> growth = Database.getNetWorthGrowth();
        netWorthGrowrthTable.getItems().addAll(growth);

        VBox netWorthGrowth = new VBox();
        netWorthGrowth.getChildren().addAll(growth_title, netWorthGrowrthTable);
        netWorthGrowth.setAlignment(Pos.TOP_CENTER);

        GridPane main_content = new GridPane();
        main_content.add(currentSituation, 0, 0);
        main_content.add(expenseBreakdown, 1, 0);
        main_content.add(netWorthGrowth, 2, 0);
        main_content.add(monthlySummary, 0, 1);
        main_content.setColumnSpan(monthlySummary, 3);
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
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        var viewportHeight = Bindings.createDoubleBinding(
            () -> scrollPane.getViewportBounds().getHeight(),
            scrollPane.viewportBoundsProperty()
        );
        var topTableHeight = Bindings.max(245, viewportHeight.multiply(0.32));
        currentSituationTable.setMaxHeight(Double.MAX_VALUE);
        breakdown_table.setMaxHeight(Double.MAX_VALUE);
        netWorthGrowrthTable.setMaxHeight(Double.MAX_VALUE);
        currentSituationTable.prefHeightProperty().bind(topTableHeight);
        breakdown_table.prefHeightProperty().bind(topTableHeight);
        netWorthGrowrthTable.prefHeightProperty().bind(topTableHeight);
        monthlySummaryTable.setMaxHeight(Double.MAX_VALUE);
        monthlySummaryTable.prefHeightProperty().bind(Bindings.max(220, viewportHeight.multiply(0.28)));
        getChildren().add(scrollPane);
    }
}