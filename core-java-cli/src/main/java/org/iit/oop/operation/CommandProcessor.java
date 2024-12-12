package org.iit.oop.operation;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * CommandProcessor class handles the processing of user commands
 * for the ticket management system.
 */
public class CommandProcessor {
    private final Scanner scanner; // Scanner object to read user input
    private final TicketOperationManager operationManager; // Manager to handle ticket operations

    /**
     * Constructor to initialize CommandProcessor with a Scanner and TicketOperationManager.
     *
     * @param scanner Scanner object for reading user input
     * @param operationManager Manager to handle ticket operations
     */
    public CommandProcessor(Scanner scanner, TicketOperationManager operationManager) {
        this.scanner = scanner;
        this.operationManager = operationManager;
    }

    /**
     * Method to process user commands in a loop until the system is exited.
     */
    public void processCommands() {
        while (true) {
            if (operationManager.isRunning()) {
                return; // Exit if the operation manager is running
            }
            // Display available commands to the user
            System.out.println("\nAvailable Commands:");
            System.out.println("1. Start ticket operations");
            System.out.println("3. Exit the system");
            System.out.print("\nEnter command number: ");
            try {
                int command = scanner.nextInt(); // Read user input
                switch (command) {
                    case 1 -> operationManager.startOperations(); // Start ticket operations
                    case 3 -> {
                        // Exit the system
                        System.out.println("Exiting the system. Goodbye!");
                        scanner.close(); // Close the scanner
                        System.exit(0); // Terminate the program
                    }
                    default -> System.out.println("Invalid command. Please try again."); // Handle invalid commands
                }
            } catch (InputMismatchException e) {
                // Handle invalid input
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
    }
}
