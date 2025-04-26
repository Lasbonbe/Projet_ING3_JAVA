package Modele;

import java.time.LocalDate;

public class Promotion {
    private int Id;
    private String Name;
    private String Description;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private boolean Permanent;
    private int Percentage;



    public Promotion(int promotionId, String promotionName, int percentage, String description) {
        this.Id = promotionId;
        this.Name = promotionName;
        this.Percentage = percentage;
        this.Description = description;
    }

    public Promotion(int promotionId, String promotionName, int percentage, String description, LocalDate startDate, LocalDate endDate, boolean permanent) {
        this.Id = promotionId;
        this.Name = promotionName;
        this.Percentage = percentage;
        this.Description = description;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Permanent = permanent;
    }

    public int getId() {return Id;}
    public String getName() {return Name;}
    public String getDescription() {return Description;}
    public LocalDate getStartDate() {return StartDate;}
    public LocalDate getEndDate() {return EndDate;}
    public boolean isPermanent() {return Permanent;}
    public int getPercentage() {return Percentage;}

    public void setId(int id) {Id = id;}
    public void setName(String name) {Name = name;}
    public void setDescription(String description) {Description = description;}
    public void setStartDate(LocalDate startDate) {StartDate = startDate;}
    public void setEndDate(LocalDate endDate) {EndDate = endDate;}
    public void setPermanent(boolean permanent) {Permanent = permanent;}
    public void setPercentage(int percentage) {Percentage = percentage;}

}
