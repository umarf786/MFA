package SSDT;

import java.security.SecureRandom;

/**
 * The {@code CodeGenerator} class provides a utility for generating random codes.
 */
public class CodeGenerator {

    /** The characters from which the random code will be generated. */
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /** The secure random number generator used for generating random indices. */
    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a random code consisting of alphanumeric characters.
     *
     * @return A randomly generated code.
     */
    public static String generateRandomCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }
}
