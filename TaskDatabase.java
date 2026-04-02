import java.sql.*;
import java.util.ArrayList;

public class TaskDatabase {

    // Database URL
    private static final String URL = "jdbc:sqlite:tasks.db";

    // Static block runs once when class loads
    static {
        createTable();
    }

    // Method to create table if it does not exist
    private static void createTable() {

        try (
            Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement()
        ) {

            String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "task TEXT NOT NULL"
                    + ")";

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert new task into database
    public static void insertTask(String task) {

        String sql = "INSERT INTO tasks(task) VALUES (?)";

        try (
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, task);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete selected task
    public static void deleteTask(String task) {

        String sql = "DELETE FROM tasks WHERE task = ?";

        try (
            Connection conn = DriverManager.getConnection(URL);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, task);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all tasks from database
    public static ArrayList<String> getTasks() {

        ArrayList<String> list = new ArrayList<>();

        String sql = "SELECT task FROM tasks";

        try (
            Connection conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {

                list.add(rs.getString("task"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}