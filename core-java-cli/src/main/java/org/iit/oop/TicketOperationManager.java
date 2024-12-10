package org.iit.oop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicketOperationManager {
    private final Config config;
    private boolean isRunning = false;
    final List<Thread> vendorThreads = new ArrayList<>();
    final List<Thread> customerThreads = new ArrayList<>();

    public TicketOperationManager(Config config) {
        this.config = config;
    }

    public synchronized void startOperations() {
        if (isRunning) {
            System.out.println("System is already running!");
            return;
        }
        isRunning = true;


        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
        System.out.println("==========Ticketing system started.==========");

        listenForStopKey();

        // Start vendor threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            Vendor vendor = new Vendor(i + 1, config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendor, "Vendor " + (i + 1));
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 0; i < config.getCustomerCount(); i++) {
            Customer customer = new Customer(i + 1, config.getCustomerRetrievalRate(), ticketPool);
            Thread customerThread = new Thread(customer, "Customer " + (i + 1));
            customerThreads.add(customerThread);
            customerThread.start();
        }

    }

    public synchronized void stopOperations() {
        if (!isRunning) {
            System.out.println("System is not running!");
            return;
        }
        isRunning = false;
        vendorThreads.forEach(Thread::interrupt);
        customerThreads.forEach(Thread::interrupt);
        System.out.println("==========Ticket operations stopped.==========");
    }

    public void listenForStopKey() {
        System.out.println("==========Press 'q' to stop the ticketing system.==========");
        Thread keyListenerThread = new Thread(() -> {
            try {
                while (isRunning) {
                    int key = System.in.read();
                    if (key == 'q') {
                        stopOperations();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        keyListenerThread.setDaemon(true);
        keyListenerThread.start();
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }
}
