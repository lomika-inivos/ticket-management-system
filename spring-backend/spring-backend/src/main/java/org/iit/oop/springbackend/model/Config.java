package org.iit.oop.springbackend.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.iit.oop.springbackend.service.LoggingService;

import java.io.File;
import java.io.IOException;

/**
 * Config class to manage the configuration settings for the ticket management system.
 * It provides methods to load and save configuration from/to a JSON file.
 */
public class Config {
    private int vendorCount;
    private int customerCount;
    private int ticketsPerCustomer;
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    /**
     * Loads the configuration from a JSON file.
     * If the file does not exist, logs a warning message.
     * 
     * @throws IOException if an I/O error occurs
     */
    public void loadConfig() throws IOException {
        String filePath = "config.json"; // Path to the configuration file
        File configFile = new File(filePath);
        if (configFile.exists()) {
            // Load configuration from file if it exists
            Config loadedConfig = loadConfigFromFile(filePath);
            this.vendorCount = loadedConfig.getVendorCount();
            this.customerCount = loadedConfig.getCustomerCount();
            this.ticketsPerCustomer = loadedConfig.getTicketsPerCustomer();
            this.maxTicketCapacity = loadedConfig.getMaxTicketCapacity();
            this.totalTickets = loadedConfig.getTotalTickets();
            this.ticketReleaseRate = loadedConfig.getTicketReleaseRate();
            this.customerRetrievalRate = loadedConfig.getCustomerRetrievalRate();
        } else {
            // Log a warning if the configuration file does not exist
            LoggingService loggingService = new LoggingService();
            loggingService.warn("No config file found at " + filePath);
        }
    }

    /**
     * Configures the settings and saves them to a JSON file.
     * 
     * @param vendorCount the number of vendors
     * @param customerCount the number of customers
     * @param ticketsPerCustomer the number of tickets per customer
     * @param maxTicketCapacity the maximum ticket capacity
     * @param totalTickets the total number of tickets
     * @param ticketReleaseRate the rate at which tickets are released
     * @param customerRetrievalRate the rate at which customers retrieve tickets
     */
    public void configure(int vendorCount, int customerCount, int ticketsPerCustomer, int maxTicketCapacity, int totalTickets, int ticketReleaseRate, int customerRetrievalRate) {
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
        this.ticketsPerCustomer = ticketsPerCustomer;
        this.maxTicketCapacity = maxTicketCapacity;
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        try {
            // Save the configuration to file
            saveConfigToFile();
        } catch (IOException e) {
            // Log an error if saving the configuration fails
            LoggingService loggingService = new LoggingService();
            loggingService.error("An error occurred while saving the configuration: ", e);
        }
    }

    // Getter methods for the configuration properties
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

    /**
     * Saves the current configuration to a JSON file.
     * 
     * @throws IOException if an I/O error occurs
     */
    public void saveConfigToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(); // Create an ObjectMapper instance
        objectMapper.writeValue(new File("config.json"), this); // Write the current configuration to file
    }

    /**
     * Loads the configuration from a JSON file.
     * 
     * @param filePath the path to the configuration file
     * @return the loaded Config object
     * @throws IOException if an I/O error occurs
     */
    public static Config loadConfigFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(); // Create an ObjectMapper instance
        return objectMapper.readValue(new File(filePath), Config.class); // Read the configuration from file
    }
}
