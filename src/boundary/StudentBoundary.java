package boundary;
import entity.CentralManager;
import entity.Request;

import java.util.ArrayList;

/**
 * Student's application interface after logging in. Can only be viewed by a Student user.
 * @author Si Ming Zhou
 * @version 1.0
 * @since 2023-04-15
 */
public class StudentBoundary extends UserBoundary{

    /**
     * Constructor for StudentBoundary class.
     * @param centralManager the central manager object used to interact with the system
     */
    public StudentBoundary(CentralManager centralManager){
        super(centralManager);
    }

    /**
     * Displays the menu choices available for the student user.
     */
    public void displayMenuChoices() {

        System.out.println(
                """
                        ========================= Welcome to Student App =========================
                        1.  Change password
                        2.  View available projects
                        3.  Select project to send to coordinator: Request allocation
                        4.  View own project
                        5.  View requests status and history
                        6.  Change title of project
                        7.  Deregister FYP: Request de-allocation
                        8.  Log out
                        ========================================================================
                """
        );
    }

    /**
     * Allows the student user to change their password.
     */
    public void changePassword() {
        String newPassword = this.getLine("Please enter a new password:");
        this.getStudentController().setPassword(newPassword);
        System.out.println("Your password has been successfully reset.");
    }

    /**
     * Displays the list of available projects for the student user.
     */
    public void viewProjects() {
        if (this.getStudentController().getRegistered()){
            System.out.println("You are currently allocated to a FYP and do not have access to available project list");
        }
        else {
            if (this.getStudentController().getCurrentStudent().canRegister()) {
                this.getProjectBoundary().viewProjects("available");
            } else {
                System.out.println("You are not allowed to make selection again as you deregistered your FYP");
            }

        }
    }

    /**
     * Displays the registered project of the student user.
     */
    public void viewRegisteredProject() {
        if (this.getStudentController().getRegistered()) {
            int projectID = this.getStudentController().getRegisteredProject();
            this.getProjectBoundary().viewProjectLine(projectID);
        } else {
            System.out.println("You are not registered in any project. Unable to view registered project.");
        }
    }

    /**
     * Displays the request history of the current student user by getting the list of requests from the StudentController and passing it to the RequestBoundary.
     */
    public void viewRequestHistory() {
        ArrayList<Request> requests = this.getStudentController().getRequestsHistory();
        this.getRequestBoundary().viewRequestsHistory(requests);
    }

    /**
     * Handles the request allocation process for the current student user by calling the student controller.
     * Displays available projects and allows the user to select a project to request allocation.
     * Also checks for existing pending requests before allowing the user to submit a new request.
     */
    public void requestAllocation() {
        this.viewProjects();
        String studentID = this.getStudentController().getCurrentStudent().getId();
        if (this.getRequestController().checkPendingProjectRequest(studentID)) {
            System.out.println("Existing project allocation request is pending processing. Please wait for the current one to be processed before submitting another request. Current request:");
        } else {
            if (!this.getStudentController().getRegistered()) {
                if (this.getStudentController().getCurrentStudent().canRegister()) {
                    String projectID = this.getLine("Please enter the projectID you would like to be allocated to:");
                    while (!this.getProjectController().validateAvailProjectID(Integer.parseInt(projectID))) {
                        System.out.print("Invalid project id. ");
                        projectID = this.getLine("Please enter the projectID you would like to be allocated to:");
                    }
                    this.getStudentController().requestAllocation(Integer.valueOf(projectID), this.getStudentController().getCurrentStudent().getId());
                    System.out.println("Your request has been submitted");
                } else {
                    System.out.println("You are not allowed to make selection again as you deregistered your FYP");
                }
            } else {
                System.out.println("You are currently allocated to a FYP and do not have access to available project list");
            }
        }
    }

    /**
     * Handles the request to change the title of the current student user's project by calling the student controller.
     * Checks if the user is registered in a project before allowing the user to submit the request.
     */
    public void requestChangeTitle() {
        if (this.getStudentController().getRegistered()) {
            String newProjectTitle = this.getLine("Enter your new project title: ");
            this.getStudentController().requestChangeTitle(newProjectTitle, this.getStudentController().getCurrentStudent().getId(), this.getStudentController().getRegisteredProject().toString());
            System.out.println("Your title change request has been submitted");
        } else {
            System.out.println("You are not registered in any project. Unable to change title.");
        }
    }

    /**
     * Handles the request to deallocate the current student user from their registered project by calling the student controller.
     * Checks if the user is registered in a project before allowing the user to submit the request.
     */
    public void requestDeallocation() {
        if (this.getStudentController().getCurrentStudent().getRegistered()){
            System.out.println("Your deallocation request has been submitted");
            this.getStudentController().requestDeAllocation(this.getStudentController().getRegisteredProject().toString(), this.getStudentController().getCurrentStudent().getId());
        } else{
            System.out.println("You are not registered in any project. Please try again!");
        }
    }


    /**
     * Allows student to perform various operations based on the user input.
     * Displays the available options and executes the corresponding operation
     * based on the user's input.
     */
    public void studentOperations() {
        int choice = 0;
        while (choice != 8) {
            this.displayMenuChoices();
            choice = this.getInt("Enter your choice:");
            if (choice < 1 | choice > 8) {
                System.out.println("Enter choice between 1-8 values only");
                continue;
            }
            switch (choice) {
                case 1 -> this.changePassword();
                case 2 -> this.viewProjects();
                case 3 -> this.requestAllocation();
                case 4 -> this.viewRegisteredProject();
                case 5 -> this.viewRequestHistory();
                case 6 -> this.requestChangeTitle();
                case 7 -> this.requestDeallocation();
                case 8 -> System.out.println("Logging out...");
            }
        }
    }
}
