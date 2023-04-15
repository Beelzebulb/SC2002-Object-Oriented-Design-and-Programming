package controller;

import entity.*;

import java.util.ArrayList;

/**
 * Student's controller to process inputs obtained from the Student's Boundary. Only accessible by a Student user.
 * @author Si Ming Zhou
 * @version 1.0
 * @since 2023-04-15
 */
public class StudentController extends  UserController {
    /**
     * Constructs a new StudentController with a CentralManager object.
     * @param centralManager The CentralManager object to be used for this controller.
     */
    public StudentController(CentralManager centralManager){
        super(centralManager);
    }

    /**
    * Retrieves the current Student object.
    * @return The current Student object.
     */
    public Student getCurrentStudent() {
        return (Student) this.currentUser;
    }

    /**
     * Retrieves the registration status of the current student.
     * @return The registration status of the current student.
     */
    public boolean getRegistered() {
        return this.getCurrentStudent().getRegistered();
    }

    /**
     * Retrieves the Student object associated with the specified ID.
     * @param studentID The ID of the Student object to retrieve.
     * @return The Student object associated with the specified ID.
     */
    public Student getStudentByID(String studentID) {
        return (Student) this.getUserByID(studentID);
    }

    /**
     * Assigns a project to the specified student.
     * @param studentID The ID of the student to assign the project to.
     * @param projectID The ID of the project to be assigned.
     */
    public void assignProject(String studentID, Integer projectID) {
        Student student = (Student) this.getUserByID(studentID);
        student.setRegisteredProject(projectID);
    }

    /**
     * Unassigns the project currently registered by the specified student.
     * @param studentID The ID of the student to unassign the project from.
     */
    public void unAssignProject(String studentID) {
        Student student = (Student) this.getUserByID(studentID);
        student.setRegisteredProject(-2);
    }

    /**
     * Retrieves the ID of the project currently registered by the current student.
     * @return The ID of the project currently registered by the current student.
     */
    public Integer getRegisteredProject() {
        return this.getCurrentStudent().getRegisteredProject();
    }

    /**
    * Sets a new password for the current student.
    * @param newPassword The new password to set.
     */
    public void setPassword(String newPassword){
        this.getCurrentStudent().setPassword(newPassword);
    }

    /**
     * Sends a request to change the title of a project by calling the request controller.
     * @param newTitle The new title to be set.
     * @param studentID The ID of the student sending the request.
     * @param projectID The ID of the project to change the title of.
     */
    public void requestChangeTitle(String newTitle, String studentID, String projectID){
        this.getRequestController().requestChangeTitle(newTitle, studentID, projectID);
    }

    /**
     * Sends a request for project allocation by calling request controller. Also, update project status to 0(reserved) by calling project controller.
     * @param projectID The ID of the project to be allocated.
     * @param studentID The ID of the student sending the request.
     */
    public void requestAllocation(Integer projectID, String studentID){
        this.getRequestController().requestAllocation(projectID.toString(), studentID);
        this.getProjectController().getProjectByID(projectID).setProjectStatus(0);
    }

    /**
     * Sends a request for project deallocation by calling request controller.
     * @param projectID The ID of the project to be deallocated.
     * @param studentID The ID of the student sending the request.
     */
    public void requestDeAllocation(String projectID, String studentID){
        this.getRequestController().requestDeAllocation(projectID, studentID);
    }

    /**
     * Retrieves the request history for the current student by calling request controller.
     * @return an ArrayList of Request
     */
    public ArrayList<Request> getRequestsHistory() {
        return this.getRequestController().getRequestsByUserID(this.getCurrentStudent().getId());
    }
}
