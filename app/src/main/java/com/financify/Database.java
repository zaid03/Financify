package com.financify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    //to add tables
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

    //to fetch all transactions
    public static List<Transactions> getAllTransactions() {
        LocalDate now = LocalDate.now();
        return getSomeTransactions(now.getYear(), now.getMonthValue());
    }

    //to fetch total spent for a months transactions
    public static Double getTotalSpent() {
        LocalDate now = LocalDate.now();
        return getTotalSpent(now.getYear(), now.getMonthValue());
    }

    public static Double getTotalSpent(Integer year, Integer month) {
        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate firstDayNextMonth = firstDay.plusMonths(1);

        String fetchTransactions = """
            SELECT SUM(amount) AS total_spent
            FROM transactions
            WHERE date >= ? AND date < ?
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(fetchTransactions)) {

            stmt.setString(1, firstDay.toString());
            stmt.setString(2, firstDayNextMonth.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total_spent");
            }

            return 0.0;

        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch total amount spent", e);
        }
    }

    //selecting all the years to add to the filter
    public static List<Integer> getAllYearsFilter() {
        String fetchYearsForFilter = """
            SELECT DISTINCT strftime('%Y', date) AS year FROM transactions ORDER BY year
        """;

        List<Integer> yearsToFilter = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(fetchYearsForFilter);) {
            while (rs.next()) {
                yearsToFilter.add(rs.getInt("year"));
            }
            return yearsToFilter;
        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch years to add to filter", e);
        }
    }

    //filtering transactions by year and month
    public static List<Transactions> getSomeTransactions(Integer year, Integer month) {

        LocalDate firstDay = LocalDate.of(year, month, 1);
        LocalDate firstDayNextMonth = firstDay.plusMonths(1);

        String fetchTransactions = """
            SELECT *
            FROM transactions
            WHERE date >= ? AND date < ?
        """;

        List<Transactions> transactions = new ArrayList<>();

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(fetchTransactions)) {

            stmt.setString(1, firstDay.toString());
            stmt.setString(2, firstDayNextMonth.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transactions(
                    rs.getString("date"),
                    rs.getString("type"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getDouble("amount")
                ));
            }

            return transactions;

        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch transactions", e);
        }
    }
}