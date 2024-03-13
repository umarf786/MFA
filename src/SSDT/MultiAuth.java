package SSDT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static SSDT.User.users;

/**
 * Handles multifactor authentication methods for user verification.
 */
public class MultiAuth {
    private final Scanner input;

    /**
     * Constructor to initialize the MultiAuth object with a Scanner for user input.
     *
     * @param input The Scanner object for user input.
     */
    public MultiAuth(Scanner input) {
        this.input = input;
    }

    /**
     * Sends a verification code to the user's phone and prompts for input.
     *
     * @return 1 if the entered code matches, otherwise -1.
     */
    public int phone() {
        System.out.println("Calling your phone now with a code. Please enter it");
        String code = input.nextLine();
        if (code.equals("111")) {
            return 1;
        }
        return -1;
    }

    /**
     * Sends a verification code via text message and prompts for input.
     *
     * @return 1 if the entered code matches, otherwise -1.
     */
    public int text() {
        System.out.println("Sending a message now with a code. Please enter it");
        String code = input.nextLine();
        if (code.equals("222")) {
            return 1;
        }
        return -1;
    }

    /**
     * Sends a verification code via email and prompts for input.
     *
     * @return 1 if the entered code matches, otherwise -1.
     */
    public int email() {
        System.out.println("Sending an email now with a code. Please enter it");
        String code = input.nextLine();
        if (code.equals("333")) {
            return 1;
        }
        return -1;
    }

    /**
     * Prompts the user to enter a verification code from an authentication app.
     *
     * @return 1 if the entered code matches, otherwise -1.
     */
    public int app() {
        System.out.println("Open your auth app and enter the code");
        String code = input.nextLine();
        if (code.equals("444")) {
            return 1;
        }
        return -1;
    }

    /**
     * Checks the provided authentication method against available methods.
     *
     * @param method           The authentication method to check.
     * @param availableMethods The list of available authentication methods.
     * @return 1 if authentication succeeds, -1 if the method is not available, -2 if the code is invalid.
     */
    public int checkMultiAuth(String method, ArrayList<String> availableMethods) {
        // Initialize default response code
        int response_code = -5;

        // Check if the provided method is available/selected for the given username
        // If so, continue with the process. If not, tell user and return
        if (availableMethods.contains(method)) {
            response_code = switch (method) {
                case "phone" -> phone();
                case "text" -> text();
                case "email" -> email();
                case "app" -> app();
                default -> response_code;
            };

            // If code is correct
            if (response_code == 1) {
                System.out.println("MFA accepted");
                return 1;
            }
            // If code is incorrect
            else {
                System.out.println("Please enter a valid code next time, try again");
                return -2;
            }
        }
        // If the method is not available to the user or doesn't exist
        else {
            System.out.println("Not a valid authentication method or this method is not available to you");
            return -1;
        }
    }

    /**
     * Retrieves all authentication methods associated with each user.
     *
     * @return A HashMap containing usernames as keys and their associated authentication methods as values.
     */
    public HashMap<String, ArrayList<String>> getAllAuthMethods() {
        // Initialize hashmap to store all usernames and their associated auth methods
        HashMap<String, ArrayList<String>> usernames_and_authmethods = new HashMap<>();

        // For each user in the mock db, add their username and auth methods to the hashmap
        for (User user : users) {
            usernames_and_authmethods.put(user.getUsername(), user.getAuthMethods());
        }
        return usernames_and_authmethods;
    }
}