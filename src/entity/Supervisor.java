package entity;

import java.util.ArrayList;
/**
 * This class represents a supervisor who is a type of User in the system. They oversee a number of projects and inherits from the User class.
 * @author Khoo Yong Hui
 * @version 1.0
 * @since 2023-04-15
 */
public class Supervisor extends User {

    /**
     * A list of project IDs supervised by the supervisor.
     */
    public ArrayList<Integer> supervisedProjects;

    /**
     * Constructs a Supervisor object with the given name, ID, email, password, and type.
     * Initializes the supervisedProjects list as an empty ArrayList.
     * @param name The name of the supervisor.
     * @param id The ID of the supervisor.
     * @param email The email of the supervisor.
     * @param password The password of the supervisor.
     * @param type The type of user, which should be "Supervisor" for a supervisor object.
     */
    public Supervisor(String name, String id, String email, String password, String type) {
        super(name, id, email, password, type);
        this.supervisedProjects = new ArrayList<Integer>();
    }

    /**
     * @return The list of project IDs supervised by the supervisor.
     */
    public ArrayList<Integer> getProjects() {

        return supervisedProjects;
    }

}