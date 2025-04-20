package Modele;

public class Session {
    private static User user;

    public static void setUser(User user) {
        Session.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static void clearSession() {
        user = null;
    }

    public static boolean isAdmin() {
        return user instanceof Administrator;
    }

    public static void clear() {
        user = null;
    }

    public static boolean isLoggedIn() {
        return user != null;
    }

    public static boolean isClient() {
        return user instanceof Client;
    }
}