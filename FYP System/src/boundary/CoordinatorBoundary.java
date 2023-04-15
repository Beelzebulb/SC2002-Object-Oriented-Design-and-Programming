package boundary;

import entity.CentralManager;
import entity.Request;

import java.util.ArrayList;
import java.util.Objects;

/**
 * CoordinatorBoundary is the Boundary layer for coordinators of the FYP in the system that
 * interfaces with the Coordinator system actor and issue commands to other
 * boundary and/or controller objects for execution of commands. <br>
 * CoordinatorBoundary is extended from SupervisorBoundary as the Coordinator is also a
 * Supervisor, hence would need its boundary attributes and methods
 * which provides basic functionality for supervising system components.
 *
 * @author Jeremy
 * @version 1.0
 * @since 15/4/2023
 */
public class CoordinatorBoundary extends SupervisorBoundary{
    /**
     * Constructs a new instance of CoordinatorBoundary object using the CentralManager of the system.
     * Additional methods and fields for the CoordinatorBoundary class would be defined here.
     * @param centralManager CentralManager entity for the system
     */
    public CoordinatorBoundary(CentralManager centralManager) {
        super(centralManager);
    }

    /**
     * Method to display the coordinator operation choices at the CoordinatorBoundary (that is called at the UserBoundary only if it is the Coordinator).
     * Then, after the Coordinator inputs her choice for which operation she wishes to perform, calls the respective method from the required controllers and performs it.
     */
    public void coordinatorOperations() {
        int choice = 0;
        while (choice != 16) {
            System.out.print(
                    """
                            ========================= Welcome to Coordinator App =========================
                    """
            );
            this.displayMenuChoices();
            choice = this.getInt("Enter your choice:");
            if (choice < 1 | choice > 16) {
                System.out.println("Enter choice between 1-16 values only");
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
                case 9 -> this.processSpecificRequests("changeSupervisor");
                case 10 -> this.processSpecificRequests("register");
                case 11 -> this.processSpecificRequests("deRegister");
                case 12 -> this.viewAllProjects();
                case 13 -> this.viewProjectsByFilter();
                case 14 -> this.processAllPendingRequests();
                case 15 -> this.viewAllRequests();
                case 16 -> System.out.println("Logging out...");
            }
        }
    }

    /**
     * Processes requests of a specific type by delegating to the appropriate boundary method
     * and prints a message indicating the type of requests being processed.
     *
     * @param type the type of request to process.
     */
    public void processSpecificRequests(String type) {
        System.out.println("Processing " + type + "requests:");
        this.getRequestBoundary().processRequestsCoordinator(type);
    }

    /**
     * Displays a list of all projects using the project boundary's "viewProjects" method. <br>
     * It prints out the 1. project ID, 2. project title, 3. Supervisor Name, 4. project status
     */
    public void viewAllProjects() {
        this.getProjectBoundary().viewProjects("all");
    }

    /**
     * Asks the user whether they want to filter by the specific criterion (prompt)
     * with a message containing the specified criterion to confirm their filter choice.
     *
     * @param prompt prompt the filter criterion to be presented to the user.
     * @return true if user inputs 'Y', false if user inputs 'N'
     */
    public boolean getYesNoForFilter(String prompt) {
        String res = this.getLine("Do you want to filter by " + prompt + "? Please enter only 'Y' or 'N'.");
        while (!(Objects.equals(res, "Y") || Objects.equals(res, "N"))) {
            res = this.getLine("Do you want to filter by " + prompt + "? Please enter only 'Y' or 'N'.");
        }
        return res.equals("Y");
    }

    /**
     * Method to get the filter status if the coordinator wishes to filter projects
     * with this status. Prompts the user with a message asking whether they want to
     * filter by the selected status. <br>
     * If user answers 'Y', it proceeds to prompt the user which status he/ she wishes
     * to filter by with the choices - Available, Reserved, Unavailable and Allocated.
     * Based on his/ her choice, returns a unique integer with respect to the project
     * status filter requested.<br>
     * If user answers 'N', it returns the integer '-3' to indicate that there is no
     * status filter requested.
     *
     * @return integer indicating the status which the supervisor wishes to filter by,
     * or if no status is request.
     */
    public int getFilterStatus() {
        boolean filterStatus = this.getYesNoForFilter("status");
        if (filterStatus) {
            int choice = this.getInt(
                    """
                            Please choose one of the following project status filters by entering numbers 1 to 4:
                            1) Available
                            2) Reserved
                            3) Unavailable
                            4) Allocated
                            """
            );
            while (choice < 1 || choice > 5) {
                System.out.print("Invalid choice. ");
                choice = this.getInt(
                        """
                                Please choose one of the following project status filters by entering numbers 1 to 4:
                                1) Available
                                2) Reserved
                                3) Unavailable
                                4) Allocated
                                """
                );
            }
            switch (choice) {
                case 1 -> {
                    return 1;
                }
                case 2 -> {
                    return 0;
                }
                case 3 -> {
                    return -2;
                }
                case 4 -> {
                    return -1;
                }
            }
        }

        return -3;
    }

    /**
     * Method to get the filter supervisor if the coordinator wishes to filter projects
     * by this supervisor. Prompts the user with a message asking
     * whether they want to filter by the selected supervisor.
     * If user answers 'Y', methods gets the string representing the supervisor to filter by
     * using the chooseSupervisor() method in the SupervisorBoundary object.
     * If user answers 'N', this method returns a string containing '!' to indicate that
     * there is no supervisor filter requested.
     *
     * @return a string representing the supervisor to filter by, or '!' if no supervisor
     * filter is requested.
     */
    public String getFilterSupervisor() {
        boolean filterStatus = this.getYesNoForFilter("supervisor");
        if (filterStatus) {
            return this.getSupervisorBoundary().chooseSupervisor();
        }
        return "!";
    }

    /**
     * Display projects with the filters requested by the coordinator
     * by calling the displayProjectsWithFilter in the ProjectBoundary.
     */
    public void viewProjectsByFilter() {
        int status = this.getFilterStatus();
        String supervisorID = this.getFilterSupervisor();
        this.getProjectBoundary().displayProjectsWithFilter(status, supervisorID);
    }

    /**
     * Displays all pending requests and allows coordinator to choose requests to approve.
     */
    public void processAllPendingRequests() {
        System.out.println("Processing all requests:");
        this.getRequestBoundary().processRequestsCoordinator("all");
    }

    /**
     * Displays all requests made (regardless of status or request type)
     */
    public void viewAllRequests() {
        this.getRequestBoundary().viewAllRequests();
    }

    /**
     * Display all menu choices that the Coordinator can make. <br>
     * Overrides displayMenuChoices from SupervisorBoundary.
     * Prints out Supervisor choices first, then further prints Coordinator choices to
     * create the full Coordinator menu.
     */
    public void displayMenuChoices() {
        super.displayMenuChoices();
        System.out.print(
                """
                        9. Process Supervisor Change Request
                        10.Process Project Allocation Request
                        11.Process Project De-registration request
                        12.View all projects
                        13.View projects by filter
                        14.Process all pending requests
                        15.View all requests
                        16.Log out
                        ========================================================================
                """
        );
    }


}
