package com.financify.views;

import java.time.LocalDate;
import java.util.List;

import com.financify.Database;
import com.financify.models.Transactions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class TransactionsView extends VBox{
    public TransactionsView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Transactions");

        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #782170;
        """;
        title.setStyle(title_styles);

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
        Button Limit_button = new Button("Set limit");
        String btn_styles = """
            -fx-background-color: #156082;
            -fx-text-fill: #ffffff;
            -fx-font-size: 12px;
            -fx-padding: 4 8;
            -fx-background-radius: 4;
        """;
        add_button.setStyle(btn_styles);
        Limit_button.setStyle(btn_styles);

        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER);
        filters.getChildren().addAll(filter, monthComboBox, yearComboBox);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(add_button);
        topBar.setCenter(filters);
        topBar.setRight(Limit_button);
        
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
        transaction_table.setRowFactory(tv -> new TableRow<Transactions>() {
            @Override
            protected void updateItem(Transactions transaction, boolean empty) {
                super.updateItem(transaction, empty);

                if (!empty && transaction != null &&
                    transaction.getIsBigPurchase().equals("Big Purchase")) {

                    setStyle("-fx-background-color: #ffe0e0;");
                } else {
                    setStyle("");
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
        String total_spent_string = String.valueOf(total_spent);
        TextFlow amount = new TextFlow();
        Text amountText = new Text("Amount spent this month : ");
        Text amountNumber = new Text(total_spent_string + " MAD");

        Integer limit_amount = Database.getLimit();
        String limit_amount_string = String.valueOf(limit_amount);
        TextFlow limit = new TextFlow();
        Text limitText = new Text("Monthly limit : ");
        Text limitNumber = new Text(limit_amount_string + " MAD");
         
        Double leftToSpend = limit_amount - total_spent;
        String leftToSpend_string = String.valueOf(leftToSpend);
        TextFlow left = new TextFlow();
        Text leftText = new Text("Amount left to spend : ");
        Text leftNumber = new Text(leftToSpend_string + " MAD");

        HBox money_stat = new HBox(10);
        money_stat.setAlignment(Pos.CENTER);
        money_stat.getChildren().addAll(amount,  left, limit);

        Runnable refrechTable = () -> {
            transaction_table.getItems().setAll(
                Database.getSomeTransactions(yearComboBox.getValue(), monthComboBox.getValue())
            );
            double total_spent_update = Database.getTotalSpent();
            String total_spent_update_string = String.valueOf(total_spent_update);
            amountNumber.setText(total_spent_update_string + " MAD");

            Integer limit_amount_update = Database.getLimit();
            String limit_amount_update_string = String.valueOf(limit_amount_update);
            limitNumber.setText(limit_amount_update_string + " MAD");
            
            Double leftToSpend_update = limit_amount_update - total_spent_update;
            String leftToSpend_update_string = String.valueOf(leftToSpend_update);
            leftNumber.setText(leftToSpend_update_string + " MAD");
            if (leftToSpend_update < 0) {
                leftNumber.setFill(Color.web("#D70652"));
            } else {
                leftNumber.setFill(Color.web("#4495b8"));
            }
        };
        monthComboBox.setOnAction(e -> refrechTable.run());
        yearComboBox.setOnAction(e -> refrechTable.run());

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
        String test = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
        """;
        amountText.setStyle(words_styles);
        amountNumber.setStyle(money_styles);
        amount.getChildren().addAll(amountText, amountNumber);
        limitText.setStyle(words_styles);
        limitNumber.setStyle(money_styles);
        limit.getChildren().addAll(limitText, limitNumber);
        leftText.setStyle(words_styles);
        leftNumber.setStyle(test);
        if (leftToSpend < 0) {
            leftNumber.setFill(Color.web("#D70652"));
        } else {
            leftNumber.setFill(Color.web("#4495b8"));
        }
        left.getChildren().addAll(leftText, leftNumber);
        filter.setStyle(words_styles); 
        
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

            if ("Income".equals(typeCombo.getValue())) {
                categoryCombo.getItems().add("Salary");
            } else {
                categoryCombo.getItems().addAll(
                    "Food",
                    "Bills",
                    "Internet",
                    "Shopping",
                    "Transport",
                    "Going out",
                    "Big purchase",
                    "Entertainment",
                    "Other"
                );
            }
        });
        TextField description = new TextField();
        TextField amount_add = new TextField();
        CheckBox nature_add = new CheckBox();
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

            add_transaction_grid.add(new Label("Nature: "), 0, 5);
            add_transaction_grid.add(nature_add, 1, 5);

            Button addTransaction = new Button("Add");
            addTransaction.setStyle(btn_styles);
            addTransaction.setAlignment(Pos.CENTER);
            add_transaction_grid.add(addTransaction, 1, 6);

            Stage stage = new Stage();
            stage.setTitle("Add Transaction");
            stage.setScene(new Scene(add_transaction_grid, 400, 280));
            stage.show();

            addTransaction.setOnAction(f -> {
                int isBigPurchase = nature_add.isSelected() ? 1 : 0;
                Database.addTransaction(
                    add_Date.getValue().toString(),
                    typeCombo.getValue(),
                    categoryCombo.getValue(),
                    description.getText(),
                    Double.parseDouble(amount_add.getText()),
                    isBigPurchase
                );

                yearComboBox.setOnAction(null);

                yearComboBox.getItems().setAll(Database.getAllYearsFilter());
                yearComboBox.setValue(LocalDate.now().getYear());

                yearComboBox.setOnAction(p -> refrechTable.run());

                refrechTable.run();
                stage.close();

                add_Date.setValue(null);
                categoryCombo.getSelectionModel().clearSelection();
                categoryCombo.getItems().clear();
                typeCombo.getSelectionModel().clearSelection();
                description.clear();
                amount_add.clear();
                nature_add.setSelected(false);
            });

        });

        //setting a monthly limit for spending
        TextField limitSet = new TextField();
        Limit_button.setOnAction(e -> {
            GridPane add_limit_grid = new GridPane();
            add_limit_grid.setHgap(10);
            add_limit_grid.setVgap(10);
            add_limit_grid.setPadding(new Insets(20));

            add_limit_grid.add(new Label("Monthly limit: "), 0, 0);
            add_limit_grid.add(limitSet, 1, 0);

            Button setLimit = new Button("Add limit");
            setLimit.setStyle(btn_styles);
            setLimit.setAlignment(Pos.CENTER);
            add_limit_grid.add(setLimit, 1, 5);

            Stage stage = new Stage();
            stage.setTitle("Set Limit");
            stage.setScene(new Scene(add_limit_grid, 400, 150));
            stage.show();

            setLimit.setOnAction(f -> {
                Database.setLimit(Integer.parseInt(limitSet.getText()));

                stage.close();
                refrechTable.run();
            });
        });

        //update a transaction grid
        DatePicker update_Date = new DatePicker();
        ComboBox<String> typeComboUpdate = new ComboBox<>();
        ComboBox<String> categoryComboUpdate = new ComboBox<>();
        typeComboUpdate.getItems().addAll(
                    "Income",
                    "Expense"
                );
            Runnable loadCategories = () -> {
            categoryComboUpdate.getItems().clear();

            if ("Income".equals(typeComboUpdate.getValue())) {
                categoryComboUpdate.getItems().add("Salary");
            } else {
                categoryComboUpdate.getItems().addAll(
                    "Food",
                    "Bills",
                    "Internet",
                    "Shopping",
                    "Transport",
                    "Going out",
                    "Big purchase",
                    "Entertainment",
                    "Other"
                );
            }
        };
        typeComboUpdate.setOnAction(e -> loadCategories.run());
        TextField descriptionUpdate = new TextField();
        TextField amount_update = new TextField();
        CheckBox nature_update = new CheckBox();
        transaction_table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Transactions selected = transaction_table.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    update_Date.setValue(LocalDate.parse(selected.getDate()));
                    typeComboUpdate.setValue(selected.getType());
                    loadCategories.run(); 
                    categoryComboUpdate.setValue(selected.getCategory());
                    descriptionUpdate.setText(selected.getDescription());
                    amount_update.setText(selected.getAmount().toString());
                    nature_update.setSelected(selected.getIsBigPurchase().equals("Big Purchase"));

                    GridPane update_transaction_grid = new GridPane();
                    update_transaction_grid.setHgap(10);
                    update_transaction_grid.setVgap(10);
                    update_transaction_grid.setPadding(new Insets(20));

                    update_transaction_grid.add(new Label("Date: "), 0, 0);
                    update_transaction_grid.add(update_Date, 1, 0);

                    update_transaction_grid.add(new Label("Type: "), 0,1);
                    update_transaction_grid.add(typeComboUpdate, 1, 1);

                    update_transaction_grid.add(new Label("Category: "), 0, 2);
                    update_transaction_grid.add(categoryComboUpdate, 1, 2);

                    update_transaction_grid.add(new Label("Description: "), 0, 3);
                    update_transaction_grid.add(descriptionUpdate, 1, 3);

                    update_transaction_grid.add(new Label("Amount: "), 0, 4);
                    update_transaction_grid.add(amount_update, 1, 4);

                    update_transaction_grid.add(new Label("Nature: "), 0, 5);
                    update_transaction_grid.add(nature_update, 1, 5);

                    Button updateButton = new Button("Update");
                    updateButton.setStyle(btn_styles);
                    updateButton.setStyle("-fx-background-color: #1f4037; -fx-text-fill: white;");
                    Button deleteButton = new Button("Delete");
                    deleteButton.setStyle(btn_styles);
                    deleteButton.setStyle("-fx-background-color: #D70652; -fx-text-fill: white;");
                    HBox butt_update = new HBox(10);
                    butt_update.setAlignment(Pos.CENTER);
                    butt_update.getChildren().addAll(updateButton, deleteButton);
                    update_transaction_grid.add(butt_update, 1, 6);

                    Stage stage = new Stage();
                    stage.setTitle("Update Transaction");
                    stage.setScene(new Scene(update_transaction_grid, 400, 280));
                    stage.show();

                    updateButton.setOnAction(f -> {
                        int isBigPurchase = nature_update.isSelected() ? 1 : 0;
                        Database.updateTransaction(
                            selected.getId(),
                            update_Date.getValue().toString(),
                            typeComboUpdate.getValue(),
                            categoryComboUpdate.getValue(),
                            descriptionUpdate.getText(),
                            Double.parseDouble(amount_update.getText()),
                            isBigPurchase
                        );

                        nature_update.setSelected(false);
                        refrechTable.run();
                        stage.close();
                    });

                    deleteButton.setOnAction(d -> {

                        Label deleteMessage = new Label("This transaction will be deleted");
                        Button dell_btn = new Button("Delete");
                        dell_btn.setStyle(btn_styles);
                        dell_btn.setStyle("-fx-background-color: #D70652; -fx-text-fill: white;");

                        VBox delete_grid = new VBox(10);
                        delete_grid.setAlignment(Pos.CENTER);
                        delete_grid.getChildren().addAll(deleteMessage, dell_btn);

                        Stage stageDelete = new Stage();
                        stageDelete.setTitle("Delete Transaction");
                        stageDelete.setScene(new Scene(delete_grid, 400, 100));
                        stageDelete.show();

                        dell_btn.setOnAction(a -> {
                            Database.deleteTrasnaction(
                                selected.getId()
                            );
                            refrechTable.run();
                            stageDelete.close();
                            stage.close();
                        });
                    });
                }
            }
        });

        content.getChildren().addAll(
            title,
            filter,
            topBar,
            transaction_table,
            money_stat
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}