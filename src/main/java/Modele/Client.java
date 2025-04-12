package Modele;

public class Client extends User {
    private String email;
    private String password;
    private int age;

    public Client(int userID, String lastName, String firstName, int age, String email, String password) {
        super(userID, lastName, firstName);
        this.age = age;
        this.email = email;
        this.password = password;
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
    public int getUserID() { return super.userID; }

    @Override
    public String getLastName() { return super.lastName; }

    public void setLastName(String lastName) { super.lastName = lastName; }

    @Override
    public String getFirstName() { return super.firstName; }

    public void setFirstName(String firstName) { super.firstName = firstName; }

    public int getAge() { return age; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

}
