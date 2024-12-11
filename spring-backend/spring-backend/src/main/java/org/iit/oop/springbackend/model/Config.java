package org.iit.oop.springbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iit.oop.springbackend.service.LoggingService;

import java.io.File;
import java.io.IOException;

public class Config {
    private int vendorCount;
    private int customerCount;
    private int ticketsPerCustomer;
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

//    public Config() throws IOException {
//        String filePath = "config.json";
//        File configFile = new File(filePath);
//        if (configFile.exists()) {
//            Config loadedConfig = loadConfigFromFile(filePath);
//            this.vendorCount = loadedConfig.getVendorCount();
//            this.customerCount = loadedConfig.getCustomerCount();
//            this.ticketsPerCustomer = loadedConfig.getTicketsPerCustomer();
//            this.maxTicketCapacity = loadedConfig.getMaxTicketCapacity();
//            this.totalTickets = loadedConfig.getTotalTickets();
//            this.ticketReleaseRate = loadedConfig.getTicketReleaseRate();
//            this.customerRetrievalRate = loadedConfig.getCustomerRetrievalRate();
//        } else {
//            LoggingService loggingService = new LoggingService();
//            loggingService.warn("No config file found at " + filePath);
//        }
//    }

    public void loadConfig() throws IOException {
        String filePath = "config.json";
        File configFile = new File(filePath);
        if (configFile.exists()) {
            Config loadedConfig = loadConfigFromFile(filePath);
            this.vendorCount = loadedConfig.getVendorCount();
            this.customerCount = loadedConfig.getCustomerCount();
            this.ticketsPerCustomer = loadedConfig.getTicketsPerCustomer();
            this.maxTicketCapacity = loadedConfig.getMaxTicketCapacity();
            this.totalTickets = loadedConfig.getTotalTickets();
            this.ticketReleaseRate = loadedConfig.getTicketReleaseRate();
            this.customerRetrievalRate = loadedConfig.getCustomerRetrievalRate();
        } else {
            LoggingService loggingService = new LoggingService();
            loggingService.warn("No config file found at " + filePath);
        }
    }

    public void configure(int vendorCount, int customerCount, int ticketsPerCustomer, int maxTicketCapacity, int totalTickets, int ticketReleaseRate, int customerRetrievalRate) {
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
        this.ticketsPerCustomer = ticketsPerCustomer;
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        try {
            saveConfigToFile();
        } catch (IOException e) {
            LoggingService loggingService = new LoggingService();
            loggingService.error("An error occurred while saving the configuration: " , e);
        }
    }



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

    public void saveConfigToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("config.json"), this);
    }

    public static Config loadConfigFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), Config.class);
    }
}

