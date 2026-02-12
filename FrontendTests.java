import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

/**
 * This class uses JUnit for testing the Frontend
 */
public class FrontendTests {

    /**
     * Test 1: Checks that the submit command is parsed correctly and sent to the backend
     */
    @Test
    public void roleTest1() {
        String input = "submit Player1 NORTH_AMERICA 100 10 5 001:20:30\n" + "submit multiple data.csv\n" + "quit\n";
        TextUITester tester = new TextUITester(input);
        Backend_Placeholder backend = new Backend_Placeholder(new Tree_Placeholder());
        Frontend frontend = new Frontend(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String output = tester.checkOutput();
        assertTrue(output.contains("Record submitted"), "Frontend should report successful submission.");
        assertTrue(output.contains("Data loaded successfully."), "Frontend should report file loading.");
    }

    /**
     * Test 2: Verifies 'collectables' and 'location' filter commands.
     */
    @Test
    public void roleTest2() {
        String input = "collectables 10 to 50\n" + "location EUROPE\n" + "quit\n";
        TextUITester tester = new TextUITester(input);
        Backend_Placeholder backend = new Backend_Placeholder(new Tree_Placeholder());
        Frontend frontend = new Frontend(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String output = tester.checkOutput();
        assertTrue(output.contains("Collectables range updated (10 to 50)"), "Frontend failed to set range.");
        assertTrue(output.contains("Location filter applied: EUROPE"), "Frontend failed to set location.");
    }

    /**
     * Test 3: Tests the different show command options
     */
    @Test
    public void roleTest3() {
        String input = "show 2\n" + "show fastest times\n" + "quit\n";
        TextUITester tester = new TextUITester(input);
        Backend_Placeholder backend = new Backend_Placeholder(new Tree_Placeholder());
        Frontend frontend = new Frontend(new Scanner(System.in), backend);
        frontend.runCommandLoop();
        String output = tester.checkOutput();
        assertTrue(output.contains("Showing up to 2 records:"), "Frontend failed 'show count' output.");
        assertTrue(output.contains("Top 10 fastest times:"), "Frontend failed 'show fastest' output.");
    }
}