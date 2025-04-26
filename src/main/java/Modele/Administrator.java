package Modele;

/**
 * Classe représentant un administrateur.
 * Hérite de la classe User.
 */
public class Administrator extends User {

    /** Constructeur de la classe Administrator.
     * @param userID L'identifiant de l'utilisateur.
     * @param lastName Le nom de l'utilisateur.
     * @param firstName Le prénom de l'utilisateur.
     * @param email L'adresse e-mail de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     */
    public Administrator( int userID, String lastName, String firstName, String email, String password) {
        super(userID, lastName, firstName, email, password);
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░

    /** GETTER userID
     * @return L'identifiant de l'utilisateur.
     */
    @Override
    public int getUserID() { return userID; }
    /** GETTER firstName
     * @return Le prénom de l'utilisateur.
     */
    @Override
    public String getFirstName() {return super.firstName;}
    /** GETTER email
     * @return L'adresse e-mail de l'utilisateur.
     */
    @Override
    public String getLastName() { return super.lastName; }

    /** SETTER lastName
     * @param lastName Le nom de l'utilisateur.
     */
    public void setLastName(String lastName) {this.lastName = lastName;}
    /** SETTER userID
     * @param userID L'identifiant de l'utilisateur.
     */
    public void setUserId(int userID) { this.userID = userID; }
    /** SETTER firstName
     * @param firstName Le prénom de l'utilisateur.
     */
    public void setFirstName(String firstName) {this.firstName = firstName;}



}
