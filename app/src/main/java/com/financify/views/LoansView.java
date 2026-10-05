package com.financify.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.financify.Database;
import com.financify.models.GoalSummaryModel;
import com.financify.models.Loans;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class LoansView extends VBox {
    public LoansView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);
        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #782170;
        """;
        String btn_styles = """
            -fx-background-color: #156082;
            -fx-text-fill: #ffffff;
            -fx-font-size: 12px;
            -fx-padding: 4 8;
            -fx-background-radius: 4;
        """;
        String words_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #0B3040;
        """;
        String money_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #4495b8;
            -fx-fill: #4495b8;
        """;
        String phrases_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #0B3040;
            -fx-fill: #0B3040;
        """;
        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """;

        Label title = new Label("Debt & Loan Management");
        title.setStyle(title_styles);

        Button add_button = new Button("Add a Loan");
        add_button.setStyle(btn_styles);

        Double[] loans = Database.getTotalLoans();

        Double loansTotalAmount = loans[0];
        TextFlow loansTotal = new TextFlow();
        Text loansTotalText = new Text("Total of loans : ");
        Text loansTotalNumber = new Text(String.format("%.2f mad", loansTotalAmount));        
        loansTotalText.setStyle(phrases_styles);
        loansTotalNumber.setStyle(money_styles);
        loansTotal.getChildren().addAll(loansTotalText, loansTotalNumber);

        Double monthly = loans[1];
        TextFlow monthlyTotal= new TextFlow();
        Text monthlyText = new Text("Monthly payments : ");
        Text monthlyNumber = new Text(String.format("%.2f mad", monthly));        
        monthlyText.setStyle(phrases_styles);
        monthlyNumber.setStyle(money_styles);
        monthlyTotal.getChildren().addAll(monthlyText, monthlyNumber);

        HBox totals = new HBox(10);
        totals.getChildren().addAll(monthlyTotal, loansTotal);
        totals.setAlignment(Pos.CENTER);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(add_button);
        topBar.setCenter(totals);

        Label uncompletedLoans = new Label("On-going Loans");
        uncompletedLoans.setStyle(phrases_styles);

        TableView<Loans> loans_table = new TableView<>();
        TableColumn<Loans, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Loans, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Loans, String> sourceColumn = new TableColumn<>("Source");
        TableColumn<Loans, Double> amountColumn = new TableColumn<>("Amount");
        TableColumn<Loans, Double> remainingColumn = new TableColumn<>("Remaining");
        TableColumn<Loans, Double> monthlyColumn = new TableColumn<>("Monthly");
        TableColumn<Loans, String> startDateColumn = new TableColumn<>("Start date");
        TableColumn<Loans, String> dueDateColumn = new TableColumn<>("Due date");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("Source"));
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
        remainingColumn.setCellValueFactory(new PropertyValueFactory<>("Remaining"));
        remainingColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double Remaining, boolean empty) {
                super.updateItem(Remaining, empty);
                if (empty || Remaining == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", Remaining));
                }
            }
        });
        monthlyColumn.setCellValueFactory(new PropertyValueFactory<>("Monthly"));
        monthlyColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double Monthly, boolean empty) {
                super.updateItem(Monthly, empty);
                if (empty || Monthly == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", Monthly));
                }
            }
        });
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("Start_date"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("Due_date"));
        loans_table.getColumns().addAll(
            nameColumn,
            descriptionColumn,
            sourceColumn,
            amountColumn,
            remainingColumn,
            monthlyColumn,
            startDateColumn,
            dueDateColumn
        );
        List<Loans> uncompletedLoansData = Database.getLoans();
        loans_table.getItems().addAll(uncompletedLoansData);
        loans_table.setMaxHeight(200);
        loans_table.setStyle(table_style);
        loans_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Label completedTitle = new Label("Completed Loans");
        completedTitle.setStyle(phrases_styles);

        TableView<Loans> completedLoans_table = new TableView<>();
        TableColumn<Loans, String> nameCompletedColumn = new TableColumn<>("Name");
        TableColumn<Loans, String> descriptionCompletedColumn = new TableColumn<>("Description");
        TableColumn<Loans, String> sourceCompletedColumn = new TableColumn<>("Source");
        TableColumn<Loans, Double> amountCompletedColumn = new TableColumn<>("Amount");
        TableColumn<Loans, Double> remainingCompletedColumn = new TableColumn<>("Remaining");
        TableColumn<Loans, Double> monthlyCompletedColumn = new TableColumn<>("Monthly");
        TableColumn<Loans, String> startDateCompletedColumn = new TableColumn<>("Start date");
        TableColumn<Loans, String> dueDateCompletedColumn = new TableColumn<>("Due date");
        TableColumn<Loans, String> completionDateColumn = new TableColumn<>("Completion date");
        nameCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        sourceCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("Source"));
        amountCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountCompletedColumn.setCellFactory(column -> new TableCell<>() {
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
        remainingCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("Remaining"));
        remainingCompletedColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double Remaining, boolean empty) {
                super.updateItem(Remaining, empty);
                if (empty || Remaining == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", Remaining));
                }
            }
        });
        monthlyCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("Monthly"));
        monthlyCompletedColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double Monthly, boolean empty) {
                super.updateItem(Monthly, empty);
                if (empty || Monthly == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", Monthly));
                }
            }
        });
        startDateCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("Start_date"));
        dueDateCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("Due_date"));
        completionDateColumn.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
        completedLoans_table.getColumns().addAll(
            nameCompletedColumn,
            descriptionCompletedColumn,
            sourceCompletedColumn,
            amountCompletedColumn,
            remainingCompletedColumn,
            monthlyCompletedColumn,
            startDateCompletedColumn,
            dueDateCompletedColumn,
            completionDateColumn
        );
        List<Loans> completedLoans = Database.getCompletedLoans();
        completedLoans_table.getItems().addAll(completedLoans);
        completedLoans_table.setMaxHeight(200);
        completedLoans_table.setStyle(table_style);
        completedLoans_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Runnable refreshContent = () -> {
            loans_table.getItems().setAll(Database.getLoans());
            completedLoans_table.getItems().setAll(Database.getCompletedLoans());

            Double[] loansUpdate = Database.getTotalLoans();

            Double loansTotalAmountUpdate = loansUpdate[0];
            loansTotalText.setText("Total of loans : ");
            loansTotalNumber.setText(String.format("%.2f mad", loansTotalAmountUpdate));        

            Double monthlyUpdate = loansUpdate[1];
            monthlyText.setText("Monthly payments : ");
            monthlyNumber.setText(String.format("%.2f mad", monthlyUpdate));        
        };

        content.getChildren().addAll(
            title,
            topBar,
            uncompletedLoans,
            loans_table,
            completedTitle,
            completedLoans_table
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}