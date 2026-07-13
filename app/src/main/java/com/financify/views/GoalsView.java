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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableCell;
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


        content.getChildren().addAll(
            title,
            topBar
            
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}