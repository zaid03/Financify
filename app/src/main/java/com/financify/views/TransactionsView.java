package com.financify.views;

import java.util.List;

import com.financify.Database;
import com.financify.models.TransactionLegend;
import com.financify.models.Transactions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class TransactionsView extends VBox{
    public TransactionsView() {
        VBox content = new VBox(20);
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

        content.getChildren().addAll(
            title,
            transaction_table,
            legend,
            TransactionLegend_table
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}