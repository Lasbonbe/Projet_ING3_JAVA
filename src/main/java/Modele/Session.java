package Modele;

public class Session {
    private static User user;
    private static Attraction selectedAttraction;
    private static Promotion selectedPromotion;

    /**
     * Utilisé pour vérifier l'instance de la session
     */
    public static void showInstanceOfSession() {System.out.println("Instance de Session : " + Session.user);}
    /**
     * Méthode pour vider la session
     * */
    public static void clearSession() {
        user = null;
    }

    /**
     * Utilisé pour vérifier si l'utilisateur est un client
     * @return true si l'utilisateur est un client, false sinon
     */
    public static boolean isClient() {
        return user instanceof Client;
    }

    /**
     * Utilisé pour vérifier si l'utilisateur est un admin
     * @return true si l'utilisateur est un admin, false sinon
     */
    public static boolean isAdmin() {
        return user instanceof Administrator;
    }
    /**
     * Utilisé pour vérifier si l'utilisateur est un invité
     * @return true si l'utilisateur est un invité, false sinon
     */
    public static boolean isInvite() {return user instanceof Invite;}


    //░██████╗███████╗████████╗░░░░██╗░██████╗░███████╗████████╗
    //██╔════╝██╔════╝╚══██╔══╝░░░██╔╝██╔════╝░██╔════╝╚══██╔══╝
    //╚█████╗░█████╗░░░░░██║░░░░░██╔╝░██║░░██╗░█████╗░░░░░██║░░░
    //░╚═══██╗██╔══╝░░░░░██║░░░░██╔╝░░██║░░╚██╗██╔══╝░░░░░██║░░░
    //██████╔╝███████╗░░░██║░░░██╔╝░░░╚██████╔╝███████╗░░░██║░░░
    //╚═════╝░╚══════╝░░░╚═╝░░░╚═╝░░░░░╚═════╝░╚══════╝░░░╚═╝░░░
    /**
     * Utilisé pour stocker l'utilisateur connecté
     * @param user l'utilisateur connecté
     */
    public static void setUser(User user) {
        Session.user = user;
    }

    /**
     * Utilisé pour récupérer l'utilisateur connecté
     * @return l'utilisateur connecté
     */
    public static User getUser() {
        return user;
    }




    //░█████╗░████████╗████████╗██████╗░░█████╗░░█████╗░████████╗██╗░█████╗░███╗░░██╗
    //██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔══██╗██╔══██╗╚══██╔══╝██║██╔══██╗████╗░██║
    //███████║░░░██║░░░░░░██║░░░██████╔╝███████║██║░░╚═╝░░░██║░░░██║██║░░██║██╔██╗██║
    //██╔══██║░░░██║░░░░░░██║░░░██╔══██╗██╔══██║██║░░██╗░░░██║░░░██║██║░░██║██║╚████║
    //██║░░██║░░░██║░░░░░░██║░░░██║░░██║██║░░██║╚█████╔╝░░░██║░░░██║╚█████╔╝██║░╚███║
    //╚═╝░░╚═╝░░░╚═╝░░░░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝░╚════╝░░░░╚═╝░░░╚═╝░╚════╝░╚═╝░░╚══╝
    /**
     * Méthode pour set l'attraction sélectionnée dans le cadre d'une modification par l'admin
     */
    public static void setSelectedAttraction(Attraction selectedAttraction) {
        if (isAdmin())
            Session.selectedAttraction = selectedAttraction;
        else
            throw new ClientNotAdminException("Client is not admin, tried to set selected attraction");
    }

    /**
     * Utilisé pour récupérer l'attraction sélectionnée
     * @return l'attraction sélectionnée
     */
    public static Attraction getSelectedAttraction() {
        if (isAdmin())
            return selectedAttraction;
        else
            throw new ClientNotAdminException("Client is not admin, tried to get selected attraction");
    }

    /**
     * Utilisé pour vider l'attraction sélectionnée
     */
    public static void clearSelectedAttraction() {
        if (isAdmin())
            selectedAttraction = null;
        else
            throw new ClientNotAdminException("Client is not admin, tried to clear selected attraction");
    }


    //██████╗░██████╗░░█████╗░███╗░░░███╗░█████╗░████████╗██╗░█████╗░███╗░░██╗
    //██╔══██╗██╔══██╗██╔══██╗████╗░████║██╔══██╗╚══██╔══╝██║██╔══██╗████╗░██║
    //██████╔╝██████╔╝██║░░██║██╔████╔██║██║░░██║░░░██║░░░██║██║░░██║██╔██╗██║
    //██╔═══╝░██╔══██╗██║░░██║██║╚██╔╝██║██║░░██║░░░██║░░░██║██║░░██║██║╚████║
    //██║░░░░░██║░░██║╚█████╔╝██║░╚═╝░██║╚█████╔╝░░░██║░░░██║╚█████╔╝██║░╚███║
    //╚═╝░░░░░╚═╝░░╚═╝░╚════╝░╚═╝░░░░░╚═╝░╚════╝░░░░╚═╝░░░╚═╝░╚════╝░╚═╝░░╚══╝
    /**
     * Méthode pour set la promotion sélectionnée dans le cadre d'une modification par l'admin
     */
    public static void setSelectedPromotion(Promotion selectedPromotion) {
        if (isAdmin())
            Session.selectedPromotion = selectedPromotion;
        else
            throw new ClientNotAdminException("Client is not admin, tried to set selected promotion");
    }

    /**
     * Utilisé pour récupérer la promotion sélectionnée
     * @return la promotion sélectionnée
     */
    public static Promotion getSelectedPromotion() {
        if (isAdmin())
            return selectedPromotion;
        else
            throw new ClientNotAdminException("Client is not admin, tried to get selected promotion");
    }

    /**
     * Utilisé pour vider la promotion sélectionnée
     */
    public static void clearSelectedPromotion() {
        if (isAdmin())
            selectedPromotion = null;
        else
            throw new ClientNotAdminException("Client is not admin, tried to clear selected promotion");
    }






}