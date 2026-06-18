package com.financify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
}