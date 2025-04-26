package Modele;

import java.util.ArrayList;

/**
 * Classe Search qui représente une recherche dans la base de données.
 * Elle contient un prompt de recherche et une liste de paramètres de recherche.
 */
public class Search {
    private String searchPrompt;
    private ArrayList<Object> searchParameters;

    /**
     * Constructeur de la classe Search.
     *
     * @param searchPrompt    Le prompt de recherche.
     * @param searchParameters La liste des paramètres de recherche.
     */
    public Search(String searchPrompt, ArrayList<Object> searchParameters) {
        this.searchPrompt = searchPrompt;
        this.searchParameters = searchParameters;
    }

    /** getSearchPrompt
     * @return Le prompt de recherche.
     */
    public String getSearchPrompt() {
        return searchPrompt;
    }

    /** getSearchParameters
     * @return La liste des paramètres de recherche.
     */
    public ArrayList<Object> getSearchParameters() {
        return searchParameters;
    }
}
