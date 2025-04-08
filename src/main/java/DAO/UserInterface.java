package DAO;

import Modele.User;

import java.util.ArrayList;

public interface UserInterface {
    public ArrayList<User> getAllUser();
    public void addUser(User user);
    public void deleteUser(User user);
    public User findUser(int userID);
    public User editUser(User user);
}
