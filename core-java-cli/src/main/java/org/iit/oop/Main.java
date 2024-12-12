package org.iit.oop;

import org.iit.oop.config.Config;
import org.iit.oop.operation.CommandProcessor;
import org.iit.oop.operation.TicketOperationManager;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize scanner for user input
        Scanner scanner = new Scanner(System.in);
        // Initialize configuration object
        Config config = new Config();

        // Print welcome message
        System.out.println("*".repeat(90));
        System.out.println("        ********** WELCOME TO THE REAL-TIME EVENT TICKETING SYSTEM! ********** ");
        System.out.println("*".repeat(90));

        // Define the path to the configuration file
        String configFilePath = "config.json";
        File configFile = new File(configFilePath);

        // Check if the configuration file exists
        if (configFile.exists()) {
            System.out.println("A configuration file was found. Do you want to use it? (Y/N)");
            while (true) {
                // Get user input and convert to uppercase
                String choice = scanner.nextLine().trim().toUpperCase();
                if (choice.equals("Y")) {
                    // Load configuration from file
                    config.loadFromFile();
                    break;
                } else if (choice.equals("N")) {
                    // Configure system manually
                    config.configureSystem(scanner);
                    break;
                } else {
                    // Prompt user for valid input
                    System.out.println("Invalid choice. Please enter Y or N.");
                }
            }
        } else {
            // If no configuration file is found, prompt user to create a new one
            System.out.println("No configuration file found. You will now create a new one.");
            config.configureSystem(scanner);
        }

        // Initialize the ticket operation manager with the configuration
        TicketOperationManager operationManager = new TicketOperationManager(config);
        // Initialize the command processor with the scanner and operation manager
        CommandProcessor commandProcessor = new CommandProcessor(scanner, operationManager);
        // Start processing commands
        commandProcessor.processCommands();
    }
}
