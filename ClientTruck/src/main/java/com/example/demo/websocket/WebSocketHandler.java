package com.example.demo.websocket;

import com.example.demo.models.Truck;
import com.example.demo.repositories.TruckRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final TruckRepository truckRepository;
    private WebSocketSession currentSession = null;
    private boolean connectionEstablished = false;

    public WebSocketHandler(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket connection established: " + session.getId());
        currentSession = session;
        connectionEstablished = true;

        sendNextTruck();
    }

    public void sendMessageToAll(String message) {
        if (currentSession != null) {
            try {
                currentSession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNextTruck() {
        if (connectionEstablished) {
            Optional<Truck> truck = truckRepository.findFirstByOrderByIdAsc();
            truck.ifPresent(t -> {
                try {
                    System.out.println("Sending truck with ID: " + t.getId());
                    String message = new ObjectMapper().writeValueAsString(t);
                    sendMessageToAll(message); // Send via WebSocket
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void truckAcknowledged(Truck truck) {
        System.out.println("Truck has been acknowledged.");
        if (currentSession != null) {
            try {
                String message = "Truck has been acknowledged by the user.";
                sendMessageToAll(message);
                truckRepository.delete(truck);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnectionEstablished() {
        return connectionEstablished;
    }
}
