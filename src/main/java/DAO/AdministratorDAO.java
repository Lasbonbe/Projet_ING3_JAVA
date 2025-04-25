package DAO;

import Modele.Administrator;
import Modele.Session;
import Modele.User;

import java.sql.*;
import java.util.ArrayList;

public class AdministratorDAO {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    public static Administrator findAdminByEmail(String email) {
        Administrator adminFound = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = new AccesSQLDatabase().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Admin WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");

                adminFound = new Administrator(id, nom, prenom, password, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Administrateur introuvable");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return adminFound;

    }


    public boolean loginAdmin(String email, String password) {
        boolean isValid = false;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Admin WHERE email = ? AND password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValid = true;
                User user = new Administrator(resultSet.getInt("ID"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("password"), resultSet.getString("email"));
                Session.setUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion administrateur");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return isValid;
    }

    public ArrayList<Administrator> getAllAdministrators() {
        ArrayList<Administrator> listAdmins = new ArrayList<>();
        Connection connection;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM Admin");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                Administrator admin = new Administrator(id, nom, prenom, password, email);

                listAdmins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'administrateurs impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listAdmins;
    }

    public void addAdmin(Administrator admin) {
        Connection connection;
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
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    public void deleteAdmin(Administrator admin) {
        Connection connection;
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
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }


    public Administrator findAdmin(int adminID) {
        Administrator adminFound = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Admin where ID = ?");
            preparedStatement.setInt(1, adminID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                if (adminID == id) {
                    adminFound = new Administrator(id, nom, prenom, password, email);
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
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return adminFound;
    }

    public Administrator editAdmin(Administrator admin) {
        Connection connection;
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
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return admin;
    }
}
