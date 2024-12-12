package org.iit.oop.operation;

import org.iit.oop.config.Config;
import org.iit.oop.model.Customer;
import org.iit.oop.model.Vendor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketOperationManager {
    private final Config config; // Configuration object containing system settings
    private boolean isRunning = false; // Flag to check if the system is running
    final List<Thread> vendorThreads = new ArrayList<>(); // List to hold vendor threads
    final List<Thread> customerThreads = new ArrayList<>(); // List to hold customer threads

    public TicketOperationManager(Config config) {
        this.config = config; // Initialize config
    }

    // Method to start ticket operations
    public synchronized void startOperations() {
        if (isRunning) {
            System.out.println("System is already running!");
            return;
        }
        isRunning = true; // Set the system running flag to true

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity()); // Initialize ticket pool
        System.out.println("==========Ticketing system started.==========");

        listenForStopKey(); // Start listening for stop key

        // Start vendor threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            Vendor vendor = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendor, "Vendor " + (i + 1));
            vendorThreads.add(vendorThread); // Add vendor thread to the list
            vendorThread.start(); // Start vendor thread
        }

        // Start customer threads
        for (int i = 0; i < config.getCustomerCount(); i++) {
            Customer customer = new Customer(config.getCustomerRetrievalRate(), config.getTicketsPerCustomer(), ticketPool);
            Thread customerThread = new Thread(customer, "Customer " + (i + 1));
            customerThreads.add(customerThread); // Add customer thread to the list
            customerThread.start(); // Start customer thread
        }
    }

    // Method to stop ticket operations
    public synchronized void stopOperations() {
        if (!isRunning) {
            System.out.println("System is not running!");
            return;
        }
        isRunning = false; // Set the system running flag to false
        vendorThreads.forEach(Thread::interrupt); // Interrupt all vendor threads
        customerThreads.forEach(Thread::interrupt); // Interrupt all customer threads
        System.out.println("==========Ticket operations stopped.==========");
    }

    // Method to listen for the stop key ('q')
    public void listenForStopKey() {
        System.out.println("==========Press 'q' to stop the ticketing system.==========");
        Thread keyListenerThread = new Thread(() -> {
            try {
                while (isRunning) {
                    int key = System.in.read(); // Read input from the console
                    if (key == 'q') {
                        stopOperations(); // Stop operations if 'q' is pressed
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Print stack trace if an exception occurs
            }
        });
        keyListenerThread.setDaemon(true); // Set the thread as a daemon thread
        keyListenerThread.start(); // Start the key listener thread
    }

    // Method to check if the system is running
    public synchronized boolean isRunning() {
        return isRunning;
    }
}
