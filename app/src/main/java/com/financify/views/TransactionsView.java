package com.financify.views;

import java.time.LocalDate;
import java.util.List;

import com.financify.Database;
import com.financify.models.TransactionLegend;
import com.financify.models.Transactions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TransactionsView extends VBox{
    public TransactionsView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Transactions");
        Label legend = new Label("Legend");

        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #6a726a;
        """;

        title.setStyle(title_styles);
        legend.setStyle(title_styles);

        Label filter = new Label("Change the date to display its transactions");

        ComboBox<Integer> monthComboBox = new ComboBox<>();
        ComboBox<Integer> yearComboBox = new ComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBox.getItems().add(i);
        }
        yearComboBox.getItems().addAll(Database.getAllYearsFilter());
        monthComboBox.setValue(LocalDate.now().getMonthValue());
        yearComboBox.setValue(LocalDate.now().getYear());

        Button add_button = new Button("Add transaction");
        String btn_styles = """
            -fx-background-color: #abbaab;
            -fx-text-fill: #000000;
            -fx-font-size: 12px;
            -fx-padding: 4 8;
            -fx-background-radius: 4;
        """;
        add_button.setStyle(btn_styles);

        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER);
        filters.getChildren().addAll(filter, monthComboBox, yearComboBox);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(add_button);
        topBar.setCenter(filters);
        
        TableView<Transactions> transaction_table = new TableView<>();
        TableColumn<Transactions, String> dateColumn = new TableColumn<>("Date");
        TableColumn<Transactions, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Transactions, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<Transactions, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Transactions, Double> amountColumn = new TableColumn<>("Amount");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transaction_table.getColumns().addAll(
            dateColumn,
            typeColumn,
            categoryColumn,
            descriptionColumn,
            amountColumn
        );
        List<Transactions> transactions = Database.getAllTransactions();
        transaction_table.getItems().addAll(transactions);
        transaction_table.setMinHeight(500);

        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """;
        transaction_table.setStyle(table_style);
        transaction_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        double total_spent = Database.getTotalSpent();
        Label amount = new Label("Amount spent this month :" + total_spent + "MAD");
        
        Runnable refrechTable = () -> {
            transaction_table.getItems().setAll(
                Database.getSomeTransactions(yearComboBox.getValue(), monthComboBox.getValue())
            );
            double totalSpent = Database.getTotalSpent(yearComboBox.getValue(), monthComboBox.getValue());
            amount.setText("Amount spent this month: " + totalSpent + " MAD");
        };
        monthComboBox.setOnAction(e -> refrechTable.run());
        yearComboBox.setOnAction(e -> refrechTable.run());

        TableView<TransactionLegend> TransactionLegend_table = new TableView<>();
        TableColumn<TransactionLegend, String> legendTypeColumn = new TableColumn<>("Type");
        TableColumn<TransactionLegend, String> legendCategoryColumn = new TableColumn<>("Category");
        legendTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        legendCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        TransactionLegend_table.getColumns().addAll(
            legendTypeColumn,
            legendCategoryColumn
        );
        TransactionLegend_table.getItems().addAll(
            new TransactionLegend("Income", "Salary"),
            new TransactionLegend("Expense", "Food"),
            new TransactionLegend("Expense", "Transport"),
            new TransactionLegend("Expense", "Internet"),
            new TransactionLegend("Expense", "Going out"),
            new TransactionLegend("Expense", "Shopping"),
            new TransactionLegend("Expense", "Entertainment"),
            new TransactionLegend("Expense", "Bills"),
            new TransactionLegend("Expense", "Other")
        );
        TransactionLegend_table.setStyle(table_style);
        TransactionLegend_table.setMaxWidth(304);
        legendTypeColumn.setPrefWidth(120);
        legendCategoryColumn.setPrefWidth(180);

        String words_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #000000;
        """;

        amount.setStyle(words_styles);

        //adding a transaction grid
        DatePicker add_Date = new DatePicker();
        ComboBox<String> typeCombo = new ComboBox<>();
        ComboBox<String> categoryCombo = new ComboBox<>();
        typeCombo.getItems().addAll(
            "Income",
            "Expense"
        );
        typeCombo.setOnAction(e -> {
            categoryCombo.getItems().clear();

            if (typeCombo.getValue().equals("Income")) {
                categoryCombo.getItems().add("Salary");
            } else {
                categoryCombo.getItems().addAll(
                    "Food",
                    "Transport",
                    "Internet",
                    "Going out",
                    "Shopping",
                    "Entertainment",
                    "Bills",
                    "Other"
                );
            }
        });
        TextField description = new TextField();
        TextField amount_add = new TextField();

        add_button.setOnAction(e -> {
            GridPane add_transaction_grid = new GridPane();
            add_transaction_grid.setHgap(10);
            add_transaction_grid.setVgap(10);
            add_transaction_grid.setPadding(new Insets(20));

            add_transaction_grid.add(new Label("Date: "), 0, 0);
            add_transaction_grid.add(add_Date, 1, 0);

            add_transaction_grid.add(new Label("Type: "), 0,1);
            add_transaction_grid.add(typeCombo, 1, 1);

            add_transaction_grid.add(new Label("Category: "), 0, 2);
            add_transaction_grid.add(categoryCombo, 1, 2);

            add_transaction_grid.add(new Label("Description: "), 0, 3);
            add_transaction_grid.add(description, 1, 3);

            add_transaction_grid.add(new Label("Amount: "), 0, 4);
            add_transaction_grid.add(amount_add, 1, 4);

            Button addTransaction = new Button("Add");
            addTransaction.setStyle(btn_styles);
            addTransaction.setAlignment(Pos.CENTER);
            add_transaction_grid.add(addTransaction, 1, 5);

            Stage stage = new Stage();
            stage.setTitle("Add Transaction");
            stage.setScene(new Scene(add_transaction_grid, 400, 280));
            stage.show();

            addTransaction.setOnAction(f -> {
                Database.addTransaction(
                    add_Date.getValue().toString(),
                    typeCombo.getValue(),
                    categoryCombo.getValue(),
                    description.getText(),
                    Double.parseDouble(amount_add.getText())
                );

                refrechTable.run();
                stage.close();
            });
        });

        content.getChildren().addAll(
            title,
            filter,
            topBar,
            transaction_table,
            amount,
            legend,
            TransactionLegend_table
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}