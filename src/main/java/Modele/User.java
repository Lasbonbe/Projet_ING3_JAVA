package Modele;

public class User {
    protected int userID;
    protected String lastName;
    protected String firstName;
    protected String email;
    protected String password;

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

    /**
     * GETTER userID
     * @return L'identifiant de l'utilisateur.
     */
    public int getUserID() {return userID;}
    /**
     * GETTER lastName
     * @return Le nom de l'utilisateur.
     */
    public String getLastName() { return lastName; }
    /**
     * GETTER firstName
     * @return Le prénom de l'utilisateur.
     */
    public String getFirstName() { return firstName; }
    /**
     * GETTER email
     * @return L'adresse e-mail de l'utilisateur.
     */
    public String getEmail() { return email; }
    /**
     * GETTER password
     * @return Le mot de passe de l'utilisateur.
     */
    public String getPassword() { return password; }


    /**
     * SETTER setLastName
     * @param lastName Le nom de l'utilisateur.
     */
    public void setLastName(String lastName) { this.lastName = lastName; }
    /**
     * SETTER setFirstName
     * @param firstName Le prénom de l'utilisateur.
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }
    /**
     * SETTER setEmail
     * @param email L'adresse e-mail de l'utilisateur.
     */
    public void setEmail(String email) { this.email = email; }
    /**
     * SETTER setPassword
     * @param password Le mot de passe de l'utilisateur.
     */
    public void setPassword(String password) { this.password = password; }


    /**
     * Méthode toString pour afficher les informations de l'utilisateur
     * @return
     */
     @Override
    public String toString() {
         return "User{" +
                 "userID=" + userID +
                 ", lastName='" + lastName + '\'' +
                 ", firstName='" + firstName + '\'' +
                 '}';
     }
}
