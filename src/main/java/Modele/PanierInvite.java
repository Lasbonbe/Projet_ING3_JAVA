package Modele;

import java.util.ArrayList;

public class PanierInvite {
    private static ArrayList<PanierItem> panierItems = new ArrayList<>();
    private static int idInvite;

    public static ArrayList<PanierItem> getPanierItems() {
        return panierItems;
    }
    public static void addPanierItem(PanierItem panierItem) {
        panierItems.add(panierItem);
    }
    public static void removePanierItem(PanierItem panierItem) {
        panierItems.remove(panierItem);
    }
    public static void clearPanierItems() {
        panierItems.clear();
    }
}
