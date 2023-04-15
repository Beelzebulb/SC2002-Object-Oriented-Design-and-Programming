package controller;

import entity.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The SupervisorController class is a subclass of UserController that handles supervisor-specific functionalities.
 * It manages the creation and retrieval of projects, setting supervisor passwords, and managing incoming and outgoing requests.
 * @author Khoo Yong Hui
 * @version 1.0
 * @since 2023-04-15
 */

public class SupervisorController extends UserController {
    /**
     * The list of projects managed by the system.
     */
    ArrayList<Project> masterProjects = this.centralManager.getMasterProjects();

    /**
     * Constructor for the SupervisorController class.
     *
     * @param centralManager the central manager instance that provides access to the system's data.
     */
    public SupervisorController(CentralManager centralManager) {
        super(centralManager);
    }

    /**
     * Sets a new password for the current supervisor.
     *
     * @param newPassword the new password to be set.
     */
    public void setPassword(String newPassword) {
        this.getCurrentSupervisor().setPassword(newPassword);
    }

    /**
     * Retrieves a supervisor by their ID.
     *
     * @param userID the ID of the supervisor to retrieve.
     * @return the Supervisor instance corresponding to the given ID, or null if no such supervisor exists.
     */
    public Supervisor getSupervisorByID(String userID) {
        for (User user : this.users) {
            if (Objects.equals(user.getId(), userID)) {
                return (Supervisor) user;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of all incoming requests for the current supervisor.
     *
     * @return an ArrayList of Request instances representing all incoming requests for the current supervisor.
     */
    public ArrayList<Request> getIncomingRequests() {
        String supervisorID = this.getCurrentSupervisor().getId();
        return this.getRequestController().getRequestsBySupervisorID(supervisorID, "");
    }

    /**
     * Retrieves the current supervisor.
     *
     * @return the Supervisor instance representing the current supervisor.
     */
    public Supervisor getCurrentSupervisor() {
        return (Supervisor) this.currentUser;
    }

    /**
     * Sets the current user to the user with the given ID.
     *
     * @param userID the ID of the user to set as the current user.
     */
    public void setCurrentUser(String userID) {
        this.currentUser = this.getUserByID(userID);
    }

    /**
     * Creates a new project with the given title and assigns it to the current supervisor.
     *
     * @param projectTitle the title of the project to be created.
     */
    public void createProject(String projectTitle) {
        String supervisorID = this.getCurrentSupervisor().getId();
        int status = 1;
        if (this.reachedProjectCap(this.getCurrentSupervisor().getId())) {
            status = -2;
        }
        this.getProjectController().createProject(supervisorID, projectTitle, status);
    }

    /**
     * Retrieves a list of all submitted projects for the current supervisor.
     *
     * @return an ArrayList of Project instances representing all submitted projects for the current supervisor.
     */
    public ArrayList<Project> getSubmittedProjects() {
        String supervisorID = this.getCurrentSupervisor().getId();
        return this.getProjectController().getSubmittedProjects(supervisorID);
    }

    /**
     * Retrieves a list of all supervisors in the system.
     *
     * @return an ArrayList of Supervisor instances representing all supervisors in the system.
     */
    public ArrayList<Supervisor> getSupervisorList() {
        ArrayList<Supervisor> supervisors = new ArrayList<>();
        for (User user : this.users) {
            if (Objects.equals(user.getType(), "Supervisor")) {
                supervisors.add((Supervisor) user);
            }
        }
        return supervisors;
    }

    /**
     * Verifies that the supervisor is indeed in the list of supervisors.
     * @param supervisorID ID of the supervisor to check
     * @param supervisors ArrayList of all supervisors
     * @return true if supervisor is found and false when no such supervisor exists.
     */
    public boolean supervisorInList(String supervisorID, ArrayList<Supervisor> supervisors) {
        for (Supervisor supervisor : supervisors) {
            if (Objects.equals(supervisor.getId(), supervisorID)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the supervisor is already supervising two projects.
     * @param supervisorID ID of the supervisor to check
     * @return true if supervisor is supervising two projects (reached the maximum number of
     * projects he/she is allowed to supervise) and false if the cap has not been reached.
     */
    public boolean reachedProjectCap(String supervisorID) {
        ArrayList<Project> projects = this.getProjectController().getProjectsBySupervisorID(supervisorID);
        int projectCount = 0;
        for (Project project : projects) {
            if (project.getProjectStatus() == -1) {
                projectCount++;
            }
        }
        return !(projectCount < 2);
    }
}
