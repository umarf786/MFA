package SSDT;

// Import required utils
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Authentication {
    // Global users arraylist (mock db)
    public static ArrayList<User> users = new ArrayList<>();

    // Instantiate a scanner to take input from a user
    private final Scanner input = new Scanner(System.in);

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
                HashMap<String, ArrayList<String>> allAuthMethods = getAllAuthMethods();
                ArrayList<String> authMethods = allAuthMethods.get(username);
                System.out.println("Select an authentication method:");
                for (String method : authMethods){
                    System.out.println(method);
                }
                String selectedMethod = input.nextLine();

                // Run through the MFA process and return with a success or failure
                int res = checkMultiAuth(selectedMethod, authMethods);
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

    public int checkMultiAuth(String method, ArrayList<String> availableMethods){
        // Check if the provided method is available/selected for the given username
        // If so, continue with the process. If not, tell user and return
        if (availableMethods.contains(method)){
            if (method.equals("phone")){
                System.out.println("Calling your phone now with a code. Please enter it.");
            }
            if (method.equals("text")){
                System.out.println("Sending you a text now with a code. Please enter it.");
            }
            if (method.equals("app")){
                System.out.println("Open your auth app and find the code. Please enter it.");
            }
            if (method.equals("email")){
                System.out.println("Sending a code to your email now. Please enter it.");
            }

            String code = input.nextLine();
            if (code.equals("123")){
                System.out.println("MFA accepted");
                return 1;
            }
            else{
                System.out.println("Please enter a valid code next time, try again");
                return -2;
            }
        }
        else{
            System.out.println("Not a valid authentication method or this method is not available to you");
            return -1;
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

    public HashMap<String, ArrayList<String>> getAllAuthMethods(){
        // Initialise hashmap to store all usernames and their associated auth methods
        HashMap<String, ArrayList<String>> usernames_and_authmethods = new HashMap();

        // For each user in the mock db, add their username and auth methods to the hashmap
        for (User user : users){
            usernames_and_authmethods.put(user.getUsername(), user.getAuthMethods());
        }
        return usernames_and_authmethods;
    }
}
