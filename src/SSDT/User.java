package SSDT;

import java.util.ArrayList;

/**
 * Represents a user in the system.
 */
public class User {
    // Global users arraylist (mock db)
    public static ArrayList<User> users = new ArrayList<>();

    // Creating attributes for each user
    private int id;
    private String name;
    private String username;
    private String password;
    private ArrayList<String> authMethods;

    /**
     * Constructor to initialize a User object with provided attributes.
     *
     * @param id           The unique identifier for the user.
     * @param name         The full name of the user.
     * @param username     The username of the user.
     * @param password     The password of the user.
     * @param authMethods  The list of authentication methods associated with the user.
     */
    public User(int id, String name, String username, String password, ArrayList<String> authMethods) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authMethods = authMethods;
        users.add(this);
    }

    /**
     * Retrieves the ID of the user.
     *
     * @return The ID of the user.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Retrieves the list of authentication methods associated with the user.
     *
     * @return The list of authentication methods.
     */
    public ArrayList<String> getAuthMethods() {
        return this.authMethods;
    }
}
