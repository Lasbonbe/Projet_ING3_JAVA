package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccesSQLDatabase {
    private Connection sql_connexion = null;
    private String url = "jdbc:mysql://72.145.14.178:3306/PROJET?useSSL=false";
    private String user = "pilote";
    private String pass = "rafale";

    public Connection getConnection() {
        if (sql_connexion == null) {
            try {
                sql_connexion = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                System.out.println("An error occurred. Could not connect to the database");
                e.printStackTrace();
            }
        }
        return sql_connexion;
    }

    public void dropTable(String tableName) {
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred. Could not drop the table");
            e.printStackTrace();
        }
    }

    public void createTable(String tableName, String columns) {
        String sql = "CREATE TABLE " + tableName + " (" + columns + ")";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred. Could not create the table");
            e.printStackTrace();
        }
    }

    public void fillTable(String tableName, String columns, String values) {
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occurred. Could not insert the values");
            e.printStackTrace();
        }
    }

    public boolean LoginSuccess(String email, String password) {
        String sql = "SELECT * FROM Client WHERE email = ? AND password = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("An error occurred. Could not check login");
            e.printStackTrace();
            return false;
        }
    }

}
