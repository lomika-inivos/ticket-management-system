package org.iit.oop.model;

/**
 * The Ticket class represents a ticket for an event.
 * It contains details such as ticket ID, ticket price, and event name.
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
     * Returns a string representation of the Ticket object.
     *
     * @return a string representation of the Ticket object
     */
    @Override
    public String toString() {
        return "Ticket{" + "ticketId=" + ticketId + ", ticketPrice=" + ticketPrice + ", eventName=" + eventName + '}';
    }
}
