package Modele;

import java.sql.Date;

/**
 * Classe Modèle Client qui hérite de la classe User.
 * Représente un client avec un identifiant, un nom, un prénom, une date de naissance, un email et un mot de passe.
 */
public class Client extends User {
    private Date birthdate;

    /**
     * Constructeur de la classe Client.
     *
     * @param userID    Identifiant de l'utilisateur
     * @param lastName  Nom de famille
     * @param firstName Prénom
     * @param birthdate Date de naissance
     * @param email     Adresse email
     * @param password  Mot de passe
     */
    public Client(int userID, String lastName, String firstName, Date birthdate, String email, String password) {
        super(userID, lastName, firstName,email,password);
        this.birthdate = birthdate;
    }

    //░██████╗░███████╗████████╗░░░░██╗░██████╗███████╗████████╗
    //██╔════╝░██╔════╝╚══██╔══╝░░░██╔╝██╔════╝██╔════╝╚══██╔══╝
    //██║░░██╗░█████╗░░░░░██║░░░░░██╔╝░╚█████╗░█████╗░░░░░██║░░░
    //██║░░╚██╗██╔══╝░░░░░██║░░░░██╔╝░░░╚═══██╗██╔══╝░░░░░██║░░░
    //╚██████╔╝███████╗░░░██║░░░██╔╝░░░██████╔╝███████╗░░░██║░░░
    //░╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░╚═════╝░╚══════╝░░░╚═╝░░░

    /**
     * GETTER getUserID
     * @return userID
     */
    @Override
    public int getUserID() { return super.userID; }
    /**
     * GETTER getLastName
     * @return String lastName
     */
    @Override
    public String getLastName() { return super.lastName; }
    /**
     * GETTER getBirthDate
     * @return birthdate
     */
    public Date getBirthDate() { return birthdate; }
    /**
     * GETTER getFirstName
     * @return email
     */
    @Override
    public String getFirstName() { return super.firstName; }


    /**
     * SETTER setLastName
     * @param lastName
     */
    public void setLastName(String lastName) { super.lastName = lastName; }

    /**
     * SETTER setFirstName
     * @param firstName
     */
    public void setFirstName(String firstName) { super.firstName = firstName; }




}
