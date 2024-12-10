package org.iit.oop;

public class Customer implements Runnable{
    private int customerId;
    private int customerRetrievalRate;
    private int numberOfTickets;
    private TicketPool ticketPool;

    public Customer(int customerId, int customerRetrievalRate, TicketPool ticketPool) {
        this.customerId = customerId;
        this.customerRetrievalRate = customerRetrievalRate;
//        this.numberOfTickets = numberOfTickets;
        this.ticketPool = ticketPool;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    @Override
    public void run() {
//        for (int i = 1; i <= numberOfTickets; i++) {
            Ticket ticket = ticketPool.buyTicket();
            System.out.println(Thread.currentThread().getName() + " bought " + ticket);
            try {
                Thread.sleep(customerRetrievalRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
//    }
}
