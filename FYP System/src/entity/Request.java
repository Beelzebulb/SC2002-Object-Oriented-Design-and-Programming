package entity;
import java.time.LocalDate;

/**
 * Request class which is a blueprint to create Request objects from student, supervisor or coordinator.
 * @author Si Ming Zhou
 * @version 1.0
 * @since 2023-04-15
 */

public class Request {

    /**
     * The type of request [changeTitle, register, deRegister, changeSupervisor].
     */
    private String type;
    /**
     * The status of request [pending , approved , rejected]
     */
    private String status;
    /**
     * Date when request is sent
     */
    private LocalDate date;
    /**
     * Project ID attached with the request
     */
    private Integer projectID;
    /**
     * Requestee ID of supervisor/coordinator depending on type of request
     */
    private String requesteeID;
    /**
     *  Stored new title or new supervisor which requestor which to change to
     */
    private String updatedValue;

    /**
     * Constructs a request object using data from a file.
     * @param projectID    the ID of the project
     * @param type         the type of request (changeTitle, register, deRegister, changeSupervisor)
     * @param requesteeID  the ID of the user making the request
     * @param status       the status of the request (pending, approved, rejected)
     * @param date         the date the request was made
     * @param updatedValue the updated value for the project title or supervisor
     */
    public Request(Integer projectID, String type, String requesteeID, String status, LocalDate date, String updatedValue){
        this.type = type;
        this.projectID = projectID;
        this.requesteeID = requesteeID;
        this.status = status;
        this.updatedValue = updatedValue;
        this.date = date;
    }

    /**
     * Constructs a request object for a new request.
     * @param projectID   the ID of the project
     * @param type        the type of request (changeTitle, register, deRegister, changeSupervisor)
     * @param requesteeID the ID of the user making the request
     * @param updatedValue the updated value for the project title or supervisor
     */
    public Request(Integer projectID, String type, String requesteeID, String updatedValue){
        this.type = type;
        this.projectID = projectID;
        this.requesteeID = requesteeID;
        this.status = "pending";
        this.updatedValue = updatedValue;
        this.date = LocalDate.now();
    }

    /**
     * return the type of the request
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * return the updated value for the project title or supervisor
     * @return String
     */
    public String getUpdatedValue() {
        return this.updatedValue;
    }

    /**
     * return the ID of the project
     * @return Integer
     */
    public Integer getProjectID() {
        return this.projectID;
    }

    /**
     * return the ID of the user making the request
     * @return String
     */
    public String getRequesteeID() {
        return this.requesteeID;
    }

    /**
     * return the date the request was made
     * @return LocalDate
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * return the status of the request
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the request.
     * @param status the new status of the request
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

