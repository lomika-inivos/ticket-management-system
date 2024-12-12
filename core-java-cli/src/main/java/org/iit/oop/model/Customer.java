package org.iit.oop.model;

import org.iit.oop.operation.TicketPool;

/**
 * The Customer class implements Runnable and simulates a customer buying tickets from a TicketPool.
 */
public class Customer implements Runnable {
    private final int customerRetrievalRate; // Time interval between ticket purchases in seconds
    private final int numberOfTickets; // Total number of tickets the customer wants to buy
    private final TicketPool ticketPool; // The ticket pool from which tickets are bought

    /**
     * Constructor to initialize the Customer object.
     *
     * @param customerRetrievalRate Time interval between ticket purchases in seconds
     * @param numberOfTickets Total number of tickets the customer wants to buy
     * @param ticketPool The ticket pool from which tickets are bought
     */
    public Customer(int customerRetrievalRate, int numberOfTickets, TicketPool ticketPool) {
        this.customerRetrievalRate = customerRetrievalRate;
        this.numberOfTickets = numberOfTickets;
        this.ticketPool = ticketPool;
    }

    /**
     * The run method is executed when the thread is started.
     * It simulates the customer buying tickets at a specified interval.
     */
    @Override
    public void run() {
        for (int i = 1; i <= numberOfTickets; i++) {
            // Buy a ticket from the ticket pool
            Ticket ticket = ticketPool.buyTicket();
            // Print the ticket purchase information
            System.out.println(Thread.currentThread().getName() + " bought " + ticket);
            try {
                // Sleep for the specified interval before buying the next ticket
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
        }
    }
}
