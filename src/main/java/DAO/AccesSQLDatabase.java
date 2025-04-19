package DAO;

import java.sql.*;

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

    public boolean registerClient(String prenom, String nom, Date b, String email, String password) {
        String sql = "INSERT INTO Client (prenom, nom, birthdate, email, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, prenom);
            stmt.setString(2, nom);
            stmt.setDate(3, b);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("An error occurred. Could not register the client");
            e.printStackTrace();
            return false;
        }
    }


    public boolean LoginUserSucces(String email, String password) {
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
