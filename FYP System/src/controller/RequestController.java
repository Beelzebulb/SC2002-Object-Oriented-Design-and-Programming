package controller;

import entity.CentralManager;
import entity.Project;
import entity.Request;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that serves as a controller for managing requests.
 * This class extends the BaseController class.
 *
 * @author Si Ming Zhou
 * @version 1.0
 * @since 15/4/23
 */
public class RequestController extends BaseController {
    /**
     * An ArrayList of Request object type to store all requests in the system.
     */
    ArrayList<Request> masterRequests;

    /**
     * Constructs a new instance of RequestController using the CentralManager of the system. <br>
     * Initializes the masterRequest ArrayList with the ArrayList of all requests in the system. (if necessary)
     *
     * @param centralManager (centralManager) the CentralManager entity object for the system.
     */
    public RequestController(CentralManager centralManager) {
        super(centralManager);
        this.masterRequests = this.centralManager.getMasterRequests();
    }

    /**
     * Gets all the request made by the specified userID provided as an ArrayList of Request objects.
     *
     * @param userID ID of the user you wish to get requests of.
     * @return ArrayList of Request made by the specified userID provided
     */
    public ArrayList<Request> getRequestsByUserID(String userID) {
        ArrayList<Request> requests = new ArrayList<Request>();
        for (Request request: this.masterRequests) {
            if (Objects.equals(request.getRequesteeID(), userID)) {
                requests.add(request);
            }
        }
        return requests;
    }

    /**
     * Returns a boolean value of whether the students request is still pending or has been approved.<br>
     * If True, request is still pending and yet to be approved by the Coordinator.<br>
     * If False, request has been approved by the Coordinator.
     * @param studentID ID of the student who wishes to check his/her pending project request
     * @return True, if requestee ID is the specified student ID provided when request type is "register" and request status is "pending", <br>
     * else, False
     */
    public boolean checkPendingProjectRequest(String studentID) {
        for (Request request: this.masterRequests) {
            if (Objects.equals(request.getRequesteeID(), studentID) && Objects.equals(request.getType(), "register") && Objects.equals(request.getStatus(), "pending")) {
                return true;
            }
        }
        return false;
    }

    /**
     * With parameters given, initialise a new instance of Request with the information.
     * Then adds this new request created to the masterRequest ArrayList.
     * This method should only be used by Students.
     *
     * @param projectId ID of specified project
     * @param type Type of request student is making.
     * @param studentID ID of student requestee.
     * @param value new Title to be replaced.
     */
    public void baseRequestStudent(Integer projectId, String type, String studentID, String value) {
        Request request = new Request(projectId, type, studentID, value);
        this.submitRequest(request);
    }

    /**
     * Using the baseRequestStudent, this method specifies the type of request that the student is making
     * to "changeTitle" which is used when the student is requesting to change the project title.
     *
     * @param newTitle new Title to be changed to.
     * @param studentID ID of student who is allocated this project.
     * @param projectID ID of specific project student wishes to change the title of.
     */
    public void requestChangeTitle(String newTitle, String studentID, String projectID){
        this.baseRequestStudent(Integer.valueOf(projectID), "changeTitle", studentID, newTitle);
    }

    /**
     * Using the baseRequestStudent, this method specifies the type of request that the student is making
     * to "register" which is used when the student is requesting for a project to be allocated to him/her.
     *
     * @param projectID ID of specific project student wishes to change the title of.
     * @param studentID ID of student who is allocated this project.
     */
    public void requestAllocation(String projectID, String studentID){
        this.baseRequestStudent(Integer.valueOf(projectID), "register", studentID, "");
    }

    /**
     * Using the baseRequestStudent, this method specifies the type of request that the student is making
     * to "deRegister" which is used when student is requesting for his/ her allocated project to be
     * deallocated from him/her.
     *
     * @param projectID ID of specific project student wishes to change the title of.
     * @param studentID ID of student who is allocated this project.
     */
    public void requestDeAllocation(String projectID, String studentID){
        this.baseRequestStudent(Integer.valueOf(projectID), "deRegister", studentID, "");
    }

    /**
     * With parameters given, initialises a new request that sets the request type to "changeSupervisor".
     * This method should only be used by supervisors as only supervisors can requst a change of supervisor
     * for their own projects.
     *
     * @param projectID ID of specific project supervisor wishes to change the supervisor ID of.
     * @param requesteeID current Supervisors ID.
     * @param newSupervisorID replacement Supervisor's ID.
     */
    public void requestTransfer(String projectID, String requesteeID, String newSupervisorID) {
        Request request = new Request(Integer.parseInt(projectID), "changeSupervisor", requesteeID, newSupervisorID);
        this.submitRequest(request);
    }

    /**
     * Adds request object specified to masterRequests ArrayList.
     *
     * @param request Request object you wish to add to masterRequest
     */
    public void submitRequest(Request request) {
        this.masterRequests.add(request);
    }

    /**
     * Rejects all remaining pending requests belonging to the supervisor whose ID is provided.
     *
     * @param supervisorID ID of supervisor whos remaining projects you wish to reject.
     */
    public void rejectAllRemainingProjects(String supervisorID) {
        ArrayList<Request> requests = this.getRequestsBySupervisorID(supervisorID, "pending");
        for (Request _request: requests) {
            _request.setStatus("rejected");
        }
    }

    /**
     * Method to process request according to what decision is chosen by the Coordinator. <br>
     * If coordinator chooses 'Y', the request will be approved and updated in the masterRequest using this method. <br>
     * If coordinator chooses 'N', the request will be rejected and updated in the masterRequest using this method.
     *
     * @param request Request object which Coordinator chooses to process
     * @param decision Decision of Coordinator to approve or reject the request, can only be 'Y' or 'N'.
     * @return True, if request if approved. False, if request is rejected.
     */
    public boolean processRequest(Request request, String decision) {
        if (Objects.equals(decision, "Y")) {
            request.setStatus("approved");
            if (Objects.equals(request.getType(), "register")) {

                boolean capReached = this.getProjectController().assignProject(request.getProjectID(), request.getRequesteeID());
                if (capReached) { //  reject all pending request for supervisor ID if cap reached
                    String supervisorID = this.getProjectController().getSupervisorIDFromProjectID(request.getProjectID());
                    this.rejectAllRemainingProjects(supervisorID);
                }
            } else if (Objects.equals(request.getType(), "changeTitle")) {
                this.getProjectController().changeTitle(request.getProjectID(), request.getUpdatedValue());
            } else if (Objects.equals(request.getType(), "deRegister")) {
                boolean capReached = this.getProjectController().unassignProject(request.getProjectID(), request.getRequesteeID());
            } else if (Objects.equals(request.getType(), "changeSupervisor")) {
                boolean capReached = this.getProjectController().changeSupervisor(request.getProjectID(), request.getUpdatedValue()); // checking if new supervisor's cap reached
                if (capReached) {
                    this.rejectAllRemainingProjects(request.getUpdatedValue());
                }
            }
            return true;
        } else {
            request.setStatus("rejected");
            if (Objects.equals(request.getType(), "register")) {
                this.centralManager.getProjectController().getProjectByID(request.getProjectID()).setProjectStatus(1);
            }

            return false;
        }
    }

    /**
     * Get method for all requests made (regardless of requestee, status, or type)
     *
     * @return ArrayList of all requests made
     */
    public ArrayList<Request> getAllRequests() {
        return this.masterRequests;
    }

    /**
     * Gets an ArrayList of requests of the specified type and status.
     *
     * @param type Request Type
     * @param status Request Status
     * @return ArrayList of Request with the specified status
     */
    public ArrayList<Request> getRequestByType(String type, String status) {
        ArrayList<Request> requests = new ArrayList<>();
        for (Request request: this.masterRequests) {
            if (Objects.equals(type, "all")) {
                if (Objects.equals(request.getStatus(), status)) {
                    requests.add(request);
                }
            } else {
                if (Objects.equals(request.getType(), type) && Objects.equals(request.getStatus(), status)) {
                    requests.add(request);
                }
            }
        }

        return requests;
    }

    /**
     * Gets an ArrayList of request of type "changeTitle" for projects supervised by the specified supervisor
     * ID and status.
     *
     * @param supervisorID the ID of the supervisor for the project
     * @param status the status of the request to get (use an empty string to get all statuses)
     * @return an ArrayList of Request objects of type "changeTitle" for projects supervised by the
     * specified supervisor ID and status
     */
    public ArrayList<Request> getRequestsBySupervisorID(String supervisorID, String status) {
        ArrayList<Request> requests = new ArrayList<>();
        for (Request request: this.masterRequests) {
            if (Objects.equals(request.getType(), "changeTitle")) { // ensures that only changeTitle requests are routed to supervisor, which also means that requests of types changeTitle, register or deRegister will be routed only to coordinator
                if (!Objects.equals(status, "")) { // if status is not empty string, used for getting requests of status for projects supervised by supervisorID
                    if (Objects.equals(request.getStatus(), status)) {
                        Integer projectID = request.getProjectID();
                        Project project = this.getProjectController().getProjectByID(projectID);
                        if (Objects.equals(project.getSupervisorID(), supervisorID)) {
                            requests.add(request);
                        }
                    }
                } else { // if status is empty string, used for getting all requests of projects supervised by supervisorID
                    Integer projectID = request.getProjectID();
                    Project project = this.getProjectController().getProjectByID(projectID);
                    if (Objects.equals(project.getSupervisorID(), supervisorID)) {
                        requests.add(request);
                    }
                }
            }

        }
        return requests;
    }

}
