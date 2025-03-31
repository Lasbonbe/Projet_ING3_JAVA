package Modele;

public class Administrator extends User {
    private int adminID;

    public Administrator(String lastName, String firstName, String email, String password, int adminID) {
        super(0,lastName, firstName, email, password, 0);
        this.adminID = adminID;
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░

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
        return adminID;
    }

    public void setId(int adminID) {
        this.adminID = adminID;
    }
}
