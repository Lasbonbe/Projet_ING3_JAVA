package Modele;

import java.time.LocalDate;

/**
 * Classe représentant un code de réduction.
 * Un code de réduction peut être un pourcentage ou un montant fixe.
 */
public class Discount {
    //Le discountRate c'est par exemple 30% de réduc, et discountAmount c'est pas exemple 30€ de réduc
    private final float discountRate;
    private final float discountAmount;
    private final LocalDate expirationDate;

    /**
     * Constructeur de la classe Discount.
     *
     * @param discountRate      Le taux de réduction (ex: 0.3 pour 30%).
     * @param discountAmount    Le montant de la réduction (ex: 30 pour 30€).
     * @param expirationDate    La date d'expiration du code de réduction.
     */
    public Discount(float discountRate, float discountAmount, LocalDate expirationDate) {
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
    }

    /**
     * Vérifie si le code de réduction est expiré.
     *
     * @return true si le code est expiré, false sinon.
     */
    public boolean isExpired(){
        LocalDate today = LocalDate.now();
        return today.isAfter(expirationDate);
    }

    //░██████╗░███████╗████████╗░
    //██╔════╝░██╔════╝╚══██╔══╝░
    //██║░░██╗░█████╗░░░░░██║░░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░
    //╚██████╔╝███████╗░░░██║░░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░░

    /**
     * GETTER getDiscountRate
     * @return Le taux de réduction (ex: 0.3 pour 30%).
     */
    public float getDiscountRate() {
        return discountRate;
    }
    /**
     * GETTER getDiscountAmount
     * @return Le montant de la réduction (ex: 30 pour 30€).
     */
    public float getDiscountAmount() {
        return discountAmount;
    }
    /**
     * GETTER getExpirationDate
     * @return La date d'expiration du code de réduction.
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

}
