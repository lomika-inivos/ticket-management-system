package org.iit.oop.springbackend.controller;

import org.iit.oop.springbackend.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for handling simulation operations.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("api/v1/simulation")
public class SimulationController {
    private final SimulationService simulationService;

    /**
     * Constructor for SimulationController.
     * 
     * @param simulationService the service to handle simulation operations
     */
    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    /**
     * Endpoint to start the simulation.
     * 
     * @return a map containing a success message
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/start")
    public Map<String, String> startSimulation() throws IOException {
        // Start the simulation operations
        simulationService.startOperations();
        
        // Prepare the response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation started successfully.");
        return response;
    }

    /**
     * Endpoint to stop the simulation.
     * 
     * @return a map containing a success message
     */
    @PostMapping("/stop")
    public Map<String, String> stopSimulation() {
        // Stop the simulation operations
        simulationService.stopOperations();
        
        // Prepare the response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation stopped successfully.");
        return response;
    }
}
