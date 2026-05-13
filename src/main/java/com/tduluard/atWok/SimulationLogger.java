package com.tduluard.atWok;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class SimulationLogger {

    private final SimpMessagingTemplate messagingTemplate;

    public SimulationLogger(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendClientLog(String action, int clientId, String message) {
        Map<String, Object> payload = Map.of(
                "type", "client",
                "action", action,
                "clientId", clientId,
                "message", message
        );
        messagingTemplate.convertAndSend("/topic/logs", (Object) payload);
    }

    public void sendEmployeeLog(String action, String message) {
        Map<String, Object> payload = Map.of(
                "type", "employee",
                "action", action,
                "message", message
        );
        messagingTemplate.convertAndSend("/topic/logs", (Object) payload);
    }

    public void sendStandLog(String action, int clientId, String message) {
        Map<String, Object> payload = Map.of(
                "type", "stand",
                "action", action,
                "clientId", clientId,
                "message", message
        );
        messagingTemplate.convertAndSend("/topic/logs", (Object) payload);
    }

    public void sendBuffetLog(String action, String message) {
        Map<String, Object> payload = Map.of(
                "type", "buffet",
                "action", action,
                "message", message
        );
        messagingTemplate.convertAndSend("/topic/logs", (Object) payload);
    }

    public void sendRestaurantLog(String action, String message) {
        Map<String, Object> payload = Map.of(
                "type", "restaurant",
                "action", action,
                "message", message
        );
        messagingTemplate.convertAndSend("/topic/logs", (Object) payload);
    }
}