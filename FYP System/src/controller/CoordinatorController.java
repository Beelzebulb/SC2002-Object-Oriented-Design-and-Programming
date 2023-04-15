package controller;

import entity.CentralManager;
import entity.Coordinator;
import entity.Request;
import entity.Supervisor;

import java.util.ArrayList;

/**
 * A class that serves as a Controller for the CoordinatorBoundary class.
 * This CoordinatorController class extends from the SupervisorController class
 * and provides methods for managing coordinators.
 *
 * @author Jeremy
 * @version 1.0
 * @since 15/4/23
 */
public class CoordinatorController extends SupervisorController{

    /**
     * Constructs a new instance of CoordinatorController object using the CentralManager
     * of the system.
     *
     * @param centralManager the CentralManager entity object for the system.
     */
    public CoordinatorController(CentralManager centralManager) {
        super(centralManager);
    }

    /**
     * get method to return the coordinator of the current user.
     * @return coordinator object of current user.
     */
    public Coordinator getCurrentCoordinator() {
        return (Coordinator) this.currentUser;
    }

    /**
     * Set method to set the current user object to the user object of the userID provided.
     * @param userID userID of the user object
     */
    public void setCurrentUser(String userID) {
        this.getSupervisorController().setCurrentUser(userID);
        this.currentUser = this.getUserByID(userID);
    }

    /**
     * Returns all request with the type specified regardless of status.
     *
     * @param type type of request you wish to get
     * @return ArrayList of all request with the type specified
     */
    public ArrayList<Request> getSpecificRequestsAll(String type) {
        return this.getRequestController().getRequestByType(type, "all");
    }

    /**
     * Returns all requests with the type specified that are still pending only.
     *
     * @param type type of request you wish to get
     * @return ArrayList of all request with the type specified which are pending
     */
    public ArrayList<Request> getSpecificRequestsPending(String type) {
        return this.getRequestController().getRequestByType(type, "pending");
    }


}
