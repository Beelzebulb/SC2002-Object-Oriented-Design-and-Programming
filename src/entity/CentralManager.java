package entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import boundary.*;
import controller.*;

import utils.IO.Reader;
import utils.IO.Writer;
import utils.Input;

/**
 * The CentralManager class represents the main entity of the system.
 * It stores all the data of users, projects, and requests in corresponding ArrayLists, and allows the boundaries and controllers
 * to be initialized once for the entire program. The class also provides methods for ingesting and
 * writing data to files, as well as getters for accessing the master arrays, boundaries and controllers.
 * @author Khoo Yong Hui
 * @version 1.0
 * @since 2023-04-15
 */

public class CentralManager {

    // Master Arrays
    /**
     * Creates an ArrayList to store all user objects, including students, supervisor and coordinators
     */
    private ArrayList<User> MasterUsers;
    /**
     * Creates an ArrayList to store all project objects
     */
    private ArrayList<Project> MasterProjects;
    /**
     * Creates an ArrayList to store all request objects
     */
    private ArrayList<Request> MasterRequests;

    // Boundary & Controllers
    /**
     * Creates an instance of the user boundary class
     */
    private UserBoundary userBoundary;
    /**
     * Creates an instance of the user controller class
     */
    private UserController userController;

    /**
     * Creates an instance of the student boundary class
     */
    private StudentBoundary studentBoundary;

    /**
     * Creates an instance of the student controller class
     */
    private StudentController studentController;

    /**
     * Creates an instance of the supervisor boundary class
     */
    private SupervisorBoundary supervisorBoundary;

    /**
     * Creates an instance of the supervisor controller class
     */
    private SupervisorController supervisorController;

    /**
     * Creates an instance of the coordinator boundary class
     */
    private CoordinatorBoundary coordinatorBoundary;

    /**
     * Creates an instance of the coordinator controller class
     */
    private CoordinatorController coordinatorController;

    /**
     * Creates an instance of the project boundary class
     */
    private ProjectBoundary projectBoundary;

    /**
     * Creates an instance of the project controller class
     */
    private ProjectController projectController;

    /**
     *Creates an instance of the request boundary class
     */
    private RequestBoundary requestBoundary;

    /**
     * Creates an instance of the request controller class
     */
    private RequestController requestController;

    /**
     * Creates an instance of the string to contain the user's current working directory.
     * This allows any user to use the system without having to change their file path
     */
    private String currentWorkingDirectory;

    /**
     * Creates an instance of the Input class
     */
    private Input input;

    /**
     * Creates an instance of the Scanner class
     */
    private Scanner sc;

    /**
     * Constructor for the CentralManager class.
     * Initializes the master arrays, ingests data from files, creates instances of all controllers and boundaries,
     * and adds a shutdown hook to write the data back to files when the program terminates.
     */
    // stores all the data of Users, Projects, Requests
    public CentralManager () {
        // Current Working Directory
        /**
         * Retrieves the user's directory when they are running the system.
         */
        this.currentWorkingDirectory = System.getProperty("user.dir") + '/';

        // Master Arrays
        /**
         * Initialises the master array to contain all users objects
         */
        this.MasterUsers = new ArrayList<User>();
        /**
         * Initialises the master array to contain all project objects
         */
        this.MasterProjects = new ArrayList<Project>();
        /**
         * Initialises the master array to contain all request objects
         */
        this.MasterRequests = new ArrayList<Request>();

        // Ingest Files
        /**
         * Runs the ingest projects method defined in this class to ingest all projects into the central manager class.
         */
        this.ingestProjects();
        /**
         * Runs the ingest users method defined in this class to ingest all users into the central manager class.
         */
        this.ingestUsers();
        /**
         * Runs the ingest requests method defined in this class to ingest all requests into the central manager class.
         */
        this.ingestRequests();

        // Scanner
        /**
         * Initialises the Scanner
         */
        this.sc = new Scanner(System.in);
        /**
         * Initialises the input
         */
        this.input = new Input(this.sc);


        // Initialize Controllers
        /**
         * Initialises the project controller
         */
        this.projectController = new ProjectController(this);
        /**
         * Initialises the request controller
         */
        this.requestController = new RequestController(this);
        /**
         * Initialises the user controller
         */
        this.userController = new UserController(this);
        /**
         * Initialises the student controller
         */
        this.studentController = new StudentController(this);
        /**
         * Initialises the supervisor controller
         */
        this.supervisorController = new SupervisorController(this);
        /**
         * Initialises the coordinator controller
         */
        this.coordinatorController = new CoordinatorController(this);


        // Initialize Boundaries
        /**
         * Initialises the request boundary
         */
        this.requestBoundary = new RequestBoundary(this);
        /**
         * Initialises the project boundary
         */
        this.projectBoundary = new ProjectBoundary(this);
        /**
         * Initialises the student boundary
         */
        this.studentBoundary = new StudentBoundary(this);
        /**
         * Initialises the user boundary
         */
        this.userBoundary = new UserBoundary(this);
        /**
         * Initialises the supervisor boundary
         */
        this.supervisorBoundary = new SupervisorBoundary(this);
        /**
         * Initialises the coordinator boundary
         */
        this.coordinatorBoundary = new CoordinatorBoundary(this);

        /**
         * Initialises each project's student ID
         */
        for (Project project: this.MasterProjects) {
            if (!Objects.equals(project.getStudentID(), "\"\"")) {
                this.getStudentController().getStudentByID(project.getStudentID()).setRegisteredProject(project.getProjectID());
            }
        }

        /**
         * When the user wants to end the program, the program will write all the latest data back to their respective text files.
         */
        // Add ShutdownHook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Running shutdown routine...");
            this.writeUsers();
            this.writeRequests();
            this.writeProjects();
        }));
    }

    // Ingest Files
    /**
     * Loads user data from the Users.txt by calling the Reader class method and stores it in the MasterUsers arraylist.
     */
    public void ingestUsers() {
        String fpath = this.currentWorkingDirectory + "data/Users.txt";
        this.MasterUsers = Reader.readUsers(fpath);
    }
    /**
     * Loads project data from the Projects.txt by calling the Reader class method and stores it in the MasterProjects arraylist.
     */
    public void ingestProjects() {
        String fpath = this.currentWorkingDirectory + "data/Projects.txt";
        this.MasterProjects = Reader.readProjects(fpath);
    }
    /**
     * Loads request data from the Requests.txt by calling the Reader class method and stores it in the MasterRequests arraylist.
     */
    public void ingestRequests() {
        String fpath = this.currentWorkingDirectory + "data/Requests.txt";
        this.MasterRequests = Reader.readRequests(fpath);
    }

    // Write files
    /**
     * Writes user data to the Users.txt by calling the Writer class method.
     */
    public void writeUsers() {
        String fpath = this.currentWorkingDirectory + "data/Users.txt";
        Writer.writeUsers(fpath, this.MasterUsers);
    }
    /**
     * Writes project data to the Projects.txt by calling the Writer class method.
     */
    public void writeProjects() {
        String fpath = this.currentWorkingDirectory + "data/Projects.txt";
        Writer.writeProjects(fpath, this.MasterProjects);
    }
    /**
     * Writes request data to the Requests.txt by calling the Writer class method.
     */
    public void writeRequests() {
        String fpath = this.currentWorkingDirectory + "data/Requests.txt";
        Writer.writeRequests(fpath, this.MasterRequests);
    }


    // Boundaries
    /**
     * @return the user boundary
     */
    public UserBoundary getUserBoundary(){return this.userBoundary;}
    /**
     * @return the student boundary
     */
    public StudentBoundary getStudentBoundary(){return this.studentBoundary;}
    /**
     * @return the project boundary
     */
    public ProjectBoundary getProjectBoundary(){
        return this.projectBoundary;
    }
    /**
     * @return the request boundary
     */
    public RequestBoundary getRequestBoundary() { return this.requestBoundary;}
    /**
     * @return the coordinator boundary
     */
    public CoordinatorBoundary getCoordinatorBoundary() {
        return this.coordinatorBoundary;
    }
    /**
     * @return the supervisor boundary
     */
    public SupervisorBoundary getSupervisorBoundary() {
        return this.supervisorBoundary;
    }

    // Get Master Arrays
    /**
     * @return the master projects ArrayList which contains all the projects
     */
    public ArrayList<Project> getMasterProjects() {
        return this.MasterProjects;
    }

    /**
     * @return the master request ArrayList which contains all the requests
     */
    public ArrayList<Request> getMasterRequests() {
        return this.MasterRequests;
    }
    /**
     * @return the master user ArrayList which contains all the users
     */
    public ArrayList<User> getMasterUsers() {
        return this.MasterUsers;
    }


    // Get input
    /**
     * @return the input from users
     */
    public Input getInput() {
        return this.input;
    }


    // Get Controllers
    /**
     * @return the user controller
     */
    public UserController getUserController() {
        return this.userController;
    }
    /**
     * @return the student controller
     */
    public StudentController getStudentController(){
        return this.studentController;
    }
    /**
     * @return the coordinator controller
     */
    public CoordinatorController getCoordinatorController() {
        return this.coordinatorController;
    }
    /**
     * @return the project controller
     */
    public ProjectController getProjectController() {
        return this.projectController;
    }
    /**
     * @return the supervisor controller
     */
    public SupervisorController getSupervisorController() {
        return this.supervisorController;
    }
    /**
     * @return the request controller
     */
    public RequestController getRequestController() {
        return this.requestController;
    }
}

