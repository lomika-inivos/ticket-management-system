package org.iit.oop.springbackend.model;

import org.iit.oop.springbackend.service.LoggingService;

public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;

    LoggingService loggingService = new LoggingService();

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
                loggingService.info(Thread.currentThread().getName() + " stopped.");
            }
        }
    }
}
