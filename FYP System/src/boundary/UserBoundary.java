package boundary;
import entity.CentralManager;
import java.util.Objects;
/**
 * This class represents the UserBoundary that handles the login screen and directs users to their respective screens.
 * It extends the BaseBoundary class and takes a CentralManager object as a parameter.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */
public class UserBoundary extends BaseBoundary{
    /**
     * Constructs a new UserBoundary object with the given CentralManager object.
     *
     * @param centralManager the CentralManager object that manages the system data
     */
    public UserBoundary(CentralManager centralManager) {
        super(centralManager);
    }
    /**
     * Displays the login screen and allows users to enter their credentials to log in to the system.
     * Directs users to their respective screens based on their user type.
     */
    public void login(){
        int choice = this.getInt("""
                Welcome to login screen. Please enter either '1' or '2':
                1) Login
                2) Exit
                """);
        while (!(choice == 1 || choice == 2)) {
            System.out.println("Invalid integer entered. Please try again.");
            choice = this.getInt("""
                Welcome to login screen. Please enter either '1' or '2':
                1) Login
                2) Exit
                """);
        }
        if (choice == 1) {
            String userID = this.getLine("Input UserID: ");
            String password = this.getLine("Input Password: ");
            String userType = this.getUserController().login(userID, password);
            while (Objects.equals(userType, "InvalidUser")) {
                System.out.println("UserID or Password was invalid. Please try again.");
                userID = this.getLine("Input UserID: ");
                password = this.getLine("Input Password: ");
                userType = this.getUserController().login(userID, password);
            }

            if (Objects.equals(userType, "Student")){
                System.out.println("Directing to student screen...");
                this.getStudentController().setCurrentUser(userID);
                this.getStudentBoundary().studentOperations();
            }
            else if (Objects.equals(userType, "Supervisor")) {
                System.out.println("Directing to supervisor screen...");
                this.getSupervisorController().setCurrentUser(userID);
                this.getSupervisorBoundary().supervisorOperations();
            }
            else if (Objects.equals(userType, "Coordinator")) {
                System.out.println("Directing to Coordinator screen...");
                this.getCoordinatorController().setCurrentUser(userID);
                this.getCoordinatorBoundary().coordinatorOperations();
            }
        } else {
            System.exit(0);
        }
    }


}