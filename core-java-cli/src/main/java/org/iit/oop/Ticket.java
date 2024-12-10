package org.iit.oop;


public class Ticket {
    private final int ticketId;
    private final double ticketPrice;
    private final String eventName;

    public Ticket(int ticketId, double ticketPrice, String eventName) {
        this.ticketId = ticketId;
        this.ticketPrice = ticketPrice;
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return "Ticket{" + "ticketId=" + ticketId + ", ticketPrice=" + ticketPrice + ", eventName=" + eventName + '}';
    }
}
