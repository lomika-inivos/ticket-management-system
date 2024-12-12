package org.iit.oop.springbackend.model;

import org.iit.oop.springbackend.service.LoggingService;

/**
 * The Vendor class implements Runnable and simulates a vendor releasing tickets at a specified rate.
 */
public class Vendor implements Runnable {
    private final int totalTickets; // Total number of tickets to be released
    private final int ticketReleaseRate; // Rate at which tickets are released (in seconds)
    private final TicketPool ticketPool; // Shared ticket pool to which tickets are added

    LoggingService loggingService = new LoggingService(); // Service for logging information

    /**
     * Constructor to initialize the Vendor with total tickets, release rate, and ticket pool.
     *
     * @param totalTickets      Total number of tickets to be released
     * @param ticketReleaseRate Rate at which tickets are released (in seconds)
     * @param ticketPool        Shared ticket pool to which tickets are added
     */
    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    /**
     * The run method is executed when the thread is started. It releases tickets at the specified rate
     * until the total number of tickets is reached or the thread is interrupted.
     */
    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    double ticketPrice = Math.random() * 100 + 20; // Generate a random ticket price between 20 and 120
                    String eventName = "Concert"; // Example event name
                    ticketPool.addTicket(ticketPrice, eventName); // Add the ticket to the pool
                    Thread.sleep(ticketReleaseRate * 1000L); // Simulate delay based on release rate
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Re-interrupt the thread
                loggingService.info(Thread.currentThread().getName() + " stopped."); // Log the interruption
            }
        }
    }
}
