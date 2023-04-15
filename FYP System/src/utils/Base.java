package utils;

import controller.*;
import entity.CentralManager;
/**
* The Base class provides a base implementation for classes that require access to the various controllers in the system.
* It contains methods for retrieving instances of the ProjectController, RequestController, StudentController, SupervisorController,
* UserController, and CoordinatorController classes, all of which are obtained from the CentralManager instance passed into the constructor.
* Implements the BaseInterface abstract methods.
* @author Esther Teo Gek Wat
* @version 1.0
* @since 2023-04-15
 */
public class Base {
    /**
     * Object of class CentralManager
     */
    CentralManager centralManager;

    /**
     * Constructs a Base object with a reference to a CentralManager object.
     * @param centralManager the CentralManager object to be used to retrieve controller instances
     */
    public Base(CentralManager centralManager) {
        this.centralManager = centralManager;
    }

    /**
     * Returns the CentralManager object associated with this Base object.
     * @return the CentralManager object
     */
    public CentralManager getCentralManager() { // central manager is one of the abstract methods defined in base interface hence when we implement BaseInterface have to concretize it
        return this.centralManager;
    }

    /**
    * Returns the ProjectController object associated with this Base object.
    * @return the ProjectController object
     */
    public ProjectController getProjectController() {
        return this.getCentralManager().getProjectController();
    }

    /**
    * Returns the RequestController object associated with this Base object.
    * @return the RequestController object
     */
    public RequestController getRequestController() {
        return this.getCentralManager().getRequestController();
    }
    /**
    * Returns the StudentController object associated with this Base object.
    * @return the StudentController object
     */
    public StudentController getStudentController() {
        return this.getCentralManager().getStudentController();
    }
    /**
    * Returns the SupervisorController object associated with this Base object.
    * @return the SupervisorController object
     */
    public SupervisorController getSupervisorController() {
        return this.getCentralManager().getSupervisorController();
    }
    /**
    * Returns the UserController object associated with this Base object.
    * @return the UserController object
     */
    public UserController getUserController() {
        return this.getCentralManager().getUserController();
    }
    /**
    * Returns the CoordinatorController object associated with this Base object.
    * @return the CoordinatorController object
     */
    public CoordinatorController getCoordinatorController() {
        return this.getCentralManager().getCoordinatorController();
    }
}
