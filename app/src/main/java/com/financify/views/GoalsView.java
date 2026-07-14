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
import javafx.stage.Stage;

public class GoalsView extends VBox{
    public GoalsView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Goals and objectives");

        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #6a726a;
        """;
        title.setStyle(title_styles);

        Button add_button = new Button("Add a goal");
        String btn_styles = """
            -fx-background-color: #abbaab;
            -fx-text-fill: #000000;
            -fx-font-size: 12px;
            -fx-padding: 4 8;
            -fx-background-radius: 4;
        """;
        add_button.setStyle(btn_styles);
        add_button.setStyle("-fx-background-color: #009ffc; -fx-text-fill: white;");

        Label savings = new Label("Savings status");
        GoalSummaryModel stats = Database.fetchGoalsSummary();
        Double net_Worth = Database.getNetWorthLatest(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
        Integer total_target = stats.getTotalTarget();
        Double is_enouph = net_Worth - total_target;
        Label status = new Label(is_enouph.toString());
        String phrases_styles = """
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-text-fill: #000000;
        """;
        savings.setStyle(phrases_styles);
        status.setStyle(phrases_styles);

        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER);
        filters.getChildren().addAll(savings, status);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(add_button);
        topBar.setCenter(filters);

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
        goals_table.setMinHeight(350);
        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """;
        goals_table.setStyle(table_style);
        goals_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        Runnable refreshContent = () -> {
            goals_table.getItems().setAll(Database.getGoals());

            GoalSummaryModel statsUpdate = Database.fetchGoalsSummary();
            Double net_WorthUpdate = Database.getNetWorthLatest(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM")));
            Integer total_targetUpdate = statsUpdate.getTotalTarget();
            Double is_enouphUpdate = net_WorthUpdate - total_targetUpdate;
            status.setText(is_enouphUpdate.toString());
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
            addGoal.setStyle("-fx-background-color: #009ffc; -fx-text-fill: white;");
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

        //update and delete of a goal
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
                    Button deleteButton = new Button("Delete");
                    deleteButton.setStyle(btn_styles);
                    deleteButton.setStyle("-fx-background-color: #D70652; -fx-text-fill: white;");
                    HBox butt_update = new HBox(10);
                    butt_update.setAlignment(Pos.CENTER);
                    butt_update.getChildren().addAll(updateButton, deleteButton);
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

                    deleteButton.setOnAction(a -> {
                        Database.deleteGoal(selected.getId());

                        stage.hide();
                        refreshContent.run();
                    });
                }
            }
        });

        GoalSummaryModel total_stats = Database.fetchGoalsSummary();
        Integer total_saving_amount = total_stats.getTotalTarget();
        Integer total_saved_amount = total_stats.getTotalCurrent();
        Integer total_remaining_amount = total_stats.getTotalRemaining();
        Label total_saving = new Label("Total Savings Goal: " + total_saving_amount + " MAD");
        Label total_saved = new Label("Currently Saved: " + total_saved_amount + " MAD");
        Label total_remaining = new Label("Remaining to Save: " + total_remaining_amount + " MAD");
        total_saving.setStyle(phrases_styles);
        total_saved.setStyle(phrases_styles);
        total_remaining.setStyle(phrases_styles);
        VBox total_totals = new VBox();
        total_totals.setAlignment(Pos.CENTER);
        total_totals.getChildren().addAll(total_saving, total_saved, total_remaining);

        content.getChildren().addAll(
            title,
            topBar,
            goals_table,
            total_totals
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}