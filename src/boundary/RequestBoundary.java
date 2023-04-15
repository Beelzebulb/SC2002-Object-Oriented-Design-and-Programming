package boundary;
import entity.CentralManager;
import entity.Request;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Request's application interface used by Student, Supervisor & Cooordinator to process & update requests.
 * @author Si Ming Zhou
 * @version 1.0
 * @since 2023-04-15
 */
public class RequestBoundary extends BaseBoundary {

    /**
     * Constructor for the RequestBoundary class.
     * Calls the constructor of the superclass BaseBoundary.
     * @param centralManager instance of CentralManager class
     */
    public RequestBoundary(CentralManager centralManager) {
        super(centralManager);
    }

    /**
     * Prints the request format.
     */
    public void printRequestFormat() {
        System.out.println("[Type] | Requester | Request Date | Project ID | Status | Remarks");
    }

    /**
     * Displays a single request line in the specified format.
     * @param request instance of Request class
     */
    public void displayRequestLine(Request request) {
        String requesteeName = this.getUserController().getUserByID(request.getRequesteeID()).getName();
        String remarks = "";
        if (Objects.equals(request.getType(), "changeTitle")) {
            remarks = "Change project title to " + request.getUpdatedValue();
        } else if (Objects.equals(request.getType(), "changeSupervisor")) {
            remarks = "Change supervisor to " + this.getUserController().getUserByID(request.getUpdatedValue()).getName();
        } else {
            remarks = "-";
        }
        System.out.println("[" + request.getType() + "]" + " | " + requesteeName + " | " + request.getDate() + " | " + request.getProjectID() + " | " + request.getStatus() + " | " + remarks);
    }

    /**
     * Processes a request by prompting the user to approve or reject it.
     * @param request instance of Request class
     * @return boolean true if user chooses to exit request processing, false otherwise
     */
    public boolean processRequest(Request request) {
        String decision = this.getLine("Approve request? Please enter 'Y' to approve request and 'N' to reject request, or 'B' to exit request processing.");
        while (!(Objects.equals(decision, "Y") || Objects.equals(decision, "N") || Objects.equals(decision, "B"))) {
            System.out.println("Please enter only 'Y' or 'N'. To exit request processing, please enter 'B'.");
            decision = this.getLine("Approve request? Please enter 'Y' to approve request and 'N' to reject request.");
        }
        if (decision.equals("B")) {
            return true;
        } else {
            boolean _decision = this.getRequestController().processRequest(request, decision);
            if (_decision) {
                System.out.println("Request approved.");
            } else {
                System.out.println("Request rejected.");
            }
            return false;
        }
    }

    /**
     * Requests a transfer for a project with the given supervisorID.
     * Prompts the user for the project ID and new supervisor ID.
     * @param supervisorID the supervisor ID for the project to be transferred
     */
    public void requestTransfer(String supervisorID) {
        this.getProjectBoundary().viewProjectsBySupervisorID(supervisorID, "all");
        String projectID = this.getLine("Please enter projectID of project to request transfer for:");
        String newSupervisorID = this.getCentralManager().getSupervisorBoundary().chooseSupervisor();
        this.getRequestController().requestTransfer(projectID, supervisorID, newSupervisorID);
    }

    /**
     * Displays the given list of requests with a counter for each request.
     * @param requests the list of Request objects to be displayed
     */
    public void displayRequestsWithCounter(ArrayList<Request> requests) {
        for (int i = 0; i < requests.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
            this.displayRequestLine(requests.get(i));
        }
    }

    /**
     * Views the history of incoming requests for the given supervisor ID.
     * Displays the list of pending requests for the supervisor.
     * @param supervisorID the supervisor ID for whom the requests are to be viewed
     */
    public void viewIncomingRequestsHistory(String supervisorID) {
        ArrayList<Request> requests = this.getRequestController().getRequestsBySupervisorID(supervisorID, "pending");
        this.displayRequestsWithCounter(requests);
    }

    /**
     * Views the history of requests for the given list of Request objects.
     * Displays the list of requests with a counter for each request.
     * @param requests the list of Request objects to be displayed
     */
    public void viewRequestsHistory(ArrayList<Request> requests) {
        this.getRequestBoundary().printRequestFormat();
        if (requests.size() == 0) {
            System.out.println("--None--");
        } else {
            this.displayRequestsWithCounter(requests);
        }
    }

    /**
     * Processes requests through a coordinator of a specific type.
     * @param type the type of coordinator to process requests through
     */
    public void processRequestsCoordinator(String type) {
        System.out.println("Please choose a request to process:");
        System.out.print("[Request Number] | ");
        this.printRequestFormat();
        while (true) {
            ArrayList<Request> requests = this.getCoordinatorController().getSpecificRequestsPending(type);
            if (requests.size() == 0) {
                System.out.println("No requests to process.");
                return;
            }
            this.displayRequestsWithCounter(requests);
            int requestIDX = Integer.parseInt(this.getLine("Please enter request number to process (Enter only numbers 1 to " + (requests.size()) + ")"));
            while (!(requestIDX >= 1 && requestIDX < requests.size() + 1)) {
                System.out.print("Invalid request number. ");
                requestIDX = Integer.parseInt(this.getLine("Please enter request number to process (Enter only numbers 1 to " + (requests.size()) + ")"));
            }
            boolean exit = this.processRequest(requests.get(requestIDX - 1));
            if (exit) {
                return;
            }
        }
    }

    /**
     * Displays all requests in the system.
     */
    public void viewAllRequests() {
        System.out.println("Displaying all requests:");
        ArrayList<Request> requests = this.getRequestController().getAllRequests();
        this.displayRequestsWithCounter(requests);

    }

    /**
     * Processes requests for a specific supervisor.
     * @param supervisorID the ID of the supervisor
     */
    public void processRequestsSupervisor(String supervisorID) {
        System.out.println("Please choose a request to process:");
        System.out.print("[Request Number] | ");
        this.printRequestFormat();
        while (true) {
            ArrayList<Request> requests = this.getRequestController().getRequestsBySupervisorID(supervisorID, "pending");
            if (requests.size() == 0) {
                System.out.println("No requests to process.");
                return;
            }
            this.displayRequestsWithCounter(requests);
            int requestIDX = Integer.parseInt(this.getLine("Please enter request number to process (Enter only numbers 1 to " + (requests.size()) + ")"));
            while (!(requestIDX >= 1 && requestIDX < requests.size() + 1)) {
                System.out.print("Invalid request number. ");
                requestIDX = Integer.parseInt(this.getLine("Please enter request number to process (Enter only numbers 1 to " + (requests.size()) + ")"));
            }
            boolean exit = this.processRequest(requests.get(requestIDX - 1));
            if (exit) {
                return;
            }
        }
    }
}
