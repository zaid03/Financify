package com.financify.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.financify.Database;
import com.financify.models.Loans;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

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

        //add loan grid
        TextField name_add = new TextField();
        TextField descr_add = new TextField();
        TextField source_add = new TextField();
        TextField amount_add = new TextField();
        TextField remaining_add = new TextField();
        TextField moothly_add = new TextField();
        DatePicker start_date = new DatePicker();
        DatePicker due_date = new DatePicker();
        add_button.setOnAction(e -> {
            GridPane add_loan_grid = new GridPane();
            add_loan_grid.setHgap(10);
            add_loan_grid.setVgap(10);
            add_loan_grid.setPadding(new Insets(20));

            add_loan_grid.add(new Label("Name"), 0, 0);
            add_loan_grid.add(name_add, 1, 0);

            add_loan_grid.add(new Label("Description"), 0, 1);
            add_loan_grid.add(descr_add, 1, 1);

            add_loan_grid.add(new Label("Source"), 0, 2);
            add_loan_grid.add(source_add, 1, 2);

            add_loan_grid.add(new Label("Amount"), 0, 3);
            add_loan_grid.add(amount_add, 1, 3);
            
            add_loan_grid.add(new Label("Remaining"), 0, 4);
            add_loan_grid.add(remaining_add, 1, 4);

            add_loan_grid.add(new Label("Monthly"), 0, 5);
            add_loan_grid.add(moothly_add, 1, 5);

            add_loan_grid.add(new Label("Start date"), 0, 6);
            add_loan_grid.add(start_date, 1, 6);

            add_loan_grid.add(new Label("Due date"), 0, 7);
            add_loan_grid.add(due_date, 1, 7);

            Button addGoal = new Button("Add loan");
            addGoal.setStyle(btn_styles);
            addGoal.setAlignment(Pos.CENTER);
            add_loan_grid.add(addGoal, 1, 8);

            Stage stage = new Stage();
            stage.setTitle("Add Loan");
            stage.setScene(new Scene(add_loan_grid, 400, 350));
            stage.show();


            addGoal.setOnAction(f -> {
                String startDate = start_date.getValue().toString();
                String deadlineToSend = due_date.getValue().toString();
                Database.addLoan(
                    name_add.getText(),
                    descr_add.getText(),
                    source_add.getText(),
                    Double.parseDouble(amount_add.getText()),
                    Double.parseDouble(remaining_add.getText()),
                    Double.parseDouble(moothly_add.getText()),
                    startDate,
                    deadlineToSend
                );

                stage.hide();
                name_add.clear();
                descr_add.clear();
                source_add.clear();
                amount_add.clear();
                remaining_add.clear();
                moothly_add.clear();
                start_date.setValue(null);
                due_date.setValue(null);
                refreshContent.run();
            });
        });

        //update and delete and status on uncompleted loans
        TextField name_add_update = new TextField();
        TextField descr_add_update = new TextField();
        TextField source_add_update = new TextField();
        TextField amount_add_update = new TextField();
        TextField remaining_add_update = new TextField();
        TextField moothly_add_update = new TextField();
        DatePicker start_date_update = new DatePicker();
        DatePicker due_date_update = new DatePicker();
        loans_table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Loans selected = loans_table.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    name_add_update.setText(selected.getName());
                    descr_add_update.setText(selected.getDescription());
                    source_add_update.setText(selected.getSource());
                    amount_add_update.setText(String.valueOf(selected.getAmount()));
                    remaining_add_update.setText(String.valueOf(selected.getRemaining()));
                    moothly_add_update.setText(String.valueOf(selected.getMonthly()));
                    start_date_update.setValue(LocalDate.parse(selected.getStart_date()));
                    due_date_update.setValue(LocalDate.parse(selected.getDue_date()));

                    GridPane update_loan_grid = new GridPane();
                    update_loan_grid.setHgap(10);
                    update_loan_grid.setVgap(10);
                    update_loan_grid.setPadding(new Insets(20));

                    update_loan_grid.add(new Label("Name"), 0, 0);
                    update_loan_grid.add(name_add_update, 1, 0);

                    update_loan_grid.add(new Label("Description"), 0, 1);
                    update_loan_grid.add(descr_add_update, 1, 1);

                    update_loan_grid.add(new Label("Source"), 0, 2);
                    update_loan_grid.add(source_add_update, 1, 2);

                    update_loan_grid.add(new Label("Amount"), 0, 3);
                    update_loan_grid.add(amount_add_update, 1, 3);
                    
                    update_loan_grid.add(new Label("Remaining"), 0, 4);
                    update_loan_grid.add(remaining_add_update, 1, 4);

                    update_loan_grid.add(new Label("Monthly"), 0, 5);
                    update_loan_grid.add(moothly_add_update, 1, 5);

                    update_loan_grid.add(new Label("Start date"), 0, 6);
                    update_loan_grid.add(start_date_update, 1, 6);

                    update_loan_grid.add(new Label("Due date"), 0, 7);
                    update_loan_grid.add(due_date_update, 1, 7);


                    Button updateButton = new Button("Update");
                    updateButton.setStyle(btn_styles);
                    updateButton.setStyle("-fx-background-color: #1f4037; -fx-text-fill: white;");
                    Button CompleteButton = new Button("Completed");
                    CompleteButton.setStyle(btn_styles);
                    CompleteButton.setStyle("-fx-background-color: #156082; -fx-text-fill: white;");
                    Button deleteButton = new Button("Delete");
                    deleteButton.setStyle(btn_styles);
                    deleteButton.setStyle("-fx-background-color: #D70652; -fx-text-fill: white;");
                    HBox butt_update = new HBox(10);
                    butt_update.setAlignment(Pos.CENTER);
                    butt_update.getChildren().addAll(updateButton, CompleteButton, deleteButton);
                    update_loan_grid.add(butt_update, 1, 8);

                    Stage stage = new Stage();
                    stage.setTitle("Update loan");
                    stage.setScene(new Scene(update_loan_grid, 400, 400));
                    stage.show();

                    updateButton.setOnAction(f -> {
                        String startDate = start_date_update.getValue().toString();
                        String deadlineToSend = due_date_update.getValue().toString();
                        Database.updateLoan(
                            selected.getId(),
                            name_add_update.getText(),
                            descr_add_update.getText(),
                            source_add_update.getText(),
                            Double.parseDouble(amount_add_update.getText()),
                            Double.parseDouble(remaining_add_update.getText()),
                            Double.parseDouble(moothly_add_update.getText()),
                            startDate,
                            deadlineToSend
                        );

                        name_add_update.clear();
                        descr_add_update.clear();
                        source_add_update.clear();
                        amount_add_update.clear();
                        remaining_add_update.clear();
                        moothly_add_update.clear();
                        start_date.setValue(null);
                        due_date.setValue(null);
                        refreshContent.run();
                        stage.hide();
                        refreshContent.run();
                    });

                    CompleteButton.setOnAction(a -> {
                        Database.changeStatusLoan(1, selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });

                    deleteButton.setOnAction(a -> {
                        Database.deleteLoan(selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });
                }
            }
        });

        //update and delete and status on completed loans
        TextField name_add_update_completed = new TextField();
        TextField descr_add_update_completed = new TextField();
        TextField source_add_update_completed = new TextField();
        TextField amount_add_update_completed = new TextField();
        TextField remaining_add_update_completed = new TextField();
        TextField moothly_add_update_completed = new TextField();
        DatePicker start_date_update_completed = new DatePicker();
        DatePicker due_date_update_completed = new DatePicker();
        completedLoans_table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Loans selected = completedLoans_table.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    name_add_update_completed.setText(selected.getName());
                    descr_add_update_completed.setText(selected.getDescription());
                    source_add_update_completed.setText(selected.getSource());
                    amount_add_update_completed.setText(String.valueOf(selected.getAmount()));
                    remaining_add_update_completed.setText(String.valueOf(selected.getRemaining()));
                    moothly_add_update_completed.setText(String.valueOf(selected.getMonthly()));
                    start_date_update_completed.setValue(LocalDate.parse(selected.getStart_date()));
                    due_date_update_completed.setValue(LocalDate.parse(selected.getDue_date()));

                    GridPane update_loan_grid = new GridPane();
                    update_loan_grid.setHgap(10);
                    update_loan_grid.setVgap(10);
                    update_loan_grid.setPadding(new Insets(20));

                    update_loan_grid.add(new Label("Name"), 0, 0);
                    update_loan_grid.add(name_add_update_completed, 1, 0);

                    update_loan_grid.add(new Label("Description"), 0, 1);
                    update_loan_grid.add(descr_add_update_completed, 1, 1);

                    update_loan_grid.add(new Label("Source"), 0, 2);
                    update_loan_grid.add(source_add_update_completed, 1, 2);

                    update_loan_grid.add(new Label("Amount"), 0, 3);
                    update_loan_grid.add(amount_add_update_completed, 1, 3);
                    
                    update_loan_grid.add(new Label("Remaining"), 0, 4);
                    update_loan_grid.add(remaining_add_update_completed, 1, 4);

                    update_loan_grid.add(new Label("Monthly"), 0, 5);
                    update_loan_grid.add(moothly_add_update_completed, 1, 5);

                    update_loan_grid.add(new Label("Start date"), 0, 6);
                    update_loan_grid.add(start_date_update_completed, 1, 6);

                    update_loan_grid.add(new Label("Due date"), 0, 7);
                    update_loan_grid.add(due_date_update_completed, 1, 7);


                    Button updateButton = new Button("Update");
                    updateButton.setStyle(btn_styles);
                    updateButton.setStyle("-fx-background-color: #1f4037; -fx-text-fill: white;");
                    Button UncmpleteButton = new Button("Uncomplete");
                    UncmpleteButton.setStyle(btn_styles);
                    UncmpleteButton.setStyle("-fx-background-color: #156082; -fx-text-fill: white;");
                    Button deleteButton = new Button("Delete");
                    deleteButton.setStyle(btn_styles);
                    deleteButton.setStyle("-fx-background-color: #D70652; -fx-text-fill: white;");
                    HBox butt_update = new HBox(10);
                    butt_update.setAlignment(Pos.CENTER);
                    butt_update.getChildren().addAll(updateButton, UncmpleteButton, deleteButton);
                    update_loan_grid.add(butt_update, 1, 8);

                    Stage stage = new Stage();
                    stage.setTitle("Update Loan");
                    stage.setScene(new Scene(update_loan_grid, 400, 400));
                    stage.show();

                    updateButton.setOnAction(f -> {
                        String startDate = start_date_update_completed.getValue().toString();
                        String deadlineToSend = due_date_update_completed.getValue().toString();
                        Database.updateLoan(
                            selected.getId(),
                            name_add_update_completed.getText(),
                            descr_add_update_completed.getText(),
                            source_add_update_completed.getText(),
                            Double.parseDouble(amount_add_update_completed.getText()),
                            Double.parseDouble(remaining_add_update_completed.getText()),
                            Double.parseDouble(moothly_add_update_completed.getText()),
                            startDate,
                            deadlineToSend
                        );

                        name_add_update_completed.clear();
                        descr_add_update_completed.clear();
                        source_add_update_completed.clear();
                        amount_add_update_completed.clear();
                        remaining_add_update_completed.clear();
                        moothly_add_update_completed.clear();
                        start_date.setValue(null);
                        due_date.setValue(null);
                        refreshContent.run();
                        stage.hide();
                        refreshContent.run();
                    });

                    UncmpleteButton.setOnAction(a -> {
                        Database.changeStatusLoan(0, selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });

                    deleteButton.setOnAction(a -> {
                        Database.deleteLoan(selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });
                }
            }
        });

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