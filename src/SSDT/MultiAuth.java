package SSDT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static SSDT.Authentication.users;

public class MultiAuth {
    Scanner input;

    public MultiAuth(Scanner input){
        this.input = input;
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
