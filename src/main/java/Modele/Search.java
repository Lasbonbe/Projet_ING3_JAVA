package Modele;

import java.util.ArrayList;

public class Search {
    private String searchPrompt;
    private ArrayList<Object> searchParameters;

    public Search(String searchPrompt, ArrayList<Object> searchParameters) {
        this.searchPrompt = searchPrompt;
        this.searchParameters = searchParameters;
    }

    public String getSearchPrompt() {
        return searchPrompt;
    }

    public ArrayList<Object> getSearchParameters() {
        return searchParameters;
    }
}
