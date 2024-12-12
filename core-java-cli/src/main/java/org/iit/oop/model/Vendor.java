package org.iit.oop.model;

import org.iit.oop.operation.TicketPool;

/**
 * Vendor class that implements Runnable to simulate ticket vending.
 */
public class Vendor implements Runnable {
    private final int totalTickets; // Total number of tickets to be released
    private final int ticketReleaseRate; // Rate at which tickets are released (in seconds)
    private final TicketPool ticketPool; // Shared ticket pool to add tickets to

    /**
     * Constructor to initialize Vendor with total tickets, release rate, and ticket pool.
     *
     * @param totalTickets      Total number of tickets to be released
     * @param ticketReleaseRate Rate at which tickets are released (in seconds)
     * @param ticketPool        Shared ticket pool to add tickets to
     */
    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    /**
     * The run method to be executed when the thread starts.
     * It simulates the release of tickets at a specified rate.
     */
    @Override
    public void run() {
        for(int i = 1; i <= totalTickets; i++) { // Loop through the total number of tickets
            try {
                while (!Thread.currentThread().isInterrupted()) { // Check if the thread is interrupted
                    double ticketPrice = Math.random() * 100 + 20; // Generate a random ticket price
                    String eventName = "Concert"; // Example event name
                    ticketPool.addTicket(ticketPrice, eventName); // Add the ticket to the ticket pool
                    Thread.sleep(ticketReleaseRate * 1000L); // Simulate delay based on release rate
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                System.out.println(Thread.currentThread().getName() + " stopped."); // Log the interruption
            }
        }
    }
}
