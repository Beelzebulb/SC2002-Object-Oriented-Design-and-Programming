package entity;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Student class which is a blueprint to create Student objects.
 * @author Si Ming Zhou
 * @version 1.0
 * @since 2023-04-15
 */

public class Student extends User{
    /**
     * Holds a boolean value indicating if the student is registered or not.
     */
    private Boolean isRegistered;

    /**
     * Holds a list of project request IDs made by the student.
     */
    private ArrayList<Integer> requests;

    /**
     * Holds the project ID of the project that the student is registered for.
     */
    private int registeredProject;

    /**
    * Constructs a new Student object with the given name, id, email address, password, user type and project ID.
    * @param name the name of the student
    * @param id the ID of the student
    * @param emailAddress the email address of the student
    * @param password the password of the student
    * @param type the user type of the student
    * @param projectID the project ID of the project that the student is registered for
     */
    public Student(String name, String id, String emailAddress, String password, String type, int projectID) {
        super(name,id,emailAddress, password, type);
        this.setRegisteredProject(projectID);
    }

    /**
     * Returns a boolean value indicating if the student is registered or not.
     * @return true if the student is registered, false otherwise
     */
    public Boolean getRegistered() {
        return !(this.registeredProject == -1 || this.registeredProject == -2);
    }

    /**
     * Returns a list of project request IDs made by the student.
     * @return the list of project request IDs
     */
    public ArrayList<Integer> getRequests() {
        return requests;
    }

    /**
     * Returns the project ID of the project that the student is registered for.
     * @return the project ID of the registered project
     */
    public int getRegisteredProject() {
        return this.registeredProject;
    }

    /**
     * Returns a boolean value indicating if the student can register for a project or not.
     * @return true if the student can register for a project, false otherwise
     */
    public boolean canRegister() {
        return this.registeredProject != -2;
    }

    /**
     * Sets the project ID of the project that the student is registered for.
     * @param projectId the project ID of the registered project
     */
    public void setRegisteredProject(int projectId){
        this.registeredProject = projectId;
    }


}
