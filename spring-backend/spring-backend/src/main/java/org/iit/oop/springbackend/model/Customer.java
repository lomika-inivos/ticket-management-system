package org.iit.oop.springbackend.model;

import org.iit.oop.springbackend.service.LoggingService;

public class Customer implements Runnable{
    private final int customerRetrievalRate;
    private final int numberOfTickets;
    private final TicketPool ticketPool;

    LoggingService loggingService = new LoggingService();

    public Customer(int customerRetrievalRate, int numberOfTickets, TicketPool ticketPool) {
        this.customerRetrievalRate = customerRetrievalRate;
        this.numberOfTickets = numberOfTickets;
        this.ticketPool = ticketPool;
    }


    @Override
    public void run() {
        for (int i = 1; i <= numberOfTickets; i++) {
            Ticket ticket = ticketPool.buyTicket();
            loggingService.info(Thread.currentThread().getName() + " bought " + ticket);
            try {
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
