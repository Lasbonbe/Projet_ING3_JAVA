package Modele;

/**
 * Classe représentant un administrateur.
 * Hérite de la classe User.
 */
public class Attraction {
    private int attractionID;
    private String name;
    private String type;
    private int capacity;
    private int price;
    private int duration;
    private String imagePath;
    private String description;

    /**
     * Constructeur de la classe Attraction.
     *
     * @param attractionID L'identifiant de l'attraction.
     * @param name         Le nom de l'attraction.
     * @param type         Le type de l'attraction.
     * @param capacity     La capacité de l'attraction.
     * @param price        Le prix de l'attraction.
     * @param duration     La durée de l'attraction.
     * @param description  La description de l'attraction.
     * @param imagePath    Le chemin de l'image de l'attraction.
     */
    public Attraction(int attractionID, String name, String type, int capacity, int price, int duration, String description, String imagePath) {
        this.attractionID = attractionID;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.duration = duration;
        this.type = type;
        this.description = description;
        this.imagePath = imagePath;
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░

    /** GETTER getAttractionID
     * @return L'identifiant de l'attraction.
     */
    public int getAttractionID() { return attractionID;}
    /** GETTER getName
     * @return Le nom de l'attraction.
     */
    public String getName() { return name;}
    /** GETTER getCapacity
     * @return La capacité de l'attraction.
     */
    public int getCapacity() { return capacity;}
    /** GETTER getPrice
     * @return Le prix de l'attraction.
     */
    public int getPrice() { return price;}
    /** GETTER getDuration
     * @return La durée de l'attraction.
     */
    public int getDuration() { return duration;}
    /** GETTER getType
     * @return Le type de l'attraction.
     */
    public String getType() { return type;}
    /** GETTER getDescription
     * @return La description de l'attraction.
     */
    public String getDescription() { return description;}
    /** GETTER getImagePath
     * @return Le chemin de l'image de l'attraction.
     */
    public String getImagePath() { return imagePath;}


    /** SETTER setAttractionID
     * @param attractionID L'identifiant de l'attraction.
     */
    public void setAttractionID(int attractionID) { this.attractionID = attractionID;}
    /** SETTER setName
     * @param name Le nom de l'attraction.
     */
    public void setName(String name) { this.name = name;}
    /** SETTER setCapacity
     * @param capacity La capacité de l'attraction.
     */
    public void setCapacity(int capacity) { this.capacity = capacity;}
    /** SETTER setPrice
     * @param price Le prix de l'attraction.
     */
    public void setPrice(int price) { this.price = price;}
    /** SETTER setDuration
     * @param duration La durée de l'attraction.
     */
    public void setDuration(int duration) { this.duration = duration;}
    /** SETTER setType
     * @param type Le type de l'attraction.
     */
    public void setType(String type) { this.type = type;}
    /** SETTER setDescription
     * @param description La description de l'attraction.
     */
    public void setDescription(String description) { this.description = description;}
    /** SETTER setImagePath
     * @param imagePath Le chemin de l'image de l'attraction.
     */
    public void setImagePath(String imagePath) { this.imagePath = imagePath;}

}
