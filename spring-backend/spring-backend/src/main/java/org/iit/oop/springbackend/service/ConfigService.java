package org.iit.oop.springbackend.service;


import org.iit.oop.springbackend.model.Config;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

@Service
public class ConfigService {

    LoggingService loggingService = new LoggingService();

    public Config getConfig() throws IOException {
        Config config = new Config();
        String filePath = "config.json";
        File configFile = new File(filePath);
        if (configFile.exists()) {
            config.loadConfig();
            return config;
        }
        loggingService.warn("No config file found at " + filePath);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuration file not found. Please configure first.");

    }

    public void configureSystem( int vendorCount, int customerCount, int ticketsPerCustomer, int maxTicketCapacity, int totalTickets, int ticketReleaseRate, int customerRetrievalRate)  {
        Config config = new Config();
        config.configure(vendorCount, customerCount, ticketsPerCustomer, maxTicketCapacity, totalTickets, ticketReleaseRate, customerRetrievalRate);
        loggingService.info("Configuration updated successfully");
    }

}
