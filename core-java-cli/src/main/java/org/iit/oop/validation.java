package org.iit.oop;

import java.util.Scanner;

public class validation {
    public static int getValidInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) break;
                System.out.println("Value must be greater than zero. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }
}
