package entity;

/**
 * Coordinator Entity is a subclass of Supervisor as the Coordinator is also a Supervisor, hence would inherit the Supervisor class and its attributes accordingly.
 * The Coordinator Entity does not require additional attributes as she mainly just needs to approve/ reject requests and viewing of information would be of the respective master array.
 * @author Jeremy
 * @version 1.0
 * @since 15/4/2023
 */
public class Coordinator extends Supervisor{
    /**
     * Constructs a new instance of the coordinator object.
     *
     * @param name name of the coordinator (string)
     * @param id id of the coordinator (string)
     * @param email email of the coordinator (string)
     * @param password password of the coordinator to log in (string)
     * @param type type of user - coordinator
     */
    public Coordinator(String name,String id,String email,String password, String type) {
        super(name, id, email, password, type);
    }
}
