package controller;

import boundary.BaseBoundary;
import entity.CentralManager;
import entity.User;

import java.util.ArrayList;
import java.util.Objects;
/**
 * The UserController class is responsible for managing the logic of all users in the system and handling user-related functionality.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */

public class UserController extends BaseController {
    /**
     * Creates an instance of an ArrayList of users
     */
    ArrayList<User> users;
    /**
     * Creates an instance of the current user of the User class
     */
    User currentUser;

    /**
     * Constructor for UserController class.
     * @param centralManager the CentralManager instance used to access system data.
     */
    public UserController(CentralManager centralManager) {
        super(centralManager);
        this.users = this.centralManager.getMasterUsers();
    }

    /**
     * Sets the current user of the system.
     * @param userID the ID of the user to set as current.
     */
    public void setCurrentUser(String userID) {
        this.currentUser = this.getUserByID(userID);
    }

    /**
     * Gets the user with the specified ID.
     * @param userID the ID of the user to get.
     * @return the User instance with the specified ID, or null if the user does not exist.
     */
    public User getUserByID(String userID) {
        for (User _user: this.users) {
            if (Objects.equals(_user.getId(), userID)){
                return _user;
            }
        }
        return null;
    }
    /**
     * Authenticates the user with the specified ID and password.
     * @param userID the ID of the user to authenticate.
     * @param pwd the password of the user to authenticate.
     * @return the type of the user if the authentication succeeds, or "InvalidUser" if the authentication fails.
     */
    public String login(String userID, String pwd){
        for(User user: this.users){
            if (Objects.equals(userID, user.getId()) && Objects.equals(pwd, user.getPassword())){
                return user.getType();
            }

        }
        return "InvalidUser";
    }
}
