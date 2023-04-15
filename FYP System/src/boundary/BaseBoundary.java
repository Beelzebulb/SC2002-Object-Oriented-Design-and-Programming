package boundary;

import entity.CentralManager;
import utils.Base;

import utils.Input;
/**
 * The BaseBoundary class extends the Base class and provides the basic input functionalities and access to all the boundary classes.
 * It contains the centralManager object and input object.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */
public class BaseBoundary extends Base {
    /**
     * The centralManager object for accessing all the entities and boundaries
     */
    CentralManager centralManager;
    /**
     * The input object for getting user input
     */
    Input input;

    /**
     * Constructs a BaseBoundary object with a centralManager object.
     *
     * @param centralManager the centralManager object
     */
    public BaseBoundary(CentralManager centralManager) {
        super(centralManager);
        this.centralManager = centralManager;
        this.input = centralManager.getInput();
    }

    /**
     * Returns the centralManager object.
     *
     * @return the centralManager object
     */
    @Override
    public CentralManager getCentralManager() {
        return this.centralManager;
    }

    /**
     * Prompts the user for an integer input with a given prompt.
     *
     * @param prompt the prompt for the user input
     * @return the integer input entered by the user
     */
    public Integer getInt(String prompt) {
        return this.input.getInt(prompt);
    }

    /**
     * Prompts the user for a double input with a given prompt.
     *
     * @param prompt the prompt for the user input
     * @return the double input entered by the user
     */
    public Double getDouble(String prompt) {
        return this.input.getDouble(prompt);
    }

    /**
     * Prompts the user for a string input with a given prompt.
     *
     * @param prompt the prompt for the user input
     * @return the string input entered by the user
     */
    public String getLine(String prompt) {
        return this.input.getLine(prompt);
    }


    // get Boundaries
    /**
     * Returns the ProjectBoundary object.
     *
     * @return the ProjectBoundary object
     */
    public ProjectBoundary getProjectBoundary() {
        return this.getCentralManager().getProjectBoundary();
    }
    /**
     * Returns the RequestBoundary object.
     *
     * @return the RequestBoundary object
     */
    public RequestBoundary getRequestBoundary() {
        return this.getCentralManager().getRequestBoundary();
    }
    /**
     * Returns the StudentBoundary object.
     *
     * @return the StudentBoundary object
     */
    public StudentBoundary getStudentBoundary() {
        return this.getCentralManager().getStudentBoundary();
    }
    /**
     * Returns the SupervisorBoundary object.
     *
     * @return the SupervisorBoundary object
     */
    public SupervisorBoundary getSupervisorBoundary() {
        return this.getCentralManager().getSupervisorBoundary();
    }
    /**
     * Returns the UserBoundary object.
     *
     * @return the UserBoundary object
     */
    public UserBoundary getUserBoundary() {
        return this.getCentralManager().getUserBoundary();
    }
    /**
     * Returns the CoordinatorBoundary object.
     *
     * @return the CoordinatorBoundary object
     */
    public CoordinatorBoundary getCoordinatorBoundary() {
        return this.getCentralManager().getCoordinatorBoundary();
    }
}
