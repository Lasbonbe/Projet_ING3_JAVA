package Modele;

public class Client extends User {
    private int clientID;

    public Client(int userID, String lastName, String firstName, String email, String password, int age, int clientID) {
        super(userID,lastName, firstName, email, password, age);
        this.clientID = clientID;
    }

    public String determinePersonType(){
        if (age<12) { return "Mini Resa"; }
        else if (age<60) { return "Maxi Resa"; }
        else return "Senior Resa";
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░

    @Override
    public String getLastName() { return super.lastName; }

    public void setLastName(String lastName) { super.lastName = lastName; }

    @Override
    public String getFirstName() {
        return super.firstName;
    }

    @Override
    public String getEmail() {
        return super.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return super.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return clientID;
    }

    public void setId(int clientID) {
        this.clientID = clientID;
    }
}
