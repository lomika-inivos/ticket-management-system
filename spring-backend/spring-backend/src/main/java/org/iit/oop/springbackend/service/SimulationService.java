package org.iit.oop.springbackend.service;

import org.iit.oop.springbackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {
    Config config = new Config();
    private boolean isRunning = false;
    final List<Thread> vendorThreads = new ArrayList<>();
    final List<Thread> customerThreads = new ArrayList<>();
    private final LoggingService loggingService;

    @Autowired
    public SimulationService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }


    public synchronized void startOperations() throws IOException {
        config.loadConfig();
        if (isRunning) {
            loggingService.warn("System is already running!");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "System is already running!");
        }
        isRunning = true;


        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
       loggingService.info("Ticketing system started.");

        // Start vendor threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            Vendor vendor = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendor, "Vendor " + (i + 1));
            loggingService.info("Vendor " + (i + 1) + " started.");
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 0; i < config.getCustomerCount(); i++) {
            Customer customer = new Customer(config.getCustomerRetrievalRate(), config.getTicketsPerCustomer(), ticketPool);
            Thread customerThread = new Thread(customer, "Customer " + (i + 1));
            loggingService.info("Customer " + (i + 1) + " started.");
            customerThreads.add(customerThread);
            customerThread.start();
        }

        loggingService.info("Ticketing process initialized with " + config.getVendorCount() + " vendors and " + config.getCustomerCount() + " customers.");

    }

    public synchronized void stopOperations() {
        if (!isRunning) {
            loggingService.warn("System is not running!");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "System is not running!");
        }
        isRunning = false;
        vendorThreads.forEach(Thread::interrupt);
        customerThreads.forEach(Thread::interrupt);
        loggingService.info("Ticket operations stopped.");
    }
}
