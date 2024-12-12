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

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping()
    public Config getConfig() throws IOException {
        return configService.getConfig();
//        return new Config();
    }

    @PostMapping()
    public Map<String, String> configureSystem(@RequestBody Config config) throws IOException {
        configService.configureSystem(config.getVendorCount(), config.getCustomerCount(), config.getTicketsPerCustomer(), config.getMaxTicketCapacity(), config.getTotalTickets(), config.getTicketReleaseRate(), config.getCustomerRetrievalRate());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Configuration updated successfully");
        return response;
    }

}
