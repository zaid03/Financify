package com.financify.views;

import java.time.LocalDate;
import java.util.List;

import javax.swing.table.TableColumnModel;

import com.financify.Database;
import com.financify.models.NetWorthModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
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

public class NetWorthView extends VBox{
    public NetWorthView() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Net worth");
        String title_styles = """
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: #6a726a;
        """;
        title.setStyle(title_styles);
        Label filter = new Label("Change the year to display its net tracking history");

        ComboBox<Integer> yearComboBox = new ComboBox<>();
        yearComboBox.getItems().addAll(Database.getAllYearsToFilter());
        yearComboBox.setValue(LocalDate.now().getYear());
        Button add_button = new Button("Add Date");
        String btn_styles = """
            -fx-background-color: #abbaab;
            -fx-text-fill: #000000;
            -fx-font-size: 12px;
            -fx-padding: 4 8;
            -fx-background-radius: 4;
        """;
        add_button.setStyle(btn_styles);
        add_button.setStyle("-fx-background-color: #009ffc; -fx-text-fill: white;");

        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER);
        filters.getChildren().addAll(add_button, yearComboBox);
        BorderPane topBar = new BorderPane();
        topBar.setLeft(add_button);
        topBar.setCenter(filters);

        TableView<NetWorthModel> netWorth_table = new TableView<>();
        TableColumn<NetWorthModel, String> monthColumn = new TableColumn<>("Month");
        TableColumn<NetWorthModel, Double> bankBalanceColumn = new TableColumn<>("Bank Balance");
        TableColumn<NetWorthModel, Integer> loanColumn = new TableColumn<>("Loans");
        TableColumn<NetWorthModel, Double> netWorthColumn = new TableColumn<>("Net Worth");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        bankBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("bankBalance"));
        bankBalanceColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double bankBalance, boolean empty) {
                super.updateItem(bankBalance, empty);
                if (empty || bankBalance == null) {
                setText(null);
                } else {
                    setText(String.format("%.2f MAD", bankBalance));
                }
            }
        });
        loanColumn.setCellValueFactory(new PropertyValueFactory<>("loans"));
        loanColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer loans, boolean empty) {
            super.updateItem(loans, empty);
            if (empty || loans == null) {
                setText(null);
            } else {
                setText(String.format("%d MAD", loans));
            }
            }
        });
        netWorthColumn.setCellValueFactory(new PropertyValueFactory<>("netWorth"));
        netWorthColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double netWorth, boolean empty) {
                super.updateItem(netWorth, empty);
                if (empty || netWorth == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f MAD", netWorth));
                }
            }
        });
        netWorth_table.getColumns().addAll(
            monthColumn,
            bankBalanceColumn,
            loanColumn,
            netWorthColumn
        );
        String table_style = """
            -fx-background-color: white;
            -fx-border-color: #D1D5DB;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
        """;
        netWorth_table.setStyle(table_style);
        netWorth_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        List<NetWorthModel> netWorth = Database.getNetWorth();
        netWorth_table.getItems().addAll(netWorth);
        netWorth_table.setMinHeight(350);

        yearComboBox.setOnAction(e -> {
            netWorth_table.getItems().setAll(Database.getSomeNetWorth(yearComboBox.getValue()));
        });

        content.getChildren().addAll(
            title,
            filter,
            topBar,
            netWorth_table
            
        );
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        getChildren().add(scrollPane);
    }
}