package org.iit.oop;

public class Customer implements Runnable{
    private final int customerRetrievalRate;
    private final int numberOfTickets;
    private final TicketPool ticketPool;

    public Customer(int customerRetrievalRate, int numberOfTickets, TicketPool ticketPool) {
        this.customerRetrievalRate = customerRetrievalRate;
        this.numberOfTickets = numberOfTickets;
        this.ticketPool = ticketPool;
    }


    @Override
    public void run() {
        for (int i = 1; i <= numberOfTickets; i++) {
            Ticket ticket = ticketPool.buyTicket();
            System.out.println(Thread.currentThread().getName() + " bought " + ticket);
            try {
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
