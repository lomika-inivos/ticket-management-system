package org.iit.oop.springbackend.model;

import org.iit.oop.springbackend.service.LoggingService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketPool {
    private final int maxCapacity;
    private final Queue<Ticket> tickets;
    private final AtomicInteger ticketIdGenerator;
    private final SimpMessagingTemplate messagingTemplate;
    private final LoggingService loggingService;

    public TicketPool(int maxCapacity, SimpMessagingTemplate messagingTemplate, LoggingService loggingService) {
        this.maxCapacity = maxCapacity;
        this.messagingTemplate = messagingTemplate;
        this.loggingService = loggingService;
        this.tickets = new ConcurrentLinkedQueue<>();
        this.ticketIdGenerator = new AtomicInteger(1);
    }

    // Called by the vendor
    public synchronized void addTicket(double ticketPrice, String eventName) {
        while (tickets.size() >= maxCapacity) {
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
        sendLogToFrontend(Thread.currentThread().getName() + " added ticket: " + ticket);
        sendUpdates();
        notifyAll();
    }

    // Called by the customer
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
        loggingService.info(Thread.currentThread().getName() + " bought ticket: " + ticket);
        sendLogToFrontend(Thread.currentThread().getName() + " bought ticket: " + ticket);
        sendUpdates();
        notifyAll();
        return ticket;
    }

    private void sendUpdates() {
        // Broadcast ticket count to WebSocket topic
        messagingTemplate.convertAndSend("/topic/ticket-count", tickets.size());
        loggingService.debug("Live ticket count updated: " + tickets.size());
    }

    private void sendLogToFrontend(String log) {
        messagingTemplate.convertAndSend("/topic/logs", log);
    }
}
