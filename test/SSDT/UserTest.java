package SSDT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("testGetID")
    void testGetID() {
        // Setup
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<String>(Arrays.asList("phone", "app")));
        int expected_id = 1;

        // Execution
        int actual_id = umar.getID();

        // Assertion
        assertEquals(expected_id, actual_id);
    }

    @Test
    @DisplayName("testGetUsername")
    void testGetUsername() {
        // Setup
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<String>(Arrays.asList("phone", "app")));
        String expected_username = "umarf786";

        // Execution
        String actual_username = umar.getUsername();

        // Assertion
        assertEquals(expected_username, actual_username);
    }

    @Test
    @DisplayName("testGetPassword")
    void testGetPassword() {
        // Setup
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<String>(Arrays.asList("phone", "app")));
        String expected_password = "password";

        // Execution
        String actual_password = umar.getPassword();

        // Assertion
        assertEquals(expected_password, actual_password);
    }

    @Test
    @DisplayName("testGetAuthMethods")
    void testGetAuthMethods() {
        // Setup
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<String>(Arrays.asList("phone", "app")));
        ArrayList<String> expected_list = new ArrayList<>(Arrays.asList("phone", "app"));

        // Execution
        ArrayList<String> actual_list = umar.getAuthMethods();

        // Assertion
        assertEquals(expected_list, actual_list);
    }

    @Test
    @DisplayName("testGetAuthMethodsEmptyList")
    void testGetAuthMethodsEmptyList() {
        // Setup
        User umar = new User(1, "Umar", "umarf786", "password", new ArrayList<String>());
        ArrayList<String> expected_list = new ArrayList<>();

        // Execution
        ArrayList<String> actual_list = umar.getAuthMethods();

        // Assertion
        assertEquals(expected_list, actual_list);
    }

}