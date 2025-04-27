package DAO;

import Modele.PanierItem;
import Modele.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe PanierDAO qui gère les opérations liées au panier dans la base de données.
 * Elle permet d'ajouter, supprimer et récupérer des éléments du panier.
 */
public class PanierDAO {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();
    private int idPanier;
    private ScheduleDAO scheduleDAO = new ScheduleDAO();

    /**
     * Méthode qui permet d'ajouter une réservation au panier.
     * Elle crée un panier si aucun n'existe pour le client.
     * Ensuite, elle ajoute l'élément au panier et met à jour le prix total.
     * Enfin, elle réserve temporairement les billets pour la session.
     *
     * @param clientID L'identifiant du client.
     * @param schedule La session réservée.
     * @param nbBillets Le nombre de billets réservés.
     * @param prix Le prix total de la réservation.
     */
    public void addReservationPanier(int clientID, Schedule schedule, int nbBillets, double prix) {
        int panierID = getPanierId(clientID);
        if (panierID == -1) {
            createPanier(clientID);
            panierID = idPanier;
        }

        if (panierID <= 0) {
            System.err.println("Échec de la création du panier. Opération annulée.");
            return;
        }

        addElementPanier(panierID, schedule, nbBillets, prix);
        updatePrixPanier(panierID);
        scheduleDAO.tempReservation(schedule, nbBillets);
    }

    /**
     * Méthode qui permet de récupérer l'identifiant du panier actif pour un client donné.
     *
     * @param clientID L'identifiant du client.
     * @return L'identifiant du panier actif ou -1 si aucun panier n'est trouvé.
     */
    public int getPanierId(int clientID) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT ID FROM Panier WHERE client_ID = ? AND statut = 'actif'");
            preparedStatement.setInt(1, clientID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération de l'identifiant du client impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Fermeture des ressources impossible");
            }
        }
        return -1;
    }

    /**
     * Méthode qui permet de créer un panier pour un client donné.
     *
     * @param clientID L'identifiant du client.
     */
    private void createPanier(int clientID) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Panier(client_ID, statut, prix) VALUES (?, 'actif', 0)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientID);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    idPanier = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de création de panier");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Fermeture des ressources impossible");
            }
        }
    }

    /**
     * Méthode qui permet d'ajouter un élément au panier.
     *
     * @param panier_ID L'identifiant du panier.
     * @param schedule La session réservée.
     * @param nbBillets Le nombre de billets réservés.
     * @param prix Le prix total de la réservation.
     */
    private void addElementPanier(int panier_ID, Schedule schedule, int nbBillets, double prix ) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO PanierElement(panier_ID, schedule_ID, nbBillets, prix) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, panier_ID);
            System.out.println("schedule ID = " + schedule.getIdSchedule());
            preparedStatement.setInt(2, schedule.getIdSchedule());
            preparedStatement.setInt(3, nbBillets);
            preparedStatement.setDouble(4, prix);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur d'ajout d'élément au panier");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Fermeture des ressources impossible");
            }
        }
    }

    /**
     * Méthode qui permet de mettre à jour un panier.
     *
     * @param panierID L'identifiant du panier.
     */
    private void updatePrixPanier(int panierID) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Panier SET prix = (SELECT SUM(prix) FROM PanierElement WHERE panier_ID = ?) WHERE ID = ?");
            preparedStatement.setInt(1, panierID);
            preparedStatement.setInt(2, panierID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Mise à jour du prix total du panier impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Fermeture des ressources impossible");
            }
        }
    }

    /**
     * Méthode qui permet de récupérer tous les éléments du panier pour un client donné.
     *
     * @param clientID L'identifiant du client.
     * @return Une liste d'objets PanierItem représentant les éléments du panier.
     */
    public ArrayList<PanierItem> getAllPanier(int clientID) {
        ArrayList<PanierItem> panierItems = new ArrayList<PanierItem>();
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT pe.* FROM PanierElement pe JOIN Panier ON pe.panier_ID = Panier.ID WHERE Panier.client_ID = ? AND Panier.statut = 'actif'");
            preparedStatement.setInt(1, clientID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int prix = resultSet.getInt("prix");
                int nbBillets = resultSet.getInt("nbBillets");
                int scheduleID = resultSet.getInt("schedule_ID");
                panierItems.add(new PanierItem(id, scheduleID, nbBillets, prix));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération des éléments du panier impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Fermeture des ressources impossible");
            }
        }
        return panierItems;
    }

    /**
     * Méthode qui permet de supprimer un élément du panier.
     *
     * @param scheduleID L'identifiant de la session.
     * @param panierID L'identifiant du panier.
     */
    public void supprimerElementPanier(int scheduleID, int panierID) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT nbBillets FROM PanierElement WHERE schedule_ID = ? AND panier_ID = ?");
            preparedStatement.setInt(1, scheduleID);
            preparedStatement.setInt(2, panierID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int nbBillets = resultSet.getInt("nbBillets");
                scheduleDAO.deleteTempReservation(scheduleID, nbBillets);

                preparedStatement = connection.prepareStatement("DELETE FROM PanierElement WHERE schedule_ID = ? AND panier_ID = ?");
                preparedStatement.setInt(1, scheduleID);
                preparedStatement.setInt(2, panierID);
                preparedStatement.executeUpdate();
                updatePrixPanier(panierID);
            } else {
                System.out.println("Aucun element trouvé dans le panier");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Suppression de l'élément du panier impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Fermeture des ressources impossible");
            }
        }
    }

    /**
     * Méthode qui permet de récupérer l'identifiant de la session à partir de l'identifiant de l'élément du panier.
     *
     * @param panierElementID L'identifiant de l'élément du panier.
     * @return L'identifiant de la session ou -1 si aucun identifiant n'est trouvé.
     */
    public int getScheduleIdFromPanierElement(int panierElementID) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT PanierElement.schedule_ID FROM PanierElement WHERE ID = ?");
            preparedStatement.setInt(1, panierElementID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("schedule_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Récupération de l'identifiant de la sessions impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Fermeture des ressources impossible");
            }
        }
        return -1;
    }

    /**
     * Méthode qui permet de payer le panier d'un client.
     * Elle transfère les éléments du panier vers les réservations et met à jour le statut du panier.
     *
     * @param clientID L'identifiant du client.
     */
    public void payerPanier(int clientID) {
        Connection connection = sqlDatabase.getConnection();;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        try {
            int panierID = getPanierId(clientID);
            if (panierID == -1) {
                System.err.println("Aucun panier actif pour ce client");
                return;
            }
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM PanierElement WHERE panier_ID = ?");
            preparedStatement.setInt(1, panierID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int scheduleID = resultSet.getInt("schedule_ID");
                int nbBillets = resultSet.getInt("nbBillets");
                double prix = resultSet.getDouble("prix");
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDAO.addReservation(clientID, scheduleID, scheduleDAO.getScheduleDateById(scheduleID), nbBillets, prix, panierID);
            }
            preparedStatement = connection.prepareStatement("UPDATE Panier SET statut = 'Payé' WHERE ID = ?");
            preparedStatement.setInt(1, panierID);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du transfert du panier vers les réservations");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Fermeture des ressources impossible");
            }
        }

    }
}
