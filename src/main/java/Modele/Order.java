package Modele;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe Order qui représente une commande d'attractions.
 * Elle contient des informations sur la commande, y compris l'ID de la commande, l'ID du client,
 * le montant total, la date de la commande, les attractions commandées et une éventuelle réduction.
 */
public class Order {
    private int orderID;
    private int clientID;
    private float amount;
    private LocalDate orderDate;
    private ArrayList<Attraction> attractionOrder;
    private Discount discount;

    /**
     * Constructeur par défaut de la classe Order.
     * Initialise les attributs avec des valeurs par défaut.
     */
    public Order( ) {
        orderID = 0;
        clientID = 0;
        amount = 0;
        orderDate = LocalDate.now();
        attractionOrder = new ArrayList<>();
        discount = null;
    }

    /**
     * Constructeur de la classe Order.
     * Initialise les attributs avec les valeurs fournies.
     *
     * @param orderID ID de la commande
     * @param clientID ID du client
     * @param amount Montant total de la commande
     * @param orderDate Date de la commande
     * @param attractionOrder Liste des attractions commandées
     * @param discount Réduction appliquée à la commande
     */
    public Order(int orderID, int clientID, float amount, LocalDate orderDate, ArrayList<Attraction> attractionOrder, Discount discount) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.amount = amount;
        this.orderDate = orderDate;
        this.attractionOrder = attractionOrder;
        this.discount = discount;
    }

    /**
     * Constructeur de la classe Order.
     * Initialise les attributs avec les valeurs fournies.
     *
     * @param orderID ID de la commande
     * @param clientID ID du client
     * @param amount Montant total de la commande
     * @param orderDate Date de la commande
     * @param discount Réduction appliquée à la commande
     */
    public Order(int orderID, int clientID, float amount, LocalDate orderDate, Discount discount) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.amount = amount;
        this.orderDate = orderDate;
        this.attractionOrder = null;
        this.discount = discount;
    }

    /**
     * Méthode pour calculer le montant total de la commande.
     * Elle additionne le prix de chaque attraction commandée et applique la réduction si elle est valide.
     *
     * @return Montant total de la commande après application de la réduction
     */
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

    /**
     * Méthode pour ajouter une attraction à la commande.
     *
     * @param attraction Attraction à ajouter à la commande
     */
    public void addAttractionToOrder(Attraction attraction) {
        attractionOrder.add(attraction);
    }

    /**
     * Méthode pour supprimer une attraction de la commande.
     *
     * @param attraction Attraction à supprimer de la commande
     */
    public void removeAttractionFromOrder(Attraction attraction) {
        attractionOrder.remove(attraction);
    }

    /**
     * Méthode pour ajouter une réduction à la commande.
     * Si la réduction est expirée, elle ne sera pas ajoutée.
     *
     * @param discount Réduction à ajouter à la commande
     */
    public void addDiscount(Discount discount) {
        if (!discount.isExpired()) {
            this.discount = discount;
        }
        else {
            System.out.println("Réduction impossible - Expiration : " + discount.getExpirationDate());
        }
    }

    /**
     * Méthode pour afficher les détails de la commande.
     * Elle affiche l'ID de la commande, les attractions commandées et le montant total.
     */
    public void showOrder() {
        System.out.println("\nVotre commande");
        System.out.println("Référence : " + orderID);
        for (Attraction attraction : attractionOrder) {
            System.out.println("Attraction : " + attraction.getName() + " - Prix : " + attraction.getPrice() + "€" );
        }
        System.out.println("\nTOTAL : " + amount);
    }

    /**
     * Méthode pour afficher les détails de la commande.
     * Elle affiche l'ID de la commande, les attractions commandées et le montant total.
     */
    public float rawDiscount() {
        return (amount - calculateTotal());
    }

    //░██████╗░███████╗████████╗░
    //██╔════╝░██╔════╝╚══██╔══╝░
    //██║░░██╗░█████╗░░░░░██║░░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░
    //╚██████╔╝███████╗░░░██║░░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░░

    /** GETTER getOrderID
     * @return orderID
     */
    public int getOrderID() { return orderID; }
    /** GETTER getClientID
     * @return clientID
     */
    public int getClientID() { return clientID; }
    /** GETTER getAmount
     * @return amount
     */
    public float getAmount() { return amount; }
    /** GETTER getOrderDate
     * @return orderDate
     */
    public LocalDate getOrderDate() { return orderDate; }
    /** GETTER getAttractionOrder
     * @return attractionOrder
     */
    public ArrayList<Attraction> getAttractionOrder() { return attractionOrder; }
    /** GETTER getDiscount
     * @return discount
     */
    public Discount getDiscount() { return discount; }
}
