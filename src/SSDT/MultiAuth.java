package SSDT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static SSDT.User.users;

public class MultiAuth {
    private final Scanner input;

    public MultiAuth(Scanner input){
        this.input = input;
    }

    public int phone(){
        System.out.println("Calling your phone now with a code. Please enter it");
        String code = input.nextLine();
        if (code.equals("111")){
            return 1;
        }
        return -1;
    }

    public int text(){
        System.out.println("Sending a message now with a code. Please enter it");
        String code = input.nextLine();
        if (code.equals("222")){
            return 1;
        }
        return -1;
    }

    public int email(){
        System.out.println("Sending an email now with a code. Please enter it");
        String code = input.nextLine();
        if (code.equals("333")){
            return 1;
        }
        return -1;
    }

    public int app(){
        System.out.println("Open your auth app and enter the code");
        String code = input.nextLine();
        if (code.equals("444")){
            return 1;
        }
        return -1;
    }

    public int checkMultiAuth(String method, ArrayList<String> availableMethods){
        // Initialise default response code
        int response_code = -5;

        // Check if the provided method is available/selected for the given username
        // If so, continue with the process. If not, tell user and return
        if (availableMethods.contains(method)){
            response_code = switch (method) {
                case "phone" -> phone();
                case "text" -> text();
                case "email" -> email();
                case "app" -> app();
                default -> response_code;
            };

            // If code is correct
            if (response_code == 1){
                System.out.println("MFA accepted");
                return 1;
            }
            // If code is incorrect
            else{
                System.out.println("Please enter a valid code next time, try again");
                return -2;
            }
        }
        // If the method is not available to the user or doesn't exist
        else{
            System.out.println("Not a valid authentication method or this method is not available to you");
            return -1;
        }
    }

    public HashMap<String, ArrayList<String>> getAllAuthMethods(){
        // Initialise hashmap to store all usernames and their associated auth methods
        HashMap<String, ArrayList<String>> usernames_and_authmethods = new HashMap<>();

        // For each user in the mock db, add their username and auth methods to the hashmap
        for (User user : users){
            usernames_and_authmethods.put(user.getUsername(), user.getAuthMethods());
        }
        return usernames_and_authmethods;
    }
}
