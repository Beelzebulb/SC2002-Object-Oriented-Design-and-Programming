package fyp;
/**
 This is the main class which starts the entire FYP system. This includes initialising all the data, boundaries and controllers.
 To run the system, it creates an instance of the FYPSystem class and call its run() method.
 @author Esther Teo Gek Wat
 @version 1.0
 @since 2023-04-15
 */
public class Main {
    /**
     * Main method creates an instance of FYPSystem and runs it.
     * @param args contains the supplied command-line arguments as an array of strings
     */
    public static void main(String[] args) {
        FYPSystem fypSystem = new FYPSystem();
        fypSystem.run();
    }

}
