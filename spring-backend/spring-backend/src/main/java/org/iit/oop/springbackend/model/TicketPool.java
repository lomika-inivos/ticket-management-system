package org.iit.oop.springbackend.model;

import org.iit.oop.springbackend.service.LoggingService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TicketPool class manages a pool of tickets with a maximum capacity.
 * It allows vendors to add tickets and customers to buy tickets.
 * It also sends updates and logs to the frontend via WebSocket.
 */
public class TicketPool {
    private final int maxCapacity; // Maximum capacity of the ticket pool
    private final Queue<Ticket> tickets; // Queue to hold tickets
    private final AtomicInteger ticketIdGenerator; // Atomic integer to generate unique ticket IDs
    private final SimpMessagingTemplate messagingTemplate; // Template for sending WebSocket messages
    private final LoggingService loggingService; // Service for logging information

    /**
     * Constructor to initialize the TicketPool with a maximum capacity, messaging template, and logging service.
     *
     * @param maxCapacity       the maximum number of tickets the pool can hold
     * @param messagingTemplate the template for sending WebSocket messages
     * @param loggingService    the service for logging information
     */
    public TicketPool(int maxCapacity, SimpMessagingTemplate messagingTemplate, LoggingService loggingService) {
        this.maxCapacity = maxCapacity;
        this.messagingTemplate = messagingTemplate;
        this.loggingService = loggingService;
        this.tickets = new ConcurrentLinkedQueue<>();
        this.ticketIdGenerator = new AtomicInteger(1);
    }

    /**
     * Adds a ticket to the pool. This method is called by the vendor.
     * It waits if the pool is at maximum capacity.
     *
     * @param ticketPrice the price of the ticket
     * @param eventName   the name of the event
     */
    public synchronized void addTicket(double ticketPrice, String eventName) {
        while (tickets.size() >= maxCapacity) {
            try {
                wait(); // Wait until there is space in the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        int ticketId = ticketIdGenerator.getAndIncrement(); // Generate a unique ticket ID
        Ticket ticket = new Ticket(ticketId, ticketPrice, eventName); // Create a new ticket
        tickets.add(ticket); // Add the ticket to the pool
        loggingService.info(Thread.currentThread().getName() + " added ticket: " + ticket); // Log the addition
        sendLogToFrontend(Thread.currentThread().getName() + " added ticket: " + ticket); // Send log to frontend
        sendUpdates(); // Send updates to frontend
        notifyAll(); // Notify all waiting threads
    }

    /**
     * Buys a ticket from the pool. This method is called by the customer.
     * It waits if the pool is empty.
     *
     * @return the bought ticket, or null if interrupted
     */
    public synchronized Ticket buyTicket() {
        while (tickets.isEmpty()) {
            try {
                wait(); // Wait until there is a ticket available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Ticket ticket = tickets.poll(); // Remove and return the ticket from the pool
        loggingService.info(Thread.currentThread().getName() + " bought ticket: " + ticket); // Log the purchase
        sendLogToFrontend(Thread.currentThread().getName() + " bought ticket: " + ticket); // Send log to frontend
        sendUpdates(); // Send updates to frontend
        notifyAll(); // Notify all waiting threads
        return ticket;
    }

    /**
     * Sends the current ticket count to the WebSocket topic.
     */
    private void sendUpdates() {
        messagingTemplate.convertAndSend("/topic/ticket-count", tickets.size()); // Broadcast ticket count
        loggingService.debug("Live ticket count updated: " + tickets.size()); // Log the update
    }

    /**
     * Sends a log message to the WebSocket topic.
     *
     * @param log the log message to send
     */
    private void sendLogToFrontend(String log) {
        messagingTemplate.convertAndSend("/topic/logs", log); // Send log message
    }
}
