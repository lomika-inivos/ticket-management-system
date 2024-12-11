package org.iit.oop.springbackend.model;

import org.iit.oop.springbackend.service.LoggingService;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final int maxCapacity;
    private final Queue<Ticket> tickets;
    private final AtomicInteger ticketIdGenerator;

    LoggingService loggingService = new LoggingService();


    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        tickets = new ConcurrentLinkedQueue<>();
        this.ticketIdGenerator = new AtomicInteger(1);

    }
    // This method is gonna called by the vendor
    public synchronized void addTicket(double ticketPrice, String eventName) {
        while (tickets.size() >= maxCapacity) { // Maximum capacity is reached so vendor won't be able to add new ticket
            // This method will called by the vendor to add tickets to the queue
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        int ticketId = ticketIdGenerator.getAndIncrement();
        Ticket ticket = new Ticket(ticketId, ticketPrice, eventName);
        tickets.add(ticket);
        loggingService.info(Thread.currentThread().getName() + " added ticket: " + ticket);
        notifyAll();
    }

    // This method is gonna called by the customer
    public synchronized Ticket buyTicket() {
        while (tickets.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Ticket ticket = tickets.poll();
        notifyAll();
        return ticket;
    }

}