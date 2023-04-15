package utils.IO;

import entity.Project;
import entity.Request;
import entity.Student;
import entity.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
/**
 * Writer is a utility class that provides static methods for writing various types of objects to file.
 * It currently supports writing users, requests and projects to files.
 * Each write method takes in a file path and an ArrayList of the corresponding objects to be written to file.
 * @author Esther Teo Gek Wat
 * @version 1.0
 * @since 2023-04-15
 */

public class Writer {
    /**
     * Writes the list of users to file at the specified file path.
     * The format of each line in the file is as follows:
     * [name][email][password][type][registeredProject]
     * where [type] is one of "Supervisor", "Coordinator" or "Student", and [registeredProject] is only present for Student users.
     * @param fpath the file path to retrieve the user.txt file where the users will be written
     * @param masterUsers the list of User objects to be written to file
     */
    public static void writeUsers(String fpath, ArrayList<User> masterUsers) {
        System.out.println("Saving changes to user file...");
        try {
            FileWriter writer = new FileWriter(fpath);
            ArrayList<String> coordinators = new ArrayList<>();
            for (User user: masterUsers) {
                if (!coordinators.contains(user.getId())) {
                    String toWrite = "";
                    if (Objects.equals(user.getType(), "Student")) {
                        Student student = (Student) user;
                        toWrite = user.getName() + '_' + user.getEmail() + '_' + user.getPassword() + '_' + user.getType() + "_" +  student.getRegisteredProject() + '\n';
                    } else {
                        toWrite = user.getName() + '_' + user.getEmail() + '_' + user.getPassword() + '_' + user.getType() + '\n';
                    }
                    writer.write(toWrite);
                    if (Objects.equals(user.getType(), "Coordinator")) {
                        coordinators.add(user.getId());
                    }
                }

            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /**
     * Writes the list of requests to file at the specified file path.
     * The format of each line in the file is as follows:
     * [type][status][date][projectID][requesteeID]_[updatedValue]
     * where [type] specifies what request it is, and [status] is one of "Pending", "Approved" or "Rejected".
     *  @param fpath the file path to retrieve the request.txt file where the requests will be written
     *  @param masterRequests the list of Request objects to be written to file
     */
    public static void writeRequests(String fpath, ArrayList<Request> masterRequests) {
        System.out.println("Saving changes to requests file...");
        try {
            FileWriter writer = new FileWriter(fpath);
            for (Request request: masterRequests) {
                String toWrite = request.getType() + '_' + request.getStatus() + '_' + request.getDate() + '_' + request.getProjectID() + '_' +  request.getRequesteeID() + '_' + request.getUpdatedValue() + '\n';
                writer.write(toWrite);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /**

   *  Writes the list of projects to file at the specified file path.
   *  The format of each line in the file is as follows:
   *  [projectID][supervisorID][studentID][projectTitle][projectStatus][createdBy]
   *  where [projectStatus] specifies whether a project is "allocated", "unavailable", "reserved" or "available"
   *  and [createdBy] is the supervisor ID that created the project
   * @param fpath the file path where the projects will be written
   * @param masterProjects the list of Project objects to be written to file
     */
    public static void writeProjects(String fpath, ArrayList<Project> masterProjects) {
        System.out.println("Saving changes to projects file...");
        try {
            FileWriter writer = new FileWriter(fpath);
            for (Project project: masterProjects) {
                String toWrite = project.getProjectID() + "_" + project.getSupervisorID() + "_" + project.getStudentID() + "_" + project.getProjectTitle() + "_" +  project.getProjectStatus() + "_" + project.getCreatedBy() + '\n';
                writer.write(toWrite);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
