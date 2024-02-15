package SSDT;

// Import required utils
import java.util.ArrayList;
import java.util.Arrays;

// Import global users mock db
import static SSDT.Authentication.users;

public class Demo {
    public static void main(String[] args) {
        // Create two users and add them to the global list (mock database)
        User umar = new User(1, "Umar", "umarf786", "balls", new ArrayList<String>(Arrays.asList("phone", "text")));
        User igbo = new User(2, "Igbohim", "igbo1", "in yo jaw", new ArrayList<String>(Arrays.asList("app", "email")));

        // Instantiate the authentication class
        Authentication auth = new Authentication();

        // Call the sign in method from the authentication object
        auth.signIn();
    }
}
