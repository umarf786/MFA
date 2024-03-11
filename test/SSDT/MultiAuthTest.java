package SSDT;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiAuthTest {

    @AfterEach
    void tearDown(){
        Authentication.users = new ArrayList<>();
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
        int actual_result = auth.MultiAuth.checkMultiAuth(authMethod, authMethods);

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
        int actual_result = auth.MultiAuth.checkMultiAuth("appa", authMethods);

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
        int actual_result = auth.MultiAuth.checkMultiAuth("app", authMethods);

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testGetAllAuthMethodsReturnsAPopulatedHashmap")
    void testGetAllAuthMethodsReturnsAPopulatedHashmap() {
        // Setup
        new User(1, "Umar", "umarf786", "password", new ArrayList<>(Arrays.asList("phone", "app")));
        new User(1, "Igbohim", "igbo1", "wordpass", new ArrayList<>(Arrays.asList("email", "app")));
        Authentication auth = new Authentication();
        HashMap<String, ArrayList<String>> expected_result = new HashMap<>();
        expected_result.put("umarf786", new ArrayList<>(Arrays.asList("phone", "app")));
        expected_result.put("igbo1", new ArrayList<>(Arrays.asList("email", "app")));

        // Execution
        HashMap<String, ArrayList<String>> actual_result = auth.MultiAuth.getAllAuthMethods();

        // Assertion
        assertEquals(expected_result, actual_result);
    }

    @Test
    @DisplayName("testGetAllAuthMethodsNoUsersReturnsEmpty")
    void testGetAllAuthMethodsNoUsersReturnsEmpty() {
        // Setup
        Authentication auth = new Authentication();

        // Execution
        HashMap<String, ArrayList<String>> actual_result = auth.MultiAuth.getAllAuthMethods();

        // Assertion
        System.out.println(actual_result);
        assertTrue(actual_result.isEmpty());
    }
}
