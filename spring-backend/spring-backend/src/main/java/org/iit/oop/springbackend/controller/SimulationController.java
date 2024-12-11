package org.iit.oop.springbackend.controller;

import org.iit.oop.springbackend.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/simulation")
public class SimulationController {
    private final SimulationService simulationService;

    @Autowired
    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/start")
    public Map<String, String> startSimulation() throws IOException {
        simulationService.startOperations();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation started successfully.");
        return response;
    }

    @PostMapping("/stop")
    public Map<String, String> stopSimulation() {
        simulationService.stopOperations();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation stopped successfully.");
        return response;
    }
}
