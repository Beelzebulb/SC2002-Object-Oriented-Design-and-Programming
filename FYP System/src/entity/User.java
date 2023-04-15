package entity;
/**
 * A class representing a user in the system.
 * A user is identified by their name, id, email, password, and user type.
 * Users can be of type Student, Supervisor, or Coordinator.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */

public class User {
    /**
     * Creates the attributes of the user.
     * name - Name of the user
     * id - User ID
     * email - email address
     * password - password used to login
     * type - (Student, Supervisor, or Coordinator)
     */
    String name, id, email, password, type;

    /**
     * Constructs a new User object with the specified name, id, email, password, and user type.
     * @param name the user's name
     * @param id the user's id
     * @param email the user's email address
     * @param password the user's password
     * @param type the user's type (Student, Supervisor, or Coordinator)
     */
    public User(String name,String id,String email,String password, String type) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.type = type;
    }
    /**
     * Constructs a new empty User object.
     */
    public User(){};

    /**
     * return the user's id
     * @return String
     */
    public String getId() {
        return this.id;
    }

    /**
     * return the user's email address
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * return the user's name
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * return the user's type
     * @return String
     */
    public String getType() {
        return this.type;
    }

    /**
     * return the user's password
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's id to the specified value.
     * @param id the new id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the user's email address to the specified value.
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's name to the specified value.
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the user's password to the specified value.
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
