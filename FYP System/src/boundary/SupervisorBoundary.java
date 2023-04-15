package boundary;
import controller.SupervisorController;
import entity.CentralManager;
import entity.Project;
import entity.Request;
import entity.Supervisor;

import java.util.ArrayList;
/**
 * The SupervisorBoundary class is responsible for handling interactions between the supervisor interface and the SupervisorController.
 * It extends the UserBoundary class and inherits its methods.
 * @author Khoo Yong Hui
 * @version 1.0
 * @since 2023-04-15
 */

public class SupervisorBoundary extends UserBoundary {
    /**
     * Constructor for the SupervisorBoundary class.
     * @param centralManager A CentralManager object that allows access to the various controllers in the system.
     */
    public SupervisorBoundary(CentralManager centralManager) {
        super(centralManager);
    }
    /**
     * Displays the menu choices for the supervisor operations.
     */
    public void displayMenuChoices() {
        System.out.print(
                """
                        1.  Reset Password
                        2.  Create Project
                        3.  View Submitted Projects
                        4.  Modify project title
                        5.  Process request (Modify Project Title)
                        6.  Request project transfer
                        7.  View incoming requests status and history
                        8.  View outgoing requests status and history
                """
        );
    }


    /**
     * Performs the supervisor operations based on user input.
     */
    public void supervisorOperations() {
        int choice = 0;
        while (choice != 9) {
            System.out.print(
                """
                        ========================= Welcome to Supervisor App =========================
                """
            );
            this.displayMenuChoices();
            System.out.println("""
                            9.  Log out
                            ========================================================================
                    """);
            choice = this.getInt("Enter your choice: ");
            if (choice < 1 | choice > 9) {
                System.out.println("Enter choice between 1-9 values only: ");
                continue;
            }
            switch (choice) {
                case 1 -> this.changePassword();
                case 2 -> this.createProject();
                case 3 -> this.viewSubmittedProjects();
                case 4 -> this.modifyProjectTitle();
                case 5 -> this.processRequests();
                case 6 -> this.requestProjectTransfer();
                case 7 -> this.viewIncomingRequestHistory();
                case 8 -> this.viewOutgoingRequestHistory();
                case 9 -> System.out.println("Logging out...");
            }
        }
    }
    /**
     * Changes the password of the current supervisor.
     */
    public void changePassword() {
        String newPassword = this.getLine("Please enter a new password: ");
        this.getSupervisorController().setPassword(newPassword);
        System.out.println("Your password has been successfully reset.");
    }
    /**
     * Creates a project with the specified project title.
     */
    public void createProject(){
        String projectTitle = this.getLine("Enter Project Title: ");

        this.getSupervisorController().createProject(projectTitle);

        System.out.println("Project created successfully.");
    }
    /**
     * Displays a list of submitted projects.
     */
    public void viewSubmittedProjects() {
        ArrayList<Project> projects = this.getSupervisorController().getSubmittedProjects();
        System.out.println("Submitted Projects:");
        this.getProjectBoundary().printProjectFormat();
        for (Project project: projects) {
            this.getProjectBoundary().viewProjectLine(project.getProjectID());
        }
    }
    /**
     * Modifies the title of a submitted project.
     */
    public void modifyProjectTitle(){
        this.viewSubmittedProjects();
        this.getProjectBoundary().modifyProjectTitle(this.getSupervisorController().getCurrentSupervisor().getId());
    }
    /**
     * Allows the supervisor to process requests.
     */
    public void processRequests() {
        this.getRequestBoundary().processRequestsSupervisor(this.getSupervisorController().getCurrentSupervisor().getId());
    }

    /**
     * Allows the supervisor to request a project transfer to replacement supervisor.
     */
    public void requestProjectTransfer() {
        this.getRequestBoundary().requestTransfer(this.getSupervisorController().getCurrentSupervisor().getId());
    }
    /**
     * Displays a list of incoming request history.
     */
    public void viewIncomingRequestHistory() {
        ArrayList<Request> requests = this.getSupervisorController().getIncomingRequests();
        System.out.println("Incoming requests:");
        this.getRequestBoundary().viewRequestsHistory(requests);
    }
    /**
     * Displays a list of outgoing request history.
     */
    public void viewOutgoingRequestHistory() {
        String supervisorID = this.getSupervisorController().getCurrentSupervisor().getId();
        ArrayList<Request> requests = this.getRequestController().getRequestsByUserID(supervisorID);
        System.out.println("Outgoing requests:");
        this.getRequestBoundary().viewRequestsHistory(requests);
    }
    /**
     * Allows the supervisor to choose a supervisor from a list of supervisors.
     * @return A string representing the ID of the chosen supervisor.
     */
    public String chooseSupervisor() {
        System.out.println("List of supervisors:");
        System.out.print("[Supervisor ID]: Supervisor Name");
        ArrayList<Supervisor> supervisorList = this.getSupervisorController().getSupervisorList();
        for (Supervisor supervisor: supervisorList) {
            System.out.println("[" + supervisor.getId() + "]" + ": " + supervisor.getName());
        }
        String newSupervisorID = this.getLine("Please enter supervisorID of the chosen supervisor: ");
        while (!this.getSupervisorController().supervisorInList(newSupervisorID, supervisorList)) {
            System.out.print("SupervisorID is invalid. ");
            newSupervisorID = this.getLine("Please enter supervisorID of the chosen supervisor: ");
        }
        return newSupervisorID;
    }


}
