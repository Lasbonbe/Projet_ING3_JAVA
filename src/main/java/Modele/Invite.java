package Modele;
import java.sql.Date;

public class Invite extends User {

    public Invite(int userID,String firstName, String lastName,String email) {
        super(0,firstName,lastName,email,"");
    }
}