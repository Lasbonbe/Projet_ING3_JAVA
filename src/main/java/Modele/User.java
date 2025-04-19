package Modele;

public class User {
    protected int userID;
    protected String lastName;
    protected String firstName;
    private String email;
    private String password;

    //░█████╗░░█████╗░███╗░░██╗░██████╗████████╗██████╗░██╗░░░██╗░█████╗░████████╗░█████╗░██████╗░░██████╗
    //██╔══██╗██╔══██╗████╗░██║██╔════╝╚══██╔══╝██╔══██╗██║░░░██║██╔══██╗╚══██╔══╝██╔══██╗██╔══██╗██╔════╝
    //██║░░╚═╝██║░░██║██╔██╗██║╚█████╗░░░░██║░░░██████╔╝██║░░░██║██║░░╚═╝░░░██║░░░██║░░██║██████╔╝╚█████╗░
    //██║░░██╗██║░░██║██║╚████║░╚═══██╗░░░██║░░░██╔══██╗██║░░░██║██║░░██╗░░░██║░░░██║░░██║██╔══██╗░╚═══██╗
    //╚█████╔╝╚█████╔╝██║░╚███║██████╔╝░░░██║░░░██║░░██║╚██████╔╝╚█████╔╝░░░██║░░░╚█████╔╝██║░░██║██████╔╝
    //░╚════╝░░╚════╝░╚═╝░░╚══╝╚═════╝░░░░╚═╝░░░╚═╝░░╚═╝░╚═════╝░░╚════╝░░░░╚═╝░░░░╚════╝░╚═╝░░╚═╝╚═════╝░

    /**
     * Constructeur de User avec valeurs par défaut
     */
    public User() {
        this.userID = 0;
        this.lastName = "Nom par defaut";
        this.firstName = "Prenom par defaut";
    }

    /**
     * Constructeur de User avec paramètres
     * @param userID L'ID utilisateur
     * @param lastName Le nom d'utilisateur
     * @param firstName Le prénom d'utilsateur
     */
    public User(int userID, String lastName, String firstName, String email, String password) {
        this.userID = userID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░

    public int getUserID() {return userID;}
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }





     @Override
    public String toString() {
         return "User{" +
                 "userID=" + userID +
                 ", lastName='" + lastName + '\'' +
                 ", firstName='" + firstName + '\'' +
                 '}';
     }
}
