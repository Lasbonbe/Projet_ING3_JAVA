package DAO;

import Modele.Order;

import java.util.ArrayList;

public interface OrderInterface {
    public ArrayList<Order> getAllOrder();
    public void addOrder(Order order);
    public void deleteOrder(Order order);
    public Order findOrder(int orderId);
    public Order editOrder(Order order);
}
