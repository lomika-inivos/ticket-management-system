package org.iit.oop.springbackend.model;

import org.iit.oop.springbackend.service.LoggingService;

/**
 * The Customer class represents a customer who buys tickets from a TicketPool.
 * It implements the Runnable interface to allow customers to run in separate threads.
 */
public class Customer implements Runnable {
    private final int customerRetrievalRate; // Time interval between ticket purchases in seconds
    private final int numberOfTickets; // Total number of tickets the customer will buy
    private final TicketPool ticketPool; // The pool from which tickets are bought

    LoggingService loggingService = new LoggingService(); // Service for logging information

    /**
     * Constructs a Customer with the specified retrieval rate, number of tickets, and ticket pool.
     *
     * @param customerRetrievalRate the rate at which the customer retrieves tickets (in seconds)
     * @param numberOfTickets the total number of tickets the customer will buy
     * @param ticketPool the pool from which tickets are bought
     */
    public Customer(int customerRetrievalRate, int numberOfTickets, TicketPool ticketPool) {
        this.customerRetrievalRate = customerRetrievalRate;
        this.numberOfTickets = numberOfTickets;
        this.ticketPool = ticketPool;
    }

    /**
     * The run method is executed when the thread is started.
     * It simulates the customer buying tickets at the specified retrieval rate.
     */
    @Override
    public void run() {
        for (int i = 1; i <= numberOfTickets; i++) {
            // Buy a ticket from the ticket pool
            Ticket ticket = ticketPool.buyTicket();
            // Log the ticket purchase
            loggingService.info(Thread.currentThread().getName() + " bought " + ticket);
            try {
                // Wait for the specified retrieval rate before buying the next ticket
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                // If the thread is interrupted, log the interruption and stop the thread
                Thread.currentThread().interrupt();
                loggingService.info(Thread.currentThread().getName() + " stopped.");
            }
        }
    }
}
