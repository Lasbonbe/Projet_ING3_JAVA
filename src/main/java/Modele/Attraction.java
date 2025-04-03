package Modele;

public class Attraction {
    private int attractionID;
    private String name;
    private int capacity;
    private int price;
    private int duration;

    public Attraction() {
        attractionID = 0;
        name = "Le Gigolo N'golo D Kante - The attraction";
        capacity = 10;
        price = 5;
        duration = 3;
    }
    public Attraction(int attractionID, String name, int capacity, int price, int duration) {
        this.attractionID = attractionID;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.duration = duration;
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░
    public int getAttractionID() { return attractionID;}
    public String getName() { return name;}
    public int getCapacity() { return capacity;}
    public int getPrice() { return price;}
    public int getDuration() { return duration;}

    public void setAttractionID(int attractionID) { this.attractionID = attractionID;}
    public void setName(String name) { this.name = name;}
    public void setCapacity(int capacity) { this.capacity = capacity;}
    public void setPrice(int price) { this.price = price;}
    public void setDuration(int duration) { this.duration = duration;}

}
