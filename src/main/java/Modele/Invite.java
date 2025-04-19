package Modele;
import java.sql.Date;

public class Invite extends User {
    private Date birthdate;

    public Invite(int userID,String firstName, String lastName,String email, Date birthdate) {
        super(0,firstName,lastName,email,"");
        this.birthdate = birthdate;
    }
}