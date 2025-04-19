package Modele;

import java.sql.Date;

public class Client extends User {
    private Date birthdate;

    public Client(int userID, String lastName, String firstName, Date birthdate, String email, String password) {
        super(userID, lastName, firstName,email,password);
        this.birthdate = birthdate;
    }

    /*
    public String determinePersonType(){
        if (birthdate<12) { return "Mini Resa"; }
        else if (birthdate<60) { return "Maxi Resa"; }
        else return "Senior Resa";
    }
    */

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░

    @Override
    public int getUserID() { return super.userID; }

    @Override
    public String getLastName() { return super.lastName; }

    public void setLastName(String lastName) { super.lastName = lastName; }

    @Override
    public String getFirstName() { return super.firstName; }

    public void setFirstName(String firstName) { super.firstName = firstName; }

    public Date getBirthDate() { return birthdate; }


}
