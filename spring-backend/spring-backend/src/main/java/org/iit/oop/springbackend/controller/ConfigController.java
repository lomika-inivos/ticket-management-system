package org.iit.oop.springbackend.controller;

import org.iit.oop.springbackend.model.Config;
import org.iit.oop.springbackend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("api/v1/config")
public class ConfigController {
    private final ConfigService configService;

    /**
     * Constructor for ConfigController.
     * @param configService the service to handle configuration logic
     */
    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * Endpoint to get the current configuration.
     * @return the current configuration
     * @throws IOException if an I/O error occurs
     */
    @GetMapping()
    public Config getConfig() throws IOException {
        // Retrieve and return the current configuration
        return configService.getConfig();
    }

    /**
     * Endpoint to update the system configuration.
     * @param config the new configuration to be applied
     * @return a response message indicating the result of the operation
     * @throws IOException if an I/O error occurs
     */
    @PostMapping()
    public Map<String, String> configureSystem(@RequestBody Config config) throws IOException {
        // Update the system configuration with the provided values
        configService.configureSystem(
            config.getVendorCount(), 
            config.getCustomerCount(), 
            config.getTicketsPerCustomer(), 
            config.getMaxTicketCapacity(), 
            config.getTotalTickets(), 
            config.getTicketReleaseRate(), 
            config.getCustomerRetrievalRate()
        );
        
        // Prepare and return a response message
        Map<String, String> response = new HashMap<>();
        response.put("message", "Configuration updated successfully");
        return response;
    }
}
