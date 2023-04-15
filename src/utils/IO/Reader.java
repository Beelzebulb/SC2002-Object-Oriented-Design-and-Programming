package utils.IO;

import entity.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
/**
 * The Reader class contains methods for ingesting data from files and returning them as ArrayLists.
 * It includes methods for reading user data, project data, and request data from files.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */
public class Reader {
    /**
     * Object of class BufferedReader
     */
    BufferedReader reader;
    public Reader() {

    }
    /**
     * Reads user data from a file and returns an ArrayList of User objects.
     * Each line in the file should contain the following information separated by underscores:
     * - name (String)
     * - email (String)
     * - password (String)
     * - userType (String) - must be "Student", "Supervisor", or "Coordinator"
     * - projectID (int) - optional, only for students who are assigned to a project
     *
     * @param fpath the file path to read user data from
     * @return an ArrayList of all users
     */
    public static ArrayList<User> readUsers(String fpath) {
        System.out.println("Ingesting users...");
        ArrayList<User> users = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fpath));
            String line = reader.readLine();

            while (line != null) {
                String[] lst = line.split("_");
                String name = lst[0];
                String email = lst[1];
                String password = lst[2];
                String userType = lst[3];
                String userID = email.split("@")[0];
                int projectID = -1;
                if (lst.length == 5) {
                    projectID = Integer.parseInt(lst[4]);
                }
                if (Objects.equals(userType, "Student")) {
                    Student student = new Student(name, userID, email, password, "Student", projectID);
                    users.add(student);
                }
                else if (Objects.equals(userType, "Supervisor")){
                    Supervisor supervisor = new Supervisor(name, userID, email, password, "Supervisor");
                    users.add(supervisor);
                }
                else if (Objects.equals(userType, "Coordinator")){
                    Coordinator coordinator = new Coordinator(name, userID, email, password, "Coordinator");
                    users.add(coordinator);
                    Supervisor supervisor = new Supervisor(name, userID, email, password, "Supervisor");
                    users.add(supervisor);
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    /**
     * Reads request data from a file and returns an ArrayList of Request objects.
     * Each line in the file should contain the following information separated by underscores:
     * - type (String) - What type of request it is. Must be "changeTitle", "register", "deRegister", or "ChangeSupervisor"
     * - status (String) - What is the status of the request. Must be "Pending", "Approved", or "Rejected"
     * - date (String) - Date of the request made. In the format yyyy-mm-dd
     * - projectID (int) - What project the request is referring to
     * - requesteeID (String) - the ID of the user who made the request
     * - value (String) - To contain inputs, such as new titles and replacement supervisor ID
     *
     * @param fpath the file path to read request data from
     * @return an ArrayList of Request objects
     */
    public static ArrayList<Request> readRequests(String fpath) {
        System.out.println("Ingesting requests...");
        ArrayList<Request> requests = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fpath));
            String line = reader.readLine();

            while (line != null) {
                String[] lst = line.split("_");
                String type = lst[0];
                String status = lst[1];
                String date = lst[2];
                Integer projectID = Integer.valueOf(lst[3]);
                String requesteeID = lst[4];
                String value = "";
                if (lst.length == 6) {
                    value = lst[5];
                }
                Request request = new Request(projectID, type, requesteeID, status, LocalDate.parse(date), value);
                requests.add(request);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requests;
    }
    /**
     * Reads project data from a file and returns an ArrayList of Project objects.
     * Each line in the file should contain the following information separated by underscores:
     * - projectID (int) - ID of the project
     * - supervisorID (String) - the ID of the supervisor assigned to the project
     * - studentID (String) - the ID of the student assigned to the project
     * - projectTitle (String) - title of the project
     * - projectStatus (int) - must be -2, -1, 0, or 1 (representing "unavailable", "allocated", "reserved", or "available")
     * - createdBy (String) - the ID of the supervisor who created the project
     *
     * @param fpath the file path to read project data from
     * @return an ArrayList of Project objects
     */
    public static ArrayList<Project> readProjects(String fpath) {
        System.out.println("Ingesting projects...");
        ArrayList<Project> projects = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fpath));
            String line = reader.readLine();

            while (line != null) {
                String[] lst = line.split("_");
                int projectID = Integer.parseInt(lst[0]);
                String supervisorID = lst[1];
                String studentID = lst[2];
                String projectTitle = lst[3];
                int projectStatus = Integer.parseInt(lst[4]);
                String createdBy = lst[5];
                Project project = new Project(projectID, supervisorID, studentID, projectTitle, projectStatus, createdBy);
                projects.add(project);


                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
