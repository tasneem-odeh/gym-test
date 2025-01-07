import org.junit.jupiter.api.*;
import java.io.*;
import java.util.logging.*;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private static final String USERS_FILE_PATH = "users_test_data.csv";

    @BeforeAll
    static void setupTestData() throws IOException {
        // Create a mock users file for testing
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE_PATH))) {
            writer.println("admin,5f4dcc3b5aa765d61d8327deb882cf99,admin,John Doe,john.doe@example.com,accepted"); // password: password
            writer.println("instructor,5f4dcc3b5aa765d61d8327deb882cf99,instructor,Jane Smith,jane.smith@example.com,pending");
            writer.println("client,5f4dcc3b5aa765d61d8327deb882cf99,client,Client User,client.user@example.com,denied");
        }
        Main.USERS_FILE = USERS_FILE_PATH; // Use test data file
    }

    @AfterAll
    static void cleanupTestData() {
        // Delete the mock users file after tests
        File file = new File(USERS_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testValidLogin() {
        String username = "admin";
        String password = "password";

        boolean result = invokeAuthenticateUser(username, password);

        assertTrue(result, "Valid login should return true.");
    }

    @Test
    void testInvalidUsername() {
        String username = "invalidUser";
        String password = "password";

        boolean result = invokeAuthenticateUser(username, password);

        assertFalse(result, "Invalid username should return false.");
    }

    @Test
    void testInvalidPassword() {
        String username = "admin";
        String password = "wrongPassword";

        boolean result = invokeAuthenticateUser(username, password);

        assertFalse(result, "Invalid password should return false.");
    }

    @Test
    void testPendingAccountStatus() {
        String username = "instructor";
        String password = "password";

        boolean result = invokeAuthenticateUser(username, password);

        assertFalse(result, "Pending account should return false.");
    }

    @Test
    void testDeniedAccountStatus() {
        String username = "client";
        String password = "password";

        boolean result = invokeAuthenticateUser(username, password);

        assertFalse(result, "Denied account should return false.");
    }

    private boolean invokeAuthenticateUser(String username, String password) {
        // Mock the hashPassword method of Main for testing
        Main.hashPassword = (input) -> {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] digest = md.digest(input.getBytes());
                StringBuilder sb = new StringBuilder();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b));
                }
                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        };

        return Login.authenticateUser(username, password);
    }
}
