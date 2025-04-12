package Modele;

import java.time.LocalDate;

public class Discount {
    //Le discountRate c'est par exemple 30% de réduc, et discountAmount c'est pas exemple 30€ de réduc
    private final float discountRate;
    private final float discountAmount;
    private final LocalDate expirationDate;

    public Discount(float discountRate, float discountAmount, LocalDate expirationDate) {
        this.discountRate = discountRate;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
    }

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
    public float getDiscountRate() {
        return discountRate;
    }
    public float getDiscountAmount() {
        return discountAmount;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

}
