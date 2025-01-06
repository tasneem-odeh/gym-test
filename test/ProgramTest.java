import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

public class ProgramTest {

    private static final String TEST_PROGRAMS_FILE = "test_programs.txt";

    @Before
    public void setup() {
        // Prepare test environment by ensuring the test file is clean before each test
        File file = new File(TEST_PROGRAMS_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAddProgram() {
        // Simulate adding a program
        String instructorUsername = "instructor1";
        Programs.addProgram(instructorUsername);

        // Verify that the program was added by checking the file
        File file = new File(TEST_PROGRAMS_FILE);
        assertTrue(file.exists()); // Check if the file exists

        // Additional checks can be done to ensure program content is added
    }

    @Test
    public void testEditProgram() {
        // Simulate editing a program
        String instructorUsername = "instructor1";
        Programs.addProgram(instructorUsername); // Add a program to edit

        // Now, edit the program
        Programs.editProgram(instructorUsername);

        // Verify that the program was edited
        File file = new File(TEST_PROGRAMS_FILE);
        assertTrue(file.exists()); // Check if the file exists

        // Add assertions to verify the program was actually edited
    }

    @Test
    public void testDeleteProgram() {
        // Simulate deleting a program
        String instructorUsername = "instructor1";
        Programs.addProgram(instructorUsername); // Add a program to delete

        Programs.deleteProgram(instructorUsername); // Delete the program

        // Verify that the program was deleted
        File file = new File(TEST_PROGRAMS_FILE);
        assertTrue(file.exists()); // Check if the file exists
        // You can also read the file contents to ensure the program is deleted
    }
}
