package Modele;
import java.sql.Date;

public class Invite extends User {
    private String email;
    private Date birthdate;

    public Invite(int userID,String firstName, String lastName) {
        super(0,firstName,lastName);
    }
}