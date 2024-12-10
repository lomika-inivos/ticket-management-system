package org.iit.oop;

public class Vendor implements Runnable {
    private int vendorId;
    private int totalTickets;
    private int ticketReleaseRate;
    private TicketPool ticketPool;
    private int ticketId = 0;

    public Vendor(int vendorId, int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.vendorId = vendorId;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    public int getVendorId() {
        return vendorId;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    @Override
    public void run() {
        for(int i = 1; i <= totalTickets; i++) {
//            Ticket ticket = new Ticket(ticketId,1000, "Movie");
//            ticketPool.addTicket(ticket);
//            ticketId++;
//            System.out.println(Thread.currentThread().getName() + " added  " + ticket);
//            try {
//                Thread.sleep(ticketReleaseRate * 1000L); // Get ticketReleaseRate as the waiting time
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    double ticketPrice = Math.random() * 100 + 20; // Example price generation
                    String eventName = "Concert"; // Example event name
                    ticketPool.addTicket(ticketPrice, eventName);
                    Thread.sleep(ticketReleaseRate); // Simulate delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " stopped.");
            }
        }
    }
}

