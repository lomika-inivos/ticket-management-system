package org.iit.oop.model;

import org.iit.oop.operation.TicketPool;

public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for(int i = 1; i <= totalTickets; i++) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    double ticketPrice = Math.random() * 100 + 20; // Example price generation
                    String eventName = "Concert"; // Example event name
                    ticketPool.addTicket(ticketPrice, eventName);
                    Thread.sleep(ticketReleaseRate * 1000L); // Simulate delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " stopped.");
            }
        }
    }
}

