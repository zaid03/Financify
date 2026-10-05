package com.financify.views;

import java.util.List;

import com.financify.Database;
import com.financify.models.Transactions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class MajorPurchasesView extends VBox{
    public MajorPurchasesView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Major expenses");

        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
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
            -fx-fill: #0B3040;
        """;
        String money_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #4495b8;
            -fx-fill: #4495b8;
        """;
        title.setStyle(title_styles);

        Label filter = new Label("All major expenses made");
        filter.setStyle(words_styles); 

        TableView<Transactions> transaction_table = new TableView<>();
        TableColumn<Transactions, String> dateColumn = new TableColumn<>("Date");
        TableColumn<Transactions, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Transactions, String> categoryColumn = new TableColumn<>("Category");
        TableColumn<Transactions, String> natureColumn = new TableColumn<>("Nature");
        TableColumn<Transactions, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Transactions, Double> amountColumn = new TableColumn<>("Amount");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        natureColumn.setCellValueFactory(new PropertyValueFactory<>("isBigPurchase"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setCellFactory(column -> new TableCell<>() {
        @Override
        protected void updateItem(Double amount, boolean empty) {
            super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", amount));
                }
            }
        });
        transaction_table.getColumns().addAll(
            dateColumn,
            typeColumn,
            categoryColumn,
            natureColumn,
            descriptionColumn,
            amountColumn
        );
        List<Transactions> transactions = Database.getMajorPurchases();
        transaction_table.getItems().addAll(transactions);
        transaction_table.setMinHeight(500);
        transaction_table.setStyle(table_style);
        transaction_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        double total_spent = Database.getTotalSpentMajorPurchases();
        String total_spent_string = String.valueOf(total_spent);
        TextFlow amount = new TextFlow();
        Text amountText = new Text("Amount spent this month : ");
        Text amountNumber = new Text(total_spent_string + " MAD");
        amountText.setStyle(words_styles);
        amountNumber.setStyle(money_styles);
        amount.getChildren().addAll(amountText, amountNumber);

        double all_total_spent = Database.getAllTotalSpentMajorPurchases();
        String all_total_spent_string = String.valueOf(all_total_spent);
        TextFlow allAmount = new TextFlow();
        Text allAmountText = new Text("Total spent on major purchases : ");
        Text allAmountNumber = new Text(all_total_spent_string + " MAD");
        allAmountText.setStyle(words_styles);
        allAmountNumber.setStyle(money_styles);
        allAmount.getChildren().addAll(allAmountText, allAmountNumber);

        HBox stats = new HBox(10);
        stats.setAlignment(Pos.CENTER);
        stats.getChildren().addAll(amount, allAmount);

        content.getChildren().addAll(
            title,
            filter,
            transaction_table,
            stats
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}