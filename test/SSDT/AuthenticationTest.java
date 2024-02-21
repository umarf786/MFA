package SSDT;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @AfterEach
    void tearDown(){
        Authentication.users = new ArrayList<>();
    }

    @ParameterizedTest(name = "Test SignIn with Username: {0}, Password: {1}, AuthMethod: {2}, MFA: {3}")
    @CsvSource({
            "umarf786, password, phone, 123, 1",
            "david1, wordpass, email, 123, 1",
            "malcolm2, wordpad, app, 123, 1",
            "cheese33, leicester, text, 123, 1"
    })
    void testSignIn(String username, String password, String authMethod, String mfaCode, int expected_result) {
        // Setup
        String input = username + "\n" + password + "\n" + authMethod + "\n" + mfaCode + "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        User david = new User(2, "David", "david1", "wordpass", new ArrayList<>(Arrays.asList("phone", "email")));
        User malcolm = new User(3, "Malcolm", "malcolm2", "wordpad", new ArrayList<>(Arrays.asList("email", "app")));
        User cheese = new User(4, "Cheese", "cheese33", "leicester", new ArrayList<>(Arrays.asList("text", "app")));
        Authentication auth = new Authentication();

        // Execution
        int actual_result = auth.signIn();

        // Assertion
        assertEquals(expected_result, actual_result);
    }


    @Test
    @DisplayName("testSignInInvalidUsername")
    void testSignInInvalidUsername() {
        // Setup
        String input = "umarf7860\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        Authentication auth = new Authentication();
        int expected_result = -2;

        // Execution
        int actual_result = auth.signIn();

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testSignInInvalidPassword")
    void testSignInInvalidPassword() {
        // Setup
        String input = "umarf786\npasswordo\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        Authentication auth = new Authentication();
        int expected_result = -3;

        // Execution
        int actual_result = auth.signIn();

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testSignInNotAllowedMFAOption")
    void testSignInNotAllowedMFAOption() {
        // Setup
        String input = "umarf786\npassword\nemail\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        Authentication auth = new Authentication();
        int expected_result = -5;

        // Execution
        int actual_result = auth.signIn();


        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testSignInInvalidMFAOption")
    void testSignInInvalidMFAOption() {
        // Setup
        String input = "umarf786\npassword\naaaaa\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        Authentication auth = new Authentication();
        int expected_result = -5;

        // Execution
        int actual_result = auth.signIn();

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testSignInMFAFailure")
    void testSignInMFAFailure() {
        // Setup
        String input = "umarf786\npassword\nphone\n1233\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        Authentication auth = new Authentication();
        int expected_result = -4;

        // Execution
        int actual_result = auth.signIn();

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @ParameterizedTest(name = "Test checkMultiAuth with AuthMethod: {0}")
    @CsvSource({
            "phone, 123",
            "email, 123",
            "text, 123",
            "app, 123"
    })
    void testCheckMultiAuthSuccess(String authMethod, String input) {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("phone", "text", "app", "email"));
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = 1;

        // Execution
        int actual_result = auth.checkMultiAuth(authMethod, authMethods);

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testCheckMultiAuthInvalidMethod")
    void testCheckMultiAuthInvalidMethod() {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("phone", "app"));
        String input = "123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = -1;

        // Execution
        int actual_result = auth.checkMultiAuth("appa", authMethods);

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testCheckMultiAuthInvalidCode")
    void testCheckMultiAuthInvalidCode() {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("phone", "app"));
        String input = "1234\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = -2;

        // Execution
        int actual_result = auth.checkMultiAuth("app", authMethods);

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testGetAllUsernamesAndPasswordsReturnsAPopulatedHashmap")
    void testGetAllUsernamesAndPasswordsReturnsAPopulatedHashmap() {
        // Setup
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        User igbo = new User(1, "Igbohim", "igbo1", "wordpass", new ArrayList<>(Arrays.asList("email", "app")));
        Authentication auth = new Authentication();
        HashMap<String, String> expected_result = new HashMap<>();
        expected_result.put("umarf786", "password");
        expected_result.put("igbo1", "wordpass");

        // Execution
        HashMap<String, String> actual_result = auth.getAllUsernamesAndPasswords();

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testGetAllUsernamesAndPasswordsNoUsersReturnsEmpty")
    void testGetAllUsernamesAndPasswordsNoUsersReturnsEmpty() {
        // Setup
        Authentication auth = new Authentication();

        // Execution
        HashMap<String, String> expected_result = auth.getAllUsernamesAndPasswords();

        // Assertion
        assertTrue(expected_result.isEmpty());
    }

    @Test
    @DisplayName("testGetAllAuthMethodsReturnsAPopulatedHashmap")
    void testGetAllAuthMethodsReturnsAPopulatedHashmap() {
        // Setup
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        User igbo = new User(1, "Igbohim", "igbo1", "wordpass", new ArrayList<>(Arrays.asList("email", "app")));
        Authentication auth = new Authentication();
        HashMap<String, ArrayList<String>> expected_result = new HashMap<>();
        expected_result.put("umarf786", new ArrayList<>(Arrays.asList("phone", "app")));
        expected_result.put("igbo1", new ArrayList<>(Arrays.asList("email", "app")));

        // Execution
        HashMap<String, ArrayList<String>> actual_result = auth.getAllAuthMethods();

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testGetAllAuthMethodsNoUsersReturnsEmpty")
    void testGetAllAuthMethodsNoUsersReturnsEmpty() {
        // Setup
        Authentication auth = new Authentication();

        // Execution
        HashMap<String, ArrayList<String>> actual_result = auth.getAllAuthMethods();

        // Assertion
        System.out.println(actual_result);
        assertTrue(actual_result.isEmpty());
    }
}
