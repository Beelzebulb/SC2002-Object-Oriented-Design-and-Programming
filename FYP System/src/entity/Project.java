package entity;
/**
 * A class representing a Project entity in the system.
 * @author Khoo Yong Hui
 * @version 1.0
 * @since 2023-04-15
 */
public class Project {
    /**
     * projectID (int) - ID of the project
     */
    private int projectID;
    /**
     * projectTitle (String) - title of the project
     */
    private String projectTitle;
    /**
     * supervisorID (String) - the ID of the supervisor assigned the project
     */
    private String supervisorID;
    /**
     * studentID (String) - the ID of the student assigned to the project
     */
    private String studentID;
    /**
     * projectStatus (int) - must be -2, -1, 0, or 1 (representing "unavailable", "allocated", "reserved", or "available")
     */
    private int projectStatus;
    /**
     * createdBy (String) - the ID of the supervisor who created the project
     */
    private String createdBy;

    /**
     * Constructs a new Project object with the given parameters.
     * @param projectID the ID of the project
     * @param supervisorID the ID of the supervisor assigned to the project
     * @param studentID the ID of the student assigned to the project
     * @param projectTitle the title of the project
     * @param projectStatus the status of the project (available, reserved, unavailable or allocated)
     * @param createdBy the ID of the supervisor who created the project
     */
    public Project(int projectID, String supervisorID, String studentID, String projectTitle, int projectStatus, String createdBy) {
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.supervisorID = supervisorID;
        this.projectStatus = projectStatus;
        this.studentID = studentID;
        this.createdBy = createdBy;
    }

    /**
     * return the ID of the supervisor assigned to the project
     * @return String
     */
    public String getSupervisorID() {
        return this.supervisorID;
    }
    /**
     * return the ID of the project
     * @return Integer
     */
    public int getProjectID() {
        return this.projectID;
    }

    /**
     * return the title of the project
     * @return String
     */
    public String getProjectTitle() {
        return this.projectTitle;
    }

    /**
     * return the status of the project (available, reserved, unavailable or allocated)
     * @return Integer
     */
    public int getProjectStatus() {
        return this.projectStatus;
    }
    /**
     * return the ID of the student assigned to the project
     * @return String
     */
    public String getStudentID() {
        return this.studentID;
    }

    /**
     * return the ID of the supervisor who created the project
     * @return String
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * Sets the title of the project.
     * @param projectTitle the new title of the project
     */
    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
    /**
     * Sets the status of the project.
     * @param projectStatus the new status of the project (available, reserved, unavailable or allocated)
     */
    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }
    /**
     * Sets the ID of the student assigned to the project.
     * @param studentID the new ID of the student assigned to the project
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    /**
     * Sets the ID of the supervisor supervising the project.
     * @param newSupervisorID the new ID of the supervisor supervising the project
     */
    public void setSupervisorID(String newSupervisorID) {
        this.supervisorID = newSupervisorID;
    }
}
