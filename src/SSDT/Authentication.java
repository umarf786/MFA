package SSDT;

// Import required utils
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Authentication {
    // Global users arraylist (mock db)
    public static ArrayList<User> users = new ArrayList<>();
    // Instantiate a scanner to take input from a user
    public Scanner input = new Scanner(System.in);
    MultiAuth MultiAuth = new MultiAuth(input);

    // Method to handle a user sign in
    public int signIn(){
        System.out.println("Gimme dat username");
        String username = input.nextLine();

        // Call private method to get all usernames and password data for users as a hashmap
        HashMap<String, String> usernames_and_passwords = getAllUsernamesAndPasswords();

        // If the username is valid, go through the login process, else return an error
        if (usernames_and_passwords.containsKey(username)) {
            // If username is found, ask for the password
            System.out.println("Gimme dat password");
            String password = input.nextLine();
            if (usernames_and_passwords.get(username).equals(password)) {
                //If password is correct, get all authentication methods available/selected for the user
                HashMap<String, ArrayList<String>> allAuthMethods = MultiAuth.getAllAuthMethods();
                ArrayList<String> authMethods = allAuthMethods.get(username);
                System.out.println("Select an authentication method:");
                for (String method : authMethods){
                    System.out.println(method);
                }
                String selectedMethod = input.nextLine();

                // Run through the MFA process and return with a success or failure
                int res = MultiAuth.checkMultiAuth(selectedMethod, authMethods);
                if (res == 1){
                    System.out.println("Logged in!");
                    return 1;
                } else if (res == -2){
                    System.out.println("Issue with MFA, could not log you in");
                    return -4;
                }
                else{
                    return -5;
                }

            } else {
                // If password is incorrect
                System.out.println("Password is wrong");
                return -3;
            }
        } else {
            // If no username is found
            System.out.println("No user found with that username");
            return -2;
        }
    }

    public HashMap<String, String> getAllUsernamesAndPasswords(){
        // Initialise hashmap to store all usernames and their associated passwords
        HashMap<String, String> usernames_and_passwords = new HashMap();

        // For each user in the mock db, add their username and passwords to the hashmap
        for (User user : users){
            usernames_and_passwords.put(user.getUsername(), user.getPassword());
        }
        return usernames_and_passwords;
    }
}
