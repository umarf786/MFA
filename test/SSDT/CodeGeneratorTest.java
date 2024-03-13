package SSDT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the {@link CodeGenerator} class.
 */
class CodeGeneratorTest {

    /**
     * Tests the {@link CodeGenerator#generateRandomCode()} method to ensure it generates a random code.
     * The generated code should not be null, have a length of 5 characters, and consist of alphanumeric characters only.
     */
    @Test
    public void testGenerateRandomCode() {
        // Generate a random code
        String code = CodeGenerator.generateRandomCode();

        // Assert that the generated code is not null
        assertNotNull(code);

        // Assert that the length of the generated code is 5 characters
        assertEquals(5, code.length());

        // Assert that the generated code consists of alphanumeric characters only
        assertTrue(code.matches("[A-Za-z0-9]+"));
    }
}
