package com.tduluard.atWok;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SimulationController {

    private final SimulationLogger logger;

    public SimulationController(SimulationLogger logger) {
        this.logger = logger;
    }

    @MessageMapping("/start")
    public void startSimulation() {
        Restaurant restaurant = new Restaurant(logger);
        restaurant.startService(30);
    }
}