package org.iit.oop;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandProcessor {
    private final Scanner scanner;
    private final TicketOperationManager operationManager;

    public CommandProcessor(Scanner scanner, TicketOperationManager operationManager) {
        this.scanner = scanner;
        this.operationManager = operationManager;
    }

    public void processCommands() {
        while (true) {
            if(operationManager.isRunning()){
                return;
            }
            System.out.println("\nAvailable Commands:");
            System.out.println("1. Start ticket operations");
            System.out.println("3. Exit the system");
            System.out.print("\nEnter command number: ");
            try {
                int command = scanner.nextInt();
                switch (command) {
                    case 1 -> operationManager.startOperations();
                    case 3 -> {
                        System.out.println("Exiting the system. Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid command. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }
    }
}
