package Modele;

public class Administrator extends User {

    public Administrator( int userID, String lastName, String firstName, String email, String password) {
        super(userID, lastName, firstName, email, password);
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░
    @Override
    public int getUserID() { return userID; }

    public void setUserId(int userID) { this.userID = userID; }

    @Override
    public String getLastName() { return super.lastName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFirstName() {
        return super.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



}
