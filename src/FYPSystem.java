import entity.CentralManager;

/**
 * The FYPSystem class is responsible for running the system and initializing the CentralManager.
 * It contains a while loop that continuously calls the login() method of the UserBoundary class,
 * which is accessed through the CentralManager instance variable.
 * The CentralManager instance variable is initialized in the constructor of the FYPSystem class.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */
public class FYPSystem {
    /**
     * Object of class CentralManager
     */
    CentralManager centralManager;

    /**
     * Constructs a new instance of the FYPSystem class and initializes the CentralManager instance variable.
     */
    public FYPSystem() {
        this.centralManager = new CentralManager();
    }

    /**
     * Runs the system by continuously calling the login() method of the UserBoundary class through the CentralManager instance variable.
     * This allows the user to login and use the system.
     */
    public void run() {
        while (true) {
            this.centralManager.getUserBoundary().login();
        }
    }
}
