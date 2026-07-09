package com.financify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.financify.models.Transactions;

public class Database {
    private static final String URL = "jdbc:sqlite:financify.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Db connection failed", e);
        }
    }

    public static void init() {
        String transactionTable = """
            CREATE TABLE IF NOT EXISTS transactions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                date TEXT,
                type TEXT,
                category TEXT,
                description TEXT,
                amount REAL
            );
        """;
        String netWorthtable = """
            CREATE TABLE IF NOT EXISTS net_worth (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                month TEXT,
                bank_balance,
                loans INTEGER,
                net_worth INTEGER
            )
        """;
        String goals_section = """
            CREATE TABLE IF NOT EXISTS goals_section (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                goal TEXT,
                target INTEGER,
                current INTEGER,
                remaining INTEGER,
                deadline TEXT
            )
        """;
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(transactionTable);
            stmt.execute(netWorthtable);
            stmt.execute(goals_section);
        } catch (SQLException e) {
            throw new RuntimeException("Table created failed", e);
        }
    }

    public static List<Transactions> getAllTransactions() {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.withDayOfMonth(1);
        LocalDate firstDayNextMonth = firstDay.plusMonths(1);

        String fetchTransactions = """
            SELECT * FROM transactions WHERE date >= ? AND date < ?
        """;
        
        List<Transactions> transactions = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(fetchTransactions)) {
            stmt.setString(1, firstDay.toString());
            stmt.setString(2, firstDayNextMonth.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String category = rs.getString("category");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                Transactions transaction = new Transactions(
                    date,
                    type,
                    category,
                    description,
                    amount
                );
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch transactions", e);
        }
    }
}