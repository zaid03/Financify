package com.financify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.financify.models.GoalSummaryModel;
import com.financify.models.GoalsSection;
import com.financify.models.NetWorthModel;
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
            WHERE type = 'Expense' And
            date >= ? AND date < ?
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
                    rs.getInt("id"),
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

    //adding a transaction
    public static void addTransaction(String date, String type, String category, String description, Double amount) {
        String transaction = """
            INSERT INTO transactions (date, type, category, description, amount) VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(transaction)) {
                stmt.setString(1, date);
                stmt.setString(2, type);
                stmt.setString(3, category);
                stmt.setString(4, description);
                stmt.setDouble(5, amount);
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't add transaction", e);
        }
    }

    //updating an existing transaction
    public static void updateTransaction(Integer id, String date, String type, String category, String description, Double amount) {
        String updateTrans = """
            UPDATE transactions SET
            date = ?, type = ?, category = ?, description = ?, amount = ? 
            WHERE id = ?
        """;
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(updateTrans)){
                stmt.setString(1, date);
                stmt.setString(2, type);
                stmt.setString(3, category);
                stmt.setString(4, description);
                stmt.setDouble(5, amount);
                stmt.setInt(6, id);
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update transaction", e);
        }
    }

    //deleting a transaction
    public static void deleteTrasnaction(Integer id) {
        String deleteTras = """
            DELETE FROM transactions WHERE id = ?
        """;
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(deleteTras)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete transaction", e);
        }
    }

    //selecting and filtering in the net worth
    public static List<NetWorthModel> getNetWorth() {
        LocalDate nowYear = LocalDate.now();
        return getSomeNetWorth(nowYear.getYear());
    }

    public static List<NetWorthModel> getSomeNetWorth(Integer year) {

        String fetchNetWorth = """
            SELECT *
            FROM net_worth
            WHERE substr(month, 1, 4) = ?;
        """;

        List<NetWorthModel> NetWorth = new ArrayList<>();

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(fetchNetWorth)) {

            stmt.setString(1, String.valueOf(year));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                NetWorth.add(new NetWorthModel(
                    rs.getInt("id"),
                    rs.getString("month"),
                    rs.getDouble("bank_balance"),
                    rs.getInt("loans")
                ));
            }

            return NetWorth;

        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch Net worth", e);
        }
    }

    //selecting all the years to add to the filter of net worth
    public static List<Integer> getAllYearsToFilter() {
        String fetchYearsToFilter = """
           SELECT DISTINCT substr(month, 1, 4) AS year FROM net_worth ORDER BY month
        """;

        List<Integer> yearsToFilter = new ArrayList<>();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(fetchYearsToFilter);) {
            while (rs.next()) {
                yearsToFilter.add(rs.getInt("year"));
            }
            return yearsToFilter;
        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch years to add to filter of net worth", e);
        }
    }

    //adding monthly net worth
    public static void postNetWorth(String month, Double bank_balance, Integer loans) {
        String add_netWorth = """
            insert into net_worth (month, bank_balance, loans) values (?, ?, ?)
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(add_netWorth)){
            stmt.setString(1, month);
            stmt.setDouble(2, bank_balance);
            if (loans == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, loans);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't add to net worth", e);
        }
    }

    //update monthly net worth
    public static void updateNetWorth(Integer id, String month, Double bank_balance, Integer loans) {
        String update_netWorth = """
            UPDATE net_worth SET month = ?, bank_balance = ?, loans = ? WHERE id = ?
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(update_netWorth)){
            stmt.setString(1, month);
            stmt.setDouble(2, bank_balance);
            if (loans == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, loans);
            }
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update net worth", e);
        }
    }

    //delete a networth
    public static void deleteNetWorth(Integer id) {
        String dell_sql = """
            delete from net_worth where id = ?
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(dell_sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete net worth", e);
        }
    }

    //fetching goals
    public static List<GoalsSection> getGoals() {
        String fetch_sql = """
            SELECT * FROM goals_section
        """;

        List<GoalsSection> goals = new ArrayList<>();
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(fetch_sql);)  {
                while (rs.next()) {
                    goals.add(new GoalsSection(
                        rs.getInt("id"),
                        rs.getString("goal"),
                        rs.getInt("target"),
                        rs.getInt("current"),
                        rs.getString("deadline")
                    ));
                }

                return goals;
        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch goals", e);
        }
    }

    //fetching goals summary
    public static GoalSummaryModel fetchGoalsSummary() {
        String summary_sql = """
            SELECT
                SUM(target) AS totalTarget,
                SUM(current) AS totalCurrent,
                SUM(target - current) AS totalRemaining
            FROM goals_section;
        """;
        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(summary_sql);) {
                rs.next();
                return new GoalSummaryModel (
                    rs.getInt("totalTarget"),
                    rs.getInt("totalCurrent"),
                    rs.getInt("totalRemaining")
                );
        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch goals summary", e);
        }
    }

    //getting latest networth to calculate saving status for goals
    public static Double getNetWorthLatest(String month) {
        String nets = """
            SELECT SUM(bank_balance - loans) AS net_Worth FROM net_worth WHERE substr(month, 1, 7) = ?;
        """;
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(nets);){
                stmt.setString(1, month);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("net_Worth");
                }
                return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Can't fetch net worth for goals", e);
        }
    }

    //adding a goal
    public static void addGoal(String goal, Integer target, Integer current, String deadline) {
        String add_sql = """
            INSERT INTO goals_section (goal, target, current, deadline) VALUES (?, ?, ?, ?)
        """;
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(add_sql)){
                stmt.setString(1, goal);
                stmt.setInt(2, target);
                stmt.setInt(3, current);
                stmt.setString(4, deadline);
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't add goal", e);
        }
    }

    //updating a goal
    public static void updateGoal(Integer id, String goal, Integer target, Integer current, String deadline) {
        String update_sql = """
            UPDATE goals_section SET goal = ?, target = ?, current = ?, deadline = ? WHERE id = ?
        """;

        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(update_sql)) {
            stmt.setString(1, goal);
            stmt.setInt(2, target);
            stmt.setInt(3, current);
            stmt.setString(4, deadline);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't update goal", e);
        }
    }

    //deleting a goal
    public static void deleteGoal(Integer id) {
        String delete_sql = """
            DELETE FROM goals_section WHERE id = ?
        """;
        try (Connection conn = connect();
            PreparedStatement stmt = conn.prepareStatement(delete_sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't delete goal", e);
        }
    }
}