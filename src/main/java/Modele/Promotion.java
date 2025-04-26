package Modele;

import java.time.LocalDate;

/**
 * Classe représentant un code de réduction.
 * Un code de réduction peut être un pourcentage ou un montant fixe.
 */
public class Promotion {
    private int Id;
    private String Name;
    private String Description;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private boolean Permanent;
    private int Percentage;



    /**
     * Constructeur de la classe Promotion.
     *
     * @param promotionId   L'identifiant du code de réduction.
     * @param promotionName Le nom du code de réduction.
     * @param percentage    Le pourcentage de réduction.
     * @param description   La description du code de réduction.
     */
    public Promotion(int promotionId, String promotionName, int percentage, String description) {
        this.Id = promotionId;
        this.Name = promotionName;
        this.Percentage = percentage;
        this.Description = description;
    }

    /**
     * Constructeur de la classe Promotion.
     *
     * @param promotionId   L'identifiant du code de réduction.
     * @param promotionName Le nom du code de réduction.
     * @param percentage    Le pourcentage de réduction.
     * @param description   La description du code de réduction.
     * @param startDate     La date de début de validité du code de réduction.
     * @param endDate       La date de fin de validité du code de réduction.
     * @param permanent     Indique si le code de réduction est permanent ou non.
     */
    public Promotion(int promotionId, String promotionName, int percentage, String description, LocalDate startDate, LocalDate endDate, boolean permanent) {
        this.Id = promotionId;
        this.Name = promotionName;
        this.Percentage = percentage;
        this.Description = description;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Permanent = permanent;
    }

    /** GETTER getID
     * @return L'identifiant du code de réduction.
     */
    public int getId() {return Id;}
    /** GETTER getName
     * @return Le nom du code de réduction.
     */
    public String getName() {return Name;}
    /** GETTER getDescription
     * @return La description du code de réduction.
     */
    public String getDescription() {return Description;}
    /** GETTER getStartDate
     * @return La date de début de validité du code de réduction.
     */
    public LocalDate getStartDate() {return StartDate;}
    /** GETTER getEndDate
     * @return La date de fin de validité du code de réduction.
     */
    public LocalDate getEndDate() {return EndDate;}
    /** GETTER isPermanent
     * @return true si le code de réduction est permanent, false sinon.
     */
    public boolean isPermanent() {return Permanent;}
    /** GETTER getPercentage
     * @return Le pourcentage de réduction.
     */
    public int getPercentage() {return Percentage;}


    /** SETTER setId
     * @param id L'identifiant du code de réduction.
     */
    public void setId(int id) {Id = id;}
    /** SETTER setName
     * @param name Le nom du code de réduction.
     */
    public void setName(String name) {Name = name;}
    /** SETTER setDescription
     * @param description La description du code de réduction.
     */
    public void setDescription(String description) {Description = description;}
    /** SETTER setStartDate
     * @param startDate La date de début de validité du code de réduction.
     */
    public void setStartDate(LocalDate startDate) {StartDate = startDate;}
    /** SETTER setEndDate
     * @param endDate La date de fin de validité du code de réduction.
     */
    public void setEndDate(LocalDate endDate) {EndDate = endDate;}
    /** SETTER setPermanent
     * @param permanent true si le code de réduction est permanent, false sinon.
     */
    public void setPermanent(boolean permanent) {Permanent = permanent;}
    /** SETTER setPercentage
     * @param percentage Le pourcentage de réduction.
     */
    public void setPercentage(int percentage) {Percentage = percentage;}

}
