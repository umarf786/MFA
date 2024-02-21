package SSDT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Custom exception classes
class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}

class MultiAuthException extends AuthenticationException {
    public MultiAuthException(String message) {
        super(message);
    }
}

public class Authentication {
    // Global users arraylist (mock db)
    public static ArrayList<User> users = new ArrayList<>();

    // Instantiate a scanner to take input from a user
    private final Scanner input = new Scanner(System.in);

    // Method to handle a user sign-in
    public void signIn() {
        System.out.println("Enter your username:");
        String username = input.nextLine();

        try {
            // Attempt to authenticate the user
            authenticateUser(username);
            System.out.println("Logged in successfully!");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }

    // Private method to authenticate the user
    public void authenticateUser(String username) throws AuthenticationException {
        HashMap<String, String> usernamesAndPasswords = getAllUsernamesAndPasswords();

        if (usernamesAndPasswords.containsKey(username)) {
            System.out.println("Enter your password:");
            String password = input.nextLine();

            if (usernamesAndPasswords.get(username).equals(password)) {
                // If password is correct, proceed with multi-factor authentication
                HashMap<String, ArrayList<String>> allAuthMethods = getAllAuthMethods();
                ArrayList<String> authMethods = allAuthMethods.get(username);
                System.out.println("Select an authentication method:");

                for (String method : authMethods) {
                    System.out.println(method);
                }

                String selectedMethod = input.nextLine();
                checkMultiAuth(selectedMethod, authMethods);
            } else {
                throw new AuthenticationException("Incorrect password");
            }
        } else {
            throw new AuthenticationException("User not found");
        }
    }

    // Private method to handle multi-factor authentication
    public void checkMultiAuth(String method, ArrayList<String> availableMethods) throws MultiAuthException {
        if (availableMethods.contains(method)) {
            // Additional steps based on the selected authentication method
            performAuthenticationSteps(method);

            // Assuming the authentication process is successful
            System.out.println("Multi-factor authentication accepted");
        } else {
            throw new MultiAuthException("Invalid or unavailable authentication method");
        }
    }

    // Private method to simulate additional authentication steps based on the selected method
    public void performAuthenticationSteps(String method) throws MultiAuthException {
        switch (method) {
            case "phone":
                System.out.println("Calling your phone now with a code. Please enter it.");
                break;
            case "text":
                System.out.println("Sending you a text now with a code. Please enter it.");
                break;
            case "app":
                System.out.println("Open your auth app and find the code. Please enter it.");
                break;
            case "email":
                System.out.println("Sending a code to your email now. Please enter it.");
                break;
            // Add more cases as needed for additional authentication methods
        }

        // Simulate entering the authentication code
        String code = input.nextLine();
        if (!code.equals("123")) {
            throw new MultiAuthException("Invalid code. Please enter a valid code next time.");
        }
    }

    // Private method to retrieve usernames and passwords from the mock database
    public HashMap<String, String> getAllUsernamesAndPasswords() {
        HashMap<String, String> usernamesAndPasswords = new HashMap();

        // For each user in the mock db, add their username and passwords to the hashmap
        for (User user : users) {
            usernamesAndPasswords.put(user.getUsername(), user.getPassword());
        }
        return usernamesAndPasswords;
    }

    // Private method to retrieve usernames and authentication methods from the mock database
    public HashMap<String, ArrayList<String>> getAllAuthMethods() {
        HashMap<String, ArrayList<String>> usernamesAndAuthMethods = new HashMap();

        // For each user in the mock db, add their username and auth methods to the hashmap
        for (User user : users) {
            usernamesAndAuthMethods.put(user.getUsername(), user.getAuthMethods());
        }
        return usernamesAndAuthMethods;
    }
}
