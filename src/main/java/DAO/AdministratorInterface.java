package DAO;

import Modele.Administrator;

import java.util.ArrayList;

public interface AdministratorInterface {
    public ArrayList<Administrator> getAllAdministrators();
    public void addAdmin(Administrator admin);
    public void deleteAdmin(Administrator admin);
    public Administrator findAdmin(int adminID);
    public Administrator editAdmin(Administrator admin);
}