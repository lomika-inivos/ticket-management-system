package org.iit.oop.operation;

import org.iit.oop.model.Ticket;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final int maxCapacity; // Maximum capacity of the ticket pool
    private final Queue<Ticket> tickets; // Queue to hold the tickets
    private final AtomicInteger ticketIdGenerator; // Atomic integer to generate unique ticket IDs

    // Constructor to initialize the ticket pool with a maximum capacity
    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        tickets = new ConcurrentLinkedQueue<>(); // Initialize the ticket queue
        this.ticketIdGenerator = new AtomicInteger(1); // Initialize the ticket ID generator
    }

    // Method to add a ticket to the pool, called by the vendor
    public synchronized void addTicket(double ticketPrice, String eventName) {
        // Wait until there is space in the pool
        while (tickets.size() >= maxCapacity) {
            try {
                wait(); // Wait for space to become available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                return;
            }
        }
        // Generate a new ticket ID and create a new ticket
        int ticketId = ticketIdGenerator.getAndIncrement();
        Ticket ticket = new Ticket(ticketId, ticketPrice, eventName);
        tickets.add(ticket); // Add the ticket to the queue
        System.out.println(Thread.currentThread().getName() + " added ticket: " + ticket);
        notifyAll(); // Notify all waiting threads that a ticket has been added
    }

    // Method to buy a ticket from the pool, called by the customer
    public synchronized Ticket buyTicket() {
        // Wait until there is a ticket available
        while (tickets.isEmpty()) {
            try {
                wait(); // Wait for a ticket to become available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                return null;
            }
        }
        // Retrieve and remove the ticket from the queue
        Ticket ticket = tickets.poll();
        notifyAll(); // Notify all waiting threads that a ticket has been bought
        return ticket; // Return the bought ticket
    }
}