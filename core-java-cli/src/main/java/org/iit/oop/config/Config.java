package org.iit.oop.config;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.iit.oop.utils.validation.getValidInput;

public class Config {
    private int vendorCount;
    private int customerCount;
    private int ticketsPerCustomer;
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;


    public int getVendorCount() {
        return vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getTicketsPerCustomer() {
        return ticketsPerCustomer;
    }


    private void saveToFile() {
        try (FileWriter writer = new FileWriter("config.json")) {
            Gson gson = new Gson();
            gson.toJson(this, writer);
            System.out.println("Configuration saved to config.json");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the configuration: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try (FileReader reader = new FileReader("config.json")) {
            Gson gson = new Gson();
            Config loadedConfig = gson.fromJson(reader, Config.class);
            if (loadedConfig != null) {
                this.vendorCount = loadedConfig.vendorCount;
                this.customerCount = loadedConfig.customerCount;
                this.ticketsPerCustomer =loadedConfig.ticketsPerCustomer;
                this.maxTicketCapacity = loadedConfig.maxTicketCapacity;
                this.totalTickets = loadedConfig.totalTickets;
                this.ticketReleaseRate = loadedConfig.ticketReleaseRate;
                this.customerRetrievalRate = loadedConfig.customerRetrievalRate;
            }
            System.out.println("Configuration loaded from config.json");
        } catch (IOException e) {
            System.out.println("An error occurred while loading the configuration: " + e.getMessage());
        }
    }

    public void configureSystem(Scanner scanner) {
        System.out.println("=====System Configurations====");
        this.vendorCount = getValidInput(scanner, "Enter no. of vendors want to create: ");
        this.customerCount = getValidInput(scanner, "Enter no. of customers want to create: ");
        this.ticketsPerCustomer = getValidInput(scanner, "Enter no. of tickets per customer: ");
        this.totalTickets = getValidInput(scanner, "Enter total tickets: ");
        this.ticketReleaseRate = getValidInput(scanner, "Enter ticket release rate: ");
        this.customerRetrievalRate = getValidInput(scanner, "Enter customer retrieval rate: ");
        this.maxTicketCapacity = getValidInput(scanner, "Enter max ticket capacity: ");
        saveToFile();
        System.out.println("====Configuration complete!====");
    }


}