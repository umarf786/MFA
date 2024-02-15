package SSDT;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @AfterEach
    void tearDown(){
        Authentication.users = new ArrayList<>();
    }

    @Test
    @DisplayName("testSignInSuccess")
    void testSignInSuccess() {
        // Setup
        String input = "umarf786\npassword\nphone\n123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        Authentication auth = new Authentication();
        int expected_result = 1;

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

    @Test
    @DisplayName("testCheckMultiAuthPhoneSuccess")
    void testCheckMultiAuthPhoneSuccess() {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("phone", "text"));
        String input = "123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = 1;

        // Execution
        int actual_result = auth.checkMultiAuth("phone", authMethods);

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testCheckMultiAuthEmailSuccess")
    void testCheckMultiAuthEmailSuccess() {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("phone", "email"));
        String input = "123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = 1;

        // Execution
        int actual_result = auth.checkMultiAuth("email", authMethods);

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testCheckMultiAuthTextSuccess")
    void testCheckMultiAuthTextSuccess() {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("phone", "text"));
        String input = "123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = 1;

        // Execution
        int actual_result = auth.checkMultiAuth("text", authMethods);

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testCheckMultiAuthAppSuccess")
    void testCheckMultiAuthAppSuccess() {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("phone", "app"));
        String input = "123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = 1;

        // Execution
        int actual_result = auth.checkMultiAuth("app", authMethods);

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

        // Execution
        HashMap<String, String> expected_result = auth.getAllUsernamesAndPasswords();

        // Assertion
        assertTrue(!expected_result.isEmpty());
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

        // Execution
        HashMap<String, ArrayList<String>> actual_result = auth.getAllAuthMethods();

        // Assertion
        assertTrue(!actual_result.isEmpty());
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
