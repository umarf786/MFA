package SSDT;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

class MultiAuthTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp(){
        // Redirect System.out to a ByteArrayOutputStream
        originalSystemOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown(){
        Database.users = new ArrayList<>();
    }


    @ParameterizedTest(name = "Test checkMultiAuth with AuthMethod: {0}, Input: {1}")
    @CsvSource({
            "phone, 111",
            "email, 222",
            "text, 333",
            "app, 444"
    })
    void testCheckMultiAuthSuccess(String authMethod, String input) {
        try (MockedStatic<CodeGenerator> codeGeneratorMock = mockStatic(CodeGenerator.class)) {
            codeGeneratorMock.when(CodeGenerator::generateRandomCode).thenReturn(input);

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
            assertTrue(outContent.toString().contains("MFA accepted"));
            assertTrue(outContent.toString().contains("MOCK:"));
        }
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
        assertTrue(outContent.toString().contains("Not a valid authentication method or this method is not available to you"));
    }

    @Test
    @DisplayName("testCheckMultiAuthDefaultCase")
    void testCheckMultiAuthDefaultCase() {
        // Setup
        ArrayList<String> authMethods = new ArrayList<>(Arrays.asList("aaa", "bbb"));
        String input = "123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Authentication auth = new Authentication();
        int expected_result = -5;

        // Execution
        int actual_result = auth.MultiAuth.checkMultiAuth("aaa", authMethods);

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
        assertTrue(outContent.toString().contains("Please enter a valid code next time, try again"));
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

    @DisplayName("testPhoneAuth")
    @ParameterizedTest(name = "Test phone with Code: {0}, Expected Response Code: {1}")
    @CsvSource({
            "111, 1",
            "222, -1",
            "333, -1",
    })
    void testPhoneAuth(String code, int expectedResponseCode) {
        try (MockedStatic<CodeGenerator> codeGeneratorMock = mockStatic(CodeGenerator.class)) {
            codeGeneratorMock.when(CodeGenerator::generateRandomCode).thenReturn("111");

            // Set up the input stream for the scanner
            InputStream in = new ByteArrayInputStream(code.getBytes());
            Scanner scanner = new Scanner(in);

            // Create a MultiAuth instance with the provided scanner
            MultiAuth multiAuth = new MultiAuth(scanner);

            // Execute the phone method
            int actualResponseCode = multiAuth.phoneAuth();

            // Assert the result
            assertEquals(expectedResponseCode, actualResponseCode);
            assertTrue(outContent.toString().contains("Calling your phone now with a code. Please enter it"));
            assertTrue(outContent.toString().contains("MOCK:"));
        }
    }

    @DisplayName("testTextAuth")
    @ParameterizedTest(name = "Test text with Code: {0}, Expected Response Code: {1}")
    @CsvSource({
            "222, 1",
            "555, -1",
            "333, -1",
    })
    void testText(String code, int expectedResponseCode) {
        try (MockedStatic<CodeGenerator> codeGeneratorMock = mockStatic(CodeGenerator.class)) {
            codeGeneratorMock.when(CodeGenerator::generateRandomCode).thenReturn("222");

            // Set up the input stream for the scanner
            InputStream in = new ByteArrayInputStream(code.getBytes());
            Scanner scanner = new Scanner(in);

            // Create a MultiAuth instance with the provided scanner
            MultiAuth multiAuth = new MultiAuth(scanner);

            // Execute the phone method
            int actualResponseCode = multiAuth.textAuth();

            // Assert the result
            assertEquals(expectedResponseCode, actualResponseCode);
            assertTrue(outContent.toString().contains("Sending a message now with a code. Please enter it"));
            assertTrue(outContent.toString().contains("MOCK:"));
        }
    }

    @DisplayName("testEmailAuth")
    @ParameterizedTest(name = "Test email with Code: {0}, Expected Response Code: {1}")
    @CsvSource({
            "333, 1",
            "555, -1",
            "444, -1",
    })
    void testEmail(String code, int expectedResponseCode) {
        try (MockedStatic<CodeGenerator> codeGeneratorMock = mockStatic(CodeGenerator.class)) {
            codeGeneratorMock.when(CodeGenerator::generateRandomCode).thenReturn("333");

            // Set up the input stream for the scanner
            InputStream in = new ByteArrayInputStream(code.getBytes());
            Scanner scanner = new Scanner(in);

            // Create a MultiAuth instance with the provided scanner
            MultiAuth multiAuth = new MultiAuth(scanner);

            // Execute the phone method
            int actualResponseCode = multiAuth.emailAuth();

            // Assert the result
            assertEquals(expectedResponseCode, actualResponseCode);
            assertTrue(outContent.toString().contains("Sending an email now with a code. Please enter it"));
            assertTrue(outContent.toString().contains("MOCK:"));
        }
    }

    @DisplayName("testAppAuth")
    @ParameterizedTest(name = "Test app with Code: {0}, Expected Response Code: {1}")
    @CsvSource({
            "444, 1",
            "555, -1",
            "333, -1",
    })
    void testApp(String code, int expectedResponseCode) {
        try (MockedStatic<CodeGenerator> codeGeneratorMock = mockStatic(CodeGenerator.class)) {
            codeGeneratorMock.when(CodeGenerator::generateRandomCode).thenReturn("444");

            // Set up the input stream for the scanner
            InputStream in = new ByteArrayInputStream(code.getBytes());
            Scanner scanner = new Scanner(in);

            // Create a MultiAuth instance with the provided scanner
            MultiAuth multiAuth = new MultiAuth(scanner);

            // Execute the phone method
            int actualResponseCode = multiAuth.appAuth();

            // Assert the result
            assertEquals(expectedResponseCode, actualResponseCode);
            assertTrue(outContent.toString().contains("Open your auth app and enter the code"));
            assertTrue(outContent.toString().contains("MOCK:"));
        }
    }

}
