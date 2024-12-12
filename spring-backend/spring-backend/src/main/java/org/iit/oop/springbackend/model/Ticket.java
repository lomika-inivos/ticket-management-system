package org.iit.oop.springbackend.model;

/**
 * Represents a ticket for an event.
 */
public class Ticket {
    // Unique identifier for the ticket
    private final int ticketId;
    
    // Price of the ticket
    private final double ticketPrice;
    
    // Name of the event for which the ticket is issued
    private final String eventName;

    /**
     * Constructs a new Ticket with the specified details.
     *
     * @param ticketId the unique identifier for the ticket
     * @param ticketPrice the price of the ticket
     * @param eventName the name of the event
     */
    public Ticket(int ticketId, double ticketPrice, String eventName) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.eventName = eventName;
    }

    /**
     * Returns a string representation of the ticket.
     *
     * @return a string representation of the ticket
     */
    @Override
    public String toString() {
        return "Ticket{" + "ticketId=" + ticketId + ", ticketPrice=" + ticketPrice + ", eventName=" + eventName + '}';
    }
}