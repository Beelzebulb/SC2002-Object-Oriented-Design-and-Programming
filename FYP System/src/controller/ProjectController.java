package controller;

import entity.CentralManager;
import entity.Project;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The ProjectController class manages the creation, modification, and retrieval of Project objects, as well as the assignment of projects to students and supervisors.
 * @author Khoo Yong Hui
 * @version 1.0
 * @since 2023-04-15
 */
public class ProjectController extends BaseController {
    /**
     * The list of projects managed by the system.
     */
    ArrayList<Project> masterProjects;

    /**
     * Constructs a ProjectController object with a reference to a CentralManager object.
     *
     * @param centralManager the CentralManager object to reference
     */
    public ProjectController(CentralManager centralManager) {
        super(centralManager);
        this.masterProjects = this.getCentralManager().getMasterProjects();
    }

    /**
     * Returns a list of all projects created by a given supervisor.
     *
     * @param supervisorID the ID of the supervisor
     * @return an ArrayList of Project objects created by the given supervisor
     */
    // all the projs created by supervisor
    public ArrayList<Project> getSubmittedProjects(String supervisorID) {
        ArrayList<Project> projects = new ArrayList<>();
        for (Project project: this.masterProjects) {
            if (Objects.equals(project.getCreatedBy(), supervisorID)) {
                projects.add(project);
            }
        }
        return projects;
    }

    /**
     * Sets the status of projects supervised by a given supervisor to "unavailable" when the supervisor has reached their maximum allowed number of projects to supervise.
     *
     * @param supervisorID the ID of the supervisor
     */
    public void setUnavailableProjects(String supervisorID) {
        ArrayList<Project> projects = this.getProjectsBySupervisorID(supervisorID);
        for (Project _project: projects) {
            if (_project.getProjectStatus() != -1) {
                _project.setProjectStatus(-2);
            }
        }
    }

    /**
     * Sets the status of projects supervised by a given supervisor to "available" when the supervisor has capacity to supervise more projects.
     *
     * @param supervisorID the ID of the supervisor
     */
    public void setAvailableProjects(String supervisorID) {
        ArrayList<Project> projects = this.getProjectsBySupervisorID(supervisorID);
        for (Project _project: projects) {
            if (_project.getProjectStatus() == -2) {
                _project.setProjectStatus(1);
            }
        }
    }

    /**
     * Checks if a project ID corresponds to an available project.
     *
     * @param projectID the ID of the project to validate
     * @return true if the project is available, false otherwise
     */
    public boolean validateAvailProjectID(Integer projectID) {
        ArrayList<Integer> projectIDs = this.getProjects("available");
        for (Integer project_id: projectIDs) {
            if (Objects.equals(project_id, projectID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Assigns a project to a student and updates its status accordingly.
     *
     * @param projectID the ID of the project to assign
     * @param studentID the ID of the student to assign the project to
     * @return true if the project's supervisor has reached their maximum allowed number of projects to supervise, false otherwise
     */
    public boolean assignProject(Integer projectID, String studentID) {
        Project project = this.getProjectByID(projectID);
        project.setProjectStatus(-1);
        project.setStudentID(studentID);
        this.getStudentController().assignProject(studentID, projectID);
        boolean capReached = this.getSupervisorController().reachedProjectCap(project.getSupervisorID());
        if (capReached) {
            this.setUnavailableProjects(project.getSupervisorID());
        }
        return capReached;
    }

    /**
     * Changes the title of a project.
     *
     * @param projectID the ID of the project to modify
     * @param newTitle the new title of the project
     */
    public void changeTitle(Integer projectID, String newTitle) {
        Project project = this.getProjectByID(projectID);
        project.setProjectTitle(newTitle);
    }

    /**
     * Returns a list of project IDs based on a chosen filter
     *
     * @param status the status of the projects to filter by
     * @param supervisorID the ID of the supervisor to filter by
     * @return an ArrayList of project IDs that match the given status and supervisor
     */
    public ArrayList<Integer> getProjectsByFilter(int status, String supervisorID) {
        ArrayList<Integer> projectIDs = new ArrayList<>();
        for (Project project: this.masterProjects) {
            if (status != -3) { // filter by status
                if (!Objects.equals(supervisorID, "!")) { // filter by project, "!" denotes all supervisors
                    if (project.getProjectStatus() == status && Objects.equals(project.getSupervisorID(), supervisorID)) { // filter by status & project
                        projectIDs.add(project.getProjectID());
                    }
                } else { // don't filter by supervisor
                    if (project.getProjectStatus() == status) { // filter by status only
                        projectIDs.add(project.getProjectID());
                    }
                }
            } else { // don't filter by status
                if (!Objects.equals(supervisorID, "!")) { // filter by supervisorID
                    if (Objects.equals(project.getSupervisorID(), supervisorID)) { // filter by supervisorID only
                        projectIDs.add(project.getProjectID());
                    }
                } else { // don't filter by anything
                    projectIDs.add(project.getProjectID());
                }
            }
        }
        return projectIDs;
    }

    /**
     * Changes the supervisor of a project.
     *
     * @param projectID the ID of the project to modify
     * @param newSupervisorID the ID of the new supervisor
     * @return true if the new supervisor has reached their maximum allowed number of projects to supervise, false otherwise
     */
    public boolean changeSupervisor(Integer projectID, String newSupervisorID) {
        Project project = this.getProjectByID(projectID);
        String oldSupervisorID = project.getSupervisorID();
        project.setSupervisorID(newSupervisorID);
        boolean oldCapReached = this.getSupervisorController().reachedProjectCap(oldSupervisorID);
        if (!oldCapReached) {
            this.setAvailableProjects(oldSupervisorID);
        }
        boolean newCapReached = this.getSupervisorController().reachedProjectCap(newSupervisorID);
        if (newCapReached) {
            this.setUnavailableProjects(newSupervisorID);
        }
        return newCapReached;
    }

    /**
     * Unassigns a project from a student and updates its status accordingly.
     *
     * @param projectID the ID of the project to unassign
     * @param studentID the ID of the student to unassign the project from
     * @return true if the project's supervisor has reached their maximum allowed number of projects to supervise, false otherwise
     */
    public boolean unassignProject(Integer projectID, String studentID) {
        Project project = this.getProjectByID(projectID);
        project.setProjectStatus(1);
        project.setStudentID("\"\"");
        this.getStudentController().unAssignProject(studentID);
        boolean capReached = this.getSupervisorController().reachedProjectCap(project.getSupervisorID());
        if (!capReached) {
            this.setAvailableProjects(project.getSupervisorID());
        }
        return capReached;
    }
    /**
    * Returns the supervisor ID associated with a given project ID.
    * @param projectID the ID of the project to retrieve the supervisor ID for
    * @return the supervisor ID associated with the given project ID, or null if no such project exists
     */
    public String getSupervisorIDFromProjectID(Integer projectID) {
        for (Project project: this.masterProjects) {
            if (project.getProjectID() == projectID) {
                return project.getSupervisorID();
            }
        }
        return null;
    }

    /**
     * Modifies the title of a project with a given ID, but only if the user requesting the modification is the supervisor who created the project.
     * @param projectID the ID of the project to modify
     * @param newTitle the new title to assign to the project
     * @param supervisorID the ID of the supervisor requesting the modification
     * @return true if the project title was successfully modified, false otherwise
     */
    public boolean modifyProjectTitle(Integer projectID, String newTitle, String supervisorID) {
        Project project = this.getProjectByID(projectID);
        if (Objects.equals(project.getCreatedBy(), supervisorID)) { // this check will only allow the supervisor who created that project to modify its title
            project.setProjectTitle(newTitle);
            return true;
        }
        return false;
    }
    /**
     * Returns the text representation of a given project status code.
     * @param statusCode the project status code to retrieve the text representation for
     * @return the text representation of the given project status code
     */
    public static String statusCode2Text(int statusCode) {
        if (statusCode == 1) {
            return "available";
        } else if (statusCode == 0) {
            return "reserved";
        } else if (statusCode == -1) {
            return "allocated";
        } else {
            return "unavailable";
        }
    }
    /**
     * Returns the next available project ID.
     * @return the next available project ID
     */
    public int nextProjectID(){
        int projectID = 0;
        for (Project project: this.masterProjects) {
            if (project.getProjectID() > projectID) {
                projectID = project.getProjectID();
            }
        }

        return projectID + 1;
    }
    /**
    * Creates a new project with the given parameters and adds it to the system.
    * @param supervisorID the ID of the supervisor who will be responsible for the new project
    * @param projectTitle the title of the new project
    * @param status the status code of the new project
     */
    public void createProject(String supervisorID, String projectTitle, int status) {
        Project project = new Project(this.nextProjectID(), supervisorID, "\"\"", projectTitle, status, supervisorID);
        this.masterProjects.add(project);
    }
    /**
     * Returns the project with the given ID.
     * @param projectID the ID of the project to retrieve
     * @return the project with the given ID, or null if no such project exists
     */
    public Project getProjectByID(Integer projectID) {
        for (Project project: this.masterProjects) {
            if (Objects.equals(project.getProjectID(), projectID)) {
                return project;
            }
        }
        return null;
    }
    /**
     * Returns a list of projects associated with a given supervisor ID.
     * @param supervisorID the ID of the supervisor to retrieve projects for
     * @return an Arraylist of projects associated with the given supervisor ID
     */
    public ArrayList<Project> getProjectsBySupervisorID(String supervisorID) {
        ArrayList<Project> projects = new ArrayList<>();
        for (Project project: this.masterProjects) {
            if (Objects.equals(project.getSupervisorID(), supervisorID)) {
                projects.add(project);
            }
        }
        return projects;
    }
    /**
     * Returns a list of project IDs of a given type.
     * @param type the type of project IDs to retrieve (either "available" or any other value to retrieve all projects)
     * @return an Arraylist of project IDs of the given type
     */
    public ArrayList<Integer> getProjects(String type) {
        ArrayList<Integer> projectList = new ArrayList<Integer>();
        for (Project project: this.centralManager.getMasterProjects()) {
            if (Objects.equals(type, "available")) {
                if (project.getProjectStatus() == 1) {
                    projectList.add(project.getProjectID());
                }
            }
            else {
                projectList.add(project.getProjectID());
            }
        }
        return projectList;
    }
}
