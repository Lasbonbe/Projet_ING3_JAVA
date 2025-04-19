package DAO;

import Modele.Discount;
import Modele.Order;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;



public class OrderDAO  {
    private AccesSQLDatabase sqlDatabase = new AccesSQLDatabase();

    public ArrayList<Order> getAllOrder() {
        ArrayList<Order> listOrders = new ArrayList<>();
        Connection connection;
        Statement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.createStatement();
            resultSet = preparedStatement.executeQuery("SELECT * FROM Order");

            while (resultSet.next()) {
                int id = resultSet.getInt("OrderID");
                int clientId = resultSet.getInt("ClientID");
                float amount = resultSet.getFloat("amount");
                float rawDiscount = resultSet.getFloat("discount");
                LocalDate date = resultSet.getDate("orderDate").toLocalDate();

                Discount discount = new Discount(0, rawDiscount, null);

                Order order = new Order(id, clientId, amount, date, discount);

                listOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de commandes impossible");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return listOrders;
    }

    public void addOrder(Order order) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Order(OrderID, ClientID, amount, discount, orderDate) VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.setInt(2, order.getClientID());
            preparedStatement.setFloat(3, order.getAmount());
            preparedStatement.setFloat(4, order.rawDiscount());
            //Date date = date.valueOf(order.getOrderDate());                       //A REPARER
            //preparedStatement.setDate(5, (Date) order.getOrderDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du Order impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    public void deleteOrder(Order order) {
        Connection connection;
        PreparedStatement preparedStatement = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Order WHERE OrderID = ?");
            preparedStatement.setInt(1, order.getOrderID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de Order impossible");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
    }

    public Order findOrder(int orderID) {
        Order orderFound = null;
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Order WHERE OrderID = ?");
            preparedStatement.setInt(1, orderID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("OrderID");
                int clientId = resultSet.getInt("ClientID");
                float amount = resultSet.getFloat("amount");
                float rawDiscount = resultSet.getFloat("discount");
                LocalDate date = resultSet.getDate("orderDate").toLocalDate();

                Discount discount = new Discount(0, rawDiscount, null);

                if (orderID == id) {
                    orderFound = new Order(id, clientId, amount, date, discount);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Order introuvable");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return orderFound;
    }

    public Order editOrder(Order order) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = sqlDatabase.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Order set ClientID=?, amount=?, discount=?, orderDate=? where OrderID=?");
            preparedStatement.setInt(1, order.getClientID());
            preparedStatement.setFloat(2, order.getAmount());
            preparedStatement.setFloat(3, order.rawDiscount());
            //preparedStatement.setString(4, order.getOrderDate());              //A REPARER
            preparedStatement.setInt(5, order.getOrderID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de modification de Order");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur de fermeture des ressources");
            }
        }
        return order;
    }
}
