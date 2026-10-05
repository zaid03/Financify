package com.financify.views;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.financify.Database;
import com.financify.models.GoalSummaryModel;
import com.financify.models.GoalsSection;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GoalsView extends VBox{
    public GoalsView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Saving goals");

        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #782170;
        """;
        title.setStyle(title_styles);

        Button add_button = new Button("Add a goal");
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
        add_button.setStyle(btn_styles);

        Label savings = new Label("Net worth after goals: ");
        savings.setStyle(words_styles);
        GoalSummaryModel stats = Database.fetchGoalsSummary();
        Double net_Worth = Database.getNetWorthLatest(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")));
        Integer total_target = stats.getTotalTarget();
        Double is_enouph = net_Worth - total_target;
        Label status = new Label(is_enouph.toString());
        String phrases_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #0B3040;
            -fx-fill: #0B3040;
        """;
        savings.setStyle(phrases_styles);
        status.setStyle(phrases_styles);
        
        setStatusColor(status, is_enouph);
        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER);
        filters.getChildren().addAll(savings, status);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(add_button);
        topBar.setCenter(filters);

        Label completedTitle = new Label("On-going goals");
        completedTitle.setStyle(phrases_styles);

        TableView<GoalsSection> goals_table = new TableView<>();
        TableColumn<GoalsSection, String> goalNameColumn = new TableColumn<>("Goal");
        TableColumn<GoalsSection, Integer> targetColumn = new TableColumn<>("Target");
        TableColumn<GoalsSection, Integer> currentColumn = new TableColumn<>("Current");
        TableColumn<GoalsSection, Integer> remainingColumn = new TableColumn<>("Remaining");
        TableColumn<GoalsSection, String> deadlineColumn = new TableColumn<>("Deadline");
        goalNameColumn.setCellValueFactory(new PropertyValueFactory<>("goal"));
        targetColumn.setCellValueFactory(new PropertyValueFactory<>("target"));
        targetColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer target, boolean empty) {
                super.updateItem(target, empty);
                if (empty || target == null) {
                    setText(null);
                } else {
                    setText(String.format("%d MAD", target));
                }
            }
        });
        currentColumn.setCellValueFactory(new PropertyValueFactory<>("current"));
        currentColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer current, boolean empty) {
                super.updateItem(current, empty);
                if (empty || current == null) {
                    setText(null);
                } else {
                    setText(String.format("%d MAD", current));
                }
            }
        });
        remainingColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        remainingColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer remaining, boolean empty) {
                super.updateItem(remaining, empty);
                if (empty || remaining == null) {
                    setText(null);
                } else {
                    setText(String.format("%d MAD", remaining));
                }
            }
        });
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        goals_table.getColumns().addAll(
            goalNameColumn,
            targetColumn,
            remainingColumn,
            currentColumn,
            deadlineColumn
        );

        List<GoalsSection> goals = Database.getGoals();
        goals_table.getItems().addAll(goals);
        goals_table.setMaxHeight(200);
        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """;
        goals_table.setStyle(table_style);
        goals_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        GoalSummaryModel total_stats = Database.fetchGoalsSummary();

        Integer total_saving_amount = total_stats.getTotalTarget();
        String total_saving_amount_string = String.valueOf(total_saving_amount);
        TextFlow total_saving = new TextFlow();
        Text total_saving_text = new Text("Total Savings Goal: ");
        Text total_saving_number = new Text(total_saving_amount + " MAD");
        total_saving_text.setStyle(phrases_styles);
        total_saving_number.setStyle(money_styles);
        total_saving.getChildren().addAll(total_saving_text, total_saving_number);

        Integer total_saved_amount = total_stats.getTotalCurrent();
        String total_saved_amount_string = String.valueOf(total_saved_amount);
        TextFlow total_saved = new TextFlow();
        Text total_saved_text = new Text("Currently Saved: ");
        Text total_saved_number = new Text(total_saved_amount + " MAD");
        total_saved_text.setStyle(phrases_styles);
        total_saved_number.setStyle(money_styles);
        total_saved.getChildren().addAll(total_saved_text, total_saved_number);

        Integer total_remaining_amount = total_stats.getTotalRemaining();
        String total_remaining_amount_string = String.valueOf(total_remaining_amount);
        TextFlow total_remaining = new TextFlow();
        Text total_remaining_text = new Text("Remaining to Save: ");
        Text total_remaining_number = new Text(total_remaining_amount_string + " MAD");
        total_remaining_text.setStyle(phrases_styles);
        total_remaining_number.setStyle(money_styles);
        total_remaining.getChildren().addAll(total_remaining_text, total_remaining_number);

        HBox total_totals = new HBox(15);
        total_totals.setAlignment(Pos.CENTER);
        total_totals.getChildren().addAll(total_saving, total_saved, total_remaining);

        Label uncompletedTitle = new Label("Goals history");
        uncompletedTitle.setStyle(phrases_styles);

        TableView<GoalsSection> uncompletedGoals = new TableView<>();
        TableColumn<GoalsSection, String> goalNameUncompletedColumn = new TableColumn<>("Goal");
        TableColumn<GoalsSection, Integer> targetUncompletedColumn = new TableColumn<>("Target");
        TableColumn<GoalsSection, Integer> currentUncompletedColumn = new TableColumn<>("Current");
        TableColumn<GoalsSection, Integer> remainingUncompletedColumn = new TableColumn<>("Remaining");
        TableColumn<GoalsSection, String> deadlineUncompletedColumn = new TableColumn<>("Deadline");
        TableColumn<GoalsSection, String> completionDate = new TableColumn<>("Completion date");
        goalNameUncompletedColumn.setCellValueFactory(new PropertyValueFactory<>("goal"));
        targetUncompletedColumn.setCellValueFactory(new PropertyValueFactory<>("target"));
        targetUncompletedColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer target, boolean empty) {
                super.updateItem(target, empty);
                if (empty || target == null) {
                    setText(null);
                } else {
                    setText(String.format("%d MAD", target));
                }
            }
        });
        currentUncompletedColumn.setCellValueFactory(new PropertyValueFactory<>("current"));
        currentUncompletedColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer current, boolean empty) {
                super.updateItem(current, empty);
                if (empty || current == null) {
                    setText(null);
                } else {
                    setText(String.format("%d MAD", current));
                }
            }
        });
        remainingUncompletedColumn.setCellValueFactory(new PropertyValueFactory<>("remaining"));
        remainingUncompletedColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer remaining, boolean empty) {
                super.updateItem(remaining, empty);
                if (empty || remaining == null) {
                    setText(null);
                } else {
                    setText(String.format("%d MAD", remaining));
                }
            }
        });
        deadlineUncompletedColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        completionDate.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
        uncompletedGoals.getColumns().addAll(
            goalNameUncompletedColumn,
            targetUncompletedColumn,
            remainingUncompletedColumn,
            currentUncompletedColumn,
            deadlineUncompletedColumn,
            completionDate
        );

        List<GoalsSection> Data = Database.getUncompletedGoals();
        uncompletedGoals.getItems().addAll(Data);
        uncompletedGoals.setMaxHeight(200);
        uncompletedGoals.setStyle(table_style);
        uncompletedGoals.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Runnable refreshContent = () -> {
            goals_table.getItems().setAll(Database.getGoals());
            uncompletedGoals.getItems().setAll(Database.getUncompletedGoals());

            GoalSummaryModel statsUpdate = Database.fetchGoalsSummary();
            Double net_WorthUpdate = Database.getNetWorthLatest(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")));
            Integer total_targetUpdate = statsUpdate.getTotalTarget();
            Double is_enouphUpdate = net_WorthUpdate - total_targetUpdate;
            status.setText(is_enouphUpdate.toString());

            setStatusColor(status, is_enouphUpdate);

            GoalSummaryModel total_statsUpdate = Database.fetchGoalsSummary();
            String total_saving_amount_string_second = String.valueOf(total_statsUpdate.getTotalTarget());
            total_saving_text.setText("Total Savings Goal: ");
            total_saving_number.setText(total_saving_amount_string_second + " MAD");

            String total_saved_amount_string_second = String.valueOf(total_statsUpdate.getTotalCurrent());
            total_saved_text.setText("Currently Saved: ");
            total_saved_number.setText(total_saved_amount_string_second + " MAD");

            String total_remaining_amount_string_second = String.valueOf(total_statsUpdate.getTotalRemaining());
            total_remaining_text.setText("Remaining to Save: ");
            total_remaining_number.setText(total_remaining_amount_string_second + " MAD");
        };

        //add goal grid
        TextField goal_name = new TextField();
        TextField target = new TextField();
        TextField current = new TextField();
        DatePicker add_Date = new DatePicker();
        add_button.setOnAction(e -> {
            GridPane add_goal_grid = new GridPane();
            add_goal_grid.setHgap(10);
            add_goal_grid.setVgap(10);
            add_goal_grid.setPadding(new Insets(20));

            add_goal_grid.add(new Label("Name"), 0, 0);
            add_goal_grid.add(goal_name, 1, 0);

            add_goal_grid.add(new Label("Target"), 0, 1);
            add_goal_grid.add(target, 1, 1);

            add_goal_grid.add(new Label("Current"), 0, 2);
            add_goal_grid.add(current, 1, 2);

            add_goal_grid.add(new Label("Date"), 0, 3);
            add_goal_grid.add(add_Date, 1, 3);

            Button addGoal = new Button("Add");
            addGoal.setStyle(btn_styles);
            addGoal.setAlignment(Pos.CENTER);
            add_goal_grid.add(addGoal, 1, 5);

            Stage stage = new Stage();
            stage.setTitle("Add goal");
            stage.setScene(new Scene(add_goal_grid, 400, 280));
            stage.show();

            addGoal.setOnAction(f -> {
                String deadlineToSend = add_Date.getValue().toString();
                Database.addGoal(
                    goal_name.getText(),
                    Integer.parseInt(target.getText()),
                    Integer.parseInt(current.getText()),
                    deadlineToSend
                );

                stage.hide();
                goal_name.clear();
                target.clear();
                current.clear();
                add_Date.setValue(null);
                refreshContent.run();
            });
        });

        //update and delete of an uncompleted goal
        TextField goal_nameUpdate = new TextField();
        TextField targetUpdate = new TextField();
        TextField currentUpdate = new TextField();
        DatePicker add_DateUpdate = new DatePicker();
        goals_table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                GoalsSection selected = goals_table.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    goal_nameUpdate.setText(selected.getGoal());
                    targetUpdate.setText(selected.getTarget().toString());
                    currentUpdate.setText(selected.getCurrent().toString());
                    add_DateUpdate.setValue(LocalDate.parse(selected.getDeadline()));

                    GridPane update_goals_grid = new GridPane();
                    update_goals_grid.setHgap(10);
                    update_goals_grid.setVgap(10);
                    update_goals_grid.setPadding(new Insets(20));

                    update_goals_grid.add(new Label("Name"), 0, 0);
                    update_goals_grid.add(goal_nameUpdate, 1, 0);

                    update_goals_grid.add(new Label("Target"), 0, 1);
                    update_goals_grid.add(targetUpdate, 1, 1);

                    update_goals_grid.add(new Label("Current"), 0, 2);
                    update_goals_grid.add(currentUpdate, 1, 2);

                    update_goals_grid.add(new Label("Date"), 0, 3);
                    update_goals_grid.add(add_DateUpdate, 1, 3);

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
                    update_goals_grid.add(butt_update, 1, 5);

                    Stage stage = new Stage();
                    stage.setTitle("Update goal");
                    stage.setScene(new Scene(update_goals_grid, 400, 280));
                    stage.show();

                    updateButton.setOnAction(f -> {
                        String deadlineToSend = add_DateUpdate.getValue().toString();
                        Database.updateGoal(
                            selected.getId(),
                            goal_nameUpdate.getText(),
                            Integer.parseInt(targetUpdate.getText()),
                            Integer.parseInt(currentUpdate.getText()),
                            deadlineToSend
                        );

                        stage.hide();
                        refreshContent.run();
                    });

                    CompleteButton.setOnAction(a -> {
                        Database.completeGoal(selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });

                    deleteButton.setOnAction(a -> {
                        Database.deleteGoal(selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });
                }
            }
        });

        //update and delete of a completed goal
        TextField goal_nameUncompleteUpdate = new TextField();
        TextField targetUncompleteUpdate = new TextField();
        TextField currentUncompleteUpdate = new TextField();
        DatePicker add_DateUncompleteUpdate = new DatePicker();
        uncompletedGoals.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                GoalsSection selected = uncompletedGoals.getSelectionModel().getSelectedItem();

                if (selected != null) {
                    goal_nameUncompleteUpdate.setText(selected.getGoal());
                    targetUncompleteUpdate.setText(selected.getTarget().toString());
                    currentUncompleteUpdate.setText(selected.getCurrent().toString());
                    add_DateUncompleteUpdate.setValue(LocalDate.parse(selected.getDeadline()));

                    GridPane update_goals_grid = new GridPane();
                    update_goals_grid.setHgap(10);
                    update_goals_grid.setVgap(10);
                    update_goals_grid.setPadding(new Insets(20));

                    update_goals_grid.add(new Label("Name"), 0, 0);
                    update_goals_grid.add(goal_nameUncompleteUpdate, 1, 0);

                    update_goals_grid.add(new Label("Target"), 0, 1);
                    update_goals_grid.add(targetUncompleteUpdate, 1, 1);

                    update_goals_grid.add(new Label("Current"), 0, 2);
                    update_goals_grid.add(currentUncompleteUpdate, 1, 2);

                    update_goals_grid.add(new Label("Date"), 0, 3);
                    update_goals_grid.add(add_DateUncompleteUpdate, 1, 3);

                    Button updateButton = new Button("Update");
                    updateButton.setStyle(btn_styles);
                    updateButton.setStyle("-fx-background-color: #1f4037; -fx-text-fill: white;");
                    Button UnompleteButton = new Button("Uncomplete");
                    UnompleteButton.setStyle(btn_styles);
                    UnompleteButton.setStyle("-fx-background-color: #156082; -fx-text-fill: white;");
                    Button deleteButton = new Button("Delete");
                    deleteButton.setStyle(btn_styles);
                    deleteButton.setStyle("-fx-background-color: #D70652; -fx-text-fill: white;");
                    HBox butt_update = new HBox(10);
                    butt_update.setAlignment(Pos.CENTER);
                    butt_update.getChildren().addAll(updateButton, UnompleteButton, deleteButton);
                    update_goals_grid.add(butt_update, 1, 5);

                    Stage stage = new Stage();
                    stage.setTitle("Update goal");
                    stage.setScene(new Scene(update_goals_grid, 400, 280));
                    stage.show();

                    updateButton.setOnAction(f -> {
                        String deadlineToSend = add_DateUncompleteUpdate.getValue().toString();
                        Database.updateGoal(
                            selected.getId(),
                            goal_nameUncompleteUpdate.getText(),
                            Integer.parseInt(targetUncompleteUpdate.getText()),
                            Integer.parseInt(currentUncompleteUpdate.getText()),
                            deadlineToSend
                        );

                        stage.hide();
                        refreshContent.run();
                    });

                    UnompleteButton.setOnAction(a -> {
                        Database.uncompleteGoal(selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });

                    deleteButton.setOnAction(a -> {
                        Database.deleteGoal(selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });
                }
            }
        });

        content.getChildren().addAll(
            title,
            topBar,
            completedTitle,
            goals_table,
            uncompletedTitle,
            uncompletedGoals,
            total_totals
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }

    String positive_text = """
        -fx-font-size: 16px;
        -fx-font-weight: bold;
        -fx-text-fill: green;
    """;
    String negative_text = """
        -fx-font-size: 16px;
        -fx-font-weight: bold;
        -fx-text-fill: red;
    """;

    private void setStatusColor(Label label, double value) {
        if (value < 0) {
            label.setStyle(negative_text);
        } else {
            label.setStyle(positive_text);
        }
    }
}