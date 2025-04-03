package DAO;

import Modele.Administrator;
import Modele.User;

import java.sql.*;
import java.util.ArrayList;

public class AdministratorDAO implements AdministratorInterface {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    @Override
    public ArrayList<Administrator> getAllAdministrators() {
        ArrayList<Administrator> listAdmins = new ArrayList<>();
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM ADMIN");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                Administrator admin = new Administrator(nom, prenom, password, email, id);

                listAdmins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'administrateurs impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }

        return listAdmins;
    }

    @Override
    public void addAdmin(Administrator admin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("insert into Admin(nom, prenom, email, password) values(?,?,?,?)");
            preparedStatement.setString(1, admin.getLastName());
            preparedStatement.setString(2, admin.getFirstName());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout d'administrateur impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    @Override
    public void deleteAdmin(Administrator admin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("delete from Admin where ID = ?");
            preparedStatement.setInt(1, admin.getUserID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression d'administrateur impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    @Override
    public Administrator findAdmin(Administrator admin) {
        Administrator adminFound = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("select * from Admin where ID = ?");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                if (admin.getId() == id) {
                    adminFound = new Administrator(nom, prenom, password, email, id);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Administrateur introuvable");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return adminFound;
    }

    @Override
    public Administrator editAdmin(Administrator admin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("update Admin set nom=?, prenom=?, email=?, password=? where ID=?");
            preparedStatement.setString(1, admin.getLastName());
            preparedStatement.setString(2, admin.getFirstName());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setString(4, admin.getPassword());
            preparedStatement.setInt(5, admin.getUserID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de modification de Administrateur");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return admin;
    }
}
