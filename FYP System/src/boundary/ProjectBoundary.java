package boundary;

import controller.ProjectController;
import controller.SupervisorController;
import entity.CentralManager;
import entity.Project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This Project Boundary is responsible for retrieving inputs and displaying outputs from its project controller.
 * @author Khoo Yong Hui
 * @version 1.0
 * @since 2023-04-15
 */
public class ProjectBoundary extends BaseBoundary {

    /**
     * Creates a new project boundary
     * @param centralManager This is the central manager which contains all the data
     */
    public ProjectBoundary(CentralManager centralManager) {
        super(centralManager);
    }

    /**
     * Displays a single line of project information in the specific format
     *
     * @param projectID The ID of the project to display
     */
    public void viewProjectLine(Integer projectID) {
        Project project = this.getProjectController().getProjectByID(projectID);
        String supervisorName = this.getSupervisorController().getSupervisorByID(project.getSupervisorID()).getName();
        String projectStatus = ProjectController.statusCode2Text(project.getProjectStatus());
        System.out.println("[" + project.getProjectID() + "]" + " | " + project.getProjectTitle() + " | " + supervisorName + " | " + projectStatus);
    }

    /**
     * Prints the project format header for display
     */
    public void printProjectFormat() {
        System.out.println("[Project ID] | Project Title | Supervisor Name | Project Status");
    }

    /**
     * Modifies the title of a project
     *
     * @param supervisorID The ID of the supervisor making the change
     */
    public void modifyProjectTitle(String supervisorID) {
        int projectID = this.getInt("Please choose a project to modify the title for by entering its Project ID: ");
        String newTitle = this.getLine("Input New Project Title: ");
        boolean success = this.getProjectController().modifyProjectTitle(projectID, newTitle, supervisorID);
        if (success) {
            System.out.println("Project Title changed successfully.");
        } else {
            System.out.println("Unable to change title as user not the creator of project.");
        }
    }

    /**
     * Displays a list of projects filtered by their availability and supervisor
     *
     * @param supervisorID The ID of the supervisor to filter by
     * @param type         The type of projects to display (available or all)
     */
    public void viewProjectsBySupervisorID(String supervisorID, String type) {
        ArrayList<Integer> projects = this.getProjectController().getProjects(type);
        if (Objects.equals(type, "available")) {
            System.out.println("Available Projects:");
            this.printProjectFormat();
            for (Integer projectID: projects) {
                Project project = this.getProjectController().getProjectByID(projectID);
                if (project.getProjectStatus() == 1 && Objects.equals(project.getSupervisorID(), supervisorID)) {
                    this.viewProjectLine(projectID);
                }
            }
        } else if (Objects.equals(type, "all")) {
            System.out.println("All Projects:");
            this.printProjectFormat();
            for (Integer projectID: projects) {
                Project project = this.getProjectController().getProjectByID(projectID);
                if (Objects.equals(project.getSupervisorID(), supervisorID)) {
                    this.viewProjectLine(projectID);
                }
            }
        }
    }

    /**
     * Displays a list of projects filtered by their status and supervisor
     *
     * @param status       The status to filter by
     * @param supervisorID The ID of the supervisor to filter by
     */
    public void displayProjectsWithFilter(int status, String supervisorID) {
        ArrayList<Integer> projectIDs = this.getProjectController().getProjectsByFilter(status, supervisorID);
        for (int projectID: projectIDs) {
            this.viewProjectLine(projectID);
        }
    }
    /**
     * This method is responsible for displaying a list of projects based on the specified type.
     * If type equals "available", this method will display a list of available projects.
     * If type equals "all", this method will display a list of all projects.
     * @param type a string that specifies the type of projects to display
     */
    public void viewProjects(String type) {
        ArrayList<Integer> projects = this.getProjectController().getProjects(type);
        if (Objects.equals(type, "available")) {
            System.out.println("Available Projects:");
            this.printProjectFormat();
            for (Integer projectID: projects) {
                Project project = this.getProjectController().getProjectByID(projectID);
                if (project.getProjectStatus() == 1) {
                    this.viewProjectLine(projectID);
                }
            }
        } else if (Objects.equals(type, "all")) {
            System.out.println("All Projects:");
            this.printProjectFormat();
            for (Integer projectID: projects) {
                this.viewProjectLine(projectID);
            }
        }
    }
}
