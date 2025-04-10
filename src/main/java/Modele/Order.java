package Modele;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private int orderID;
    private float amount;
    private LocalDate orderDate;
    private ArrayList<Attraction> attractionOrder;
    private Discount discount;

    public Order( ) {
        orderID = 0;
        amount = 0;
        orderDate = LocalDate.now();
        attractionOrder = new ArrayList<>();
        discount = null;
    }

    public Order(int orderID, int amount, LocalDate orderDate, ArrayList<Attraction> attractionOrder, Discount discount) {
        this.orderID = orderID;
        this.amount = amount;
        this.orderDate = orderDate;
        this.attractionOrder = attractionOrder;
        this.discount = discount;
    }

    public float calculateTotal() {
        amount = 0;
        for (Attraction attraction : attractionOrder) {
            amount += attraction.getPrice();
        }
        if (discount != null) {
            amount = (amount - discount.getDiscountAmount()) * discount.getDiscountRate();
        }
        return amount;
    }

    public void addAttractionToOrder(Attraction attraction) {
        attractionOrder.add(attraction);
    }

    public void removeAttractionFromOrder(Attraction attraction) {
        attractionOrder.remove(attraction);
    }

    public void addDiscount(Discount discount) {
        if (!discount.isExpired()) {
            this.discount = discount;
        }
        else {
            System.out.println("Réduction impossible - Expiration : " + discount.getExpirationDate());
        }
    }

    public void showOrder() {
        System.out.println("\nVotre commande");
        System.out.println("Référence : " + orderID);
        for (Attraction attraction : attractionOrder) {
            System.out.println("Attraction : " + attraction.getName() + " - Prix : " + attraction.getPrice() + "€" );
        }
        System.out.println("\nTOTAL : " + amount);
    }

    //░██████╗░███████╗████████╗░
    //██╔════╝░██╔════╝╚══██╔══╝░
    //██║░░██╗░█████╗░░░░░██║░░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░
    //╚██████╔╝███████╗░░░██║░░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░░

    public int getOrderID() {
        return orderID;
    }
    public float getAmount() {
        return amount;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public ArrayList<Attraction> getAttractionOrder() {
        return attractionOrder;
    }
    public Discount getDiscount() {
        return discount;
    }
}
