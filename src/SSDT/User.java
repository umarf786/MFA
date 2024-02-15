package SSDT;

import java.util.ArrayList;
// Import global users mock db
import static SSDT.Authentication.users;

public class User {
    // Creating attributes for each user
    private int id;
    private String name;
    private String username;
    private String password;
    private ArrayList<String> authMethods;

    // Constructor to set the attributes for each user instance
    public User (int id, String name, String username, String password, ArrayList<String> authMethods){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authMethods = authMethods;
        users.add(this);
    }

    // Method to get the id of a user
    public int getID(){
        return this.id;
    }

    // Method to get the username of a user
    public String getUsername(){
        return this.username;
    }

    // Method to get the password of a user
    public String getPassword(){
        return this.password;
    }

    // Method to get the auth methods
    public ArrayList<String> getAuthMethods(){
        return this.authMethods;
    }
}
