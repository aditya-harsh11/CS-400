import java.util.List;
import java.util.Scanner;

/**
 * Frontend implementation for the Dungeon Drifters Leaderboard.
 * This class handles user input and communicates with the backend.
 */
public class Frontend implements FrontendInterface {

    private Scanner in;
    private BackendInterface backend;
    private Integer minCollectables;
    private Integer maxCollectables;
    private GameRecord.Continent filterContinent;

    /**
     * COnstructor that makes a Frontend instance
     * @param in Scanner for reading user input
     * @param backend BackendInterface for processing commands
     */
    public Frontend(Scanner in, BackendInterface backend) {
        this.in = in;
        this.backend = backend;
        this.minCollectables = null;
        this.maxCollectables = null;
        this.filterContinent = null;
    }

    /**
     * Loops for user input until the "quit" command is issued
     */
    @Override
    public void runCommandLoop() {
        showCommandInstructions();

        while (true) {
            System.out.print("\nEnter command: ");
            if (!in.hasNextLine()) {
                break;
            }

            String line = in.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye");
                break;
            }

            try {
                processSingleCommand(line);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Displays command instructions exactly as per spec.
     */
    @Override
    public void showCommandInstructions() {
        System.out.println("\n==== Dungeon Drifters Leaderboard Commands ====");
        System.out.println("submit NAME CONTINENT SCORE DAMAGE_TAKEN COLLECTABLES COMPLETION_TIME");
        System.out.println("submit multiple FILEPATH");
        System.out.println("collectables MAX");
        System.out.println("collectables MIN to MAX");
        System.out.println("location CONTINENT");
        System.out.println("show MAX_COUNT");
        System.out.println("show fastest times");
        System.out.println("help");
        System.out.println("quit");
        System.out.println("===============================================\n");
    }

    /**
     * Parses and executes a single command entered by the user.
     */
    @Override
    public void processSingleCommand(String command) {
        String[] parts = command.split("\\s+");
        if (parts.length == 0 || parts[0].isEmpty()) {
            return;
        }

        String keyword = parts[0].toLowerCase();

        switch (keyword) {
            case "help":
                showCommandInstructions();
                break;
            case "submit":
                handleSubmit(parts);
                break;
            case "collectables":
                handleCollectables(parts);
                break;
            case "location":
                handleLocation(parts);
                break;
            case "show":
                handleShow(parts);
                break;
            default:
                System.out.println("Invalid command. Type 'help' for options.");
        }
    }

    /**
     * Handles the submit command
     */
    private void handleSubmit(String[] parts) {
        if (parts.length >= 2 && parts[1].equalsIgnoreCase("multiple")) {
            if (parts.length < 3) {
                System.out.println("Syntax Error: submit multiple requires a FILEPATH");
            } else {
                try {
                    backend.readData(parts[2]);
                    System.out.println("Data loaded successfully.");
                } catch (Exception e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }
            }
        } else {
            if (parts.length != 7) {
                System.out.println("Syntax Error: submit requires exactly 6 arguments");
            } else {
                try {
                    String name = parts[1];
                    GameRecord.Continent continent = GameRecord.Continent.valueOf(parts[2].toUpperCase());
                    int score = Integer.parseInt(parts[3]);
                    int damageTaken = Integer.parseInt(parts[4]);
                    int collectables = Integer.parseInt(parts[5]);
                    String completionTime = parts[6];

                    backend.addRecord(new GameRecord(name, continent, score, damageTaken, collectables, completionTime));
                    System.out.println("Record submitted");
                } catch (IllegalArgumentException e) {
                    System.out.println("Syntax Error: Invalid data format. Check numbers and continent");
                }
            }
        }
    }

    /**
     * Handles the "collectables" command.
     */
    private void handleCollectables(String[] parts) {
        try {
            if (parts.length == 2) {
                this.minCollectables = null;
                this.maxCollectables = Integer.parseInt(parts[1]);
                backend.getAndSetRange(minCollectables, maxCollectables);
                System.out.println("Collectables range updated (Max: " + maxCollectables + ")");
            } else if (parts.length == 4 && parts[2].equalsIgnoreCase("to")) {
                this.minCollectables = Integer.parseInt(parts[1]);
                this.maxCollectables = Integer.parseInt(parts[3]);
                backend.getAndSetRange(minCollectables, maxCollectables);
                System.out.println("Collectables range updated (" + minCollectables + " to " + maxCollectables + ")");
            } else {
                System.out.println("Syntax Error: Usage 'collectables MAX' or 'collectables MIN to MAX'");
            }
        } catch (NumberFormatException e) {
            System.out.println("Syntax Error: collectables values must be integers");
        }
    }

    /**
     * Handles the "location" command.
     */
    private void handleLocation(String[] parts) {
        if (parts.length != 2) {
            System.out.println("Syntax Error: Usage 'location CONTINENT'");
            return;
        }

        try {
            this.filterContinent = GameRecord.Continent.valueOf(parts[1].toUpperCase());
            backend.applyAndSetFilter(this.filterContinent);
            System.out.println("Location filter applied: " + filterContinent);
        } catch (IllegalArgumentException e) {
            System.out.println("Syntax Error: Invalid continent");
        }
    }

    /**
     * Handles the "show" command.
     */
    private void handleShow(String[] parts) {
      if (parts.length < 2) {
        System.out.println("Syntax Error: Usage 'show MAX_COUNT' or 'show fastest times'");
        return;
     }

      if (parts[1].equalsIgnoreCase("fastest") && parts.length >= 3 && parts[2].equalsIgnoreCase("times")) {
        List<String> topTen = backend.getTopTen();
        System.out.println("Top 10 fastest times:");
        for (String name : topTen) {
            System.out.println(name);
        }
      } else {
          try {
            int count = Integer.parseInt(parts[1]);
            // Just get the current filtered list, don't re-set the range
            List<String> records = backend.getTopTen(); // or a getter method if available
            System.out.println("Showing up to " + count + " records:");
            for (int i = 0; i < count && i < records.size(); i++) {
                System.out.println(records.get(i));
            }
          } catch (NumberFormatException e) {
            System.out.println("Syntax Error: MAX_COUNT must be an integer");
        }
    }
  }
}