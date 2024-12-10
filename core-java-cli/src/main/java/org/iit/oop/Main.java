package org.iit.oop;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Config config = new Config();

        System.out.println("*".repeat(90));
        System.out.println("        ********** WELCOME TO THE REAL-TIME EVENT TICKETING SYSTEM! ********** ");
        System.out.println("*".repeat(90));

        // Check for existing configuration
        String configFilePath = "config.json";
        File configFile = new File(configFilePath);

        if (configFile.exists()) {
            System.out.println("A configuration file was found. Do you want to use it? (Y/N)");
            while (true) {
                String choice = scanner.nextLine().trim().toUpperCase();
                if (choice.equals("Y")) {
                    config.loadFromFile();
                    break;
                } else if (choice.equals("N")) {
                    config.configureSystem(scanner);
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter Y or N.");
                }
            }
        } else {
            System.out.println("No configuration file found. You will now create a new one.");
            config.configureSystem(scanner);
        }

        TicketOperationManager operationManager = new TicketOperationManager(config);
        CommandProcessor commandProcessor = new CommandProcessor(scanner, operationManager);
        commandProcessor.processCommands();
    }
}
