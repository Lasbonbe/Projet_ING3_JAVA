package DAO;

import Modele.Administrator;

import java.util.ArrayList;

public interface AdministratorInterface {
    public ArrayList<Administrator> getAdministrators();


    public void addAdmin(Administrator admin);
    public void deleteAdmin(Administrator admin);
    public Administrator findAdmin(Administrator admin);
    public Administrator editAdmin(Administrator admin);
}