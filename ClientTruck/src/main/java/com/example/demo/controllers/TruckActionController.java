package com.example.demo.controllers;

import com.example.demo.models.Truck;
import com.example.demo.repositories.TruckRepository;
import com.example.demo.websocket.WebSocketHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TruckActionController {

    private final TruckRepository truckRepository;
    private final RabbitTemplate rabbitTemplate;
    private final WebSocketHandler webSocketHandler;

    @Autowired
    public TruckActionController(TruckRepository truckRepository, RabbitTemplate rabbitTemplate, WebSocketHandler webSocketHandler) {
        this.truckRepository = truckRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.webSocketHandler = webSocketHandler;
    }

    @GetMapping("/")
    public String getNotificationPage() {
        return "notifications";
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<String> handleTruckAction(@PathVariable Long id, @RequestParam boolean check) {
        Truck truck = truckRepository.findById(id).orElseThrow(() -> new RuntimeException("Truck not found"));

        // Отправляем обратно через RabbitMQ обновлённую сущность
        truck.setChecked(check);
        rabbitTemplate.convertAndSend("checkedTruckQueue", truck);

        webSocketHandler.truckAcknowledged(truck);

        // Возвращаем ответ в зависимости от действия
        String responseMessage = check ? "Truck confirmed" : "Truck rejected";
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/check-truck")
    public String checkTruck() {
        // Каждый раз при вызове страницы вызываем метод отправки первого грузовика
        webSocketHandler.sendNextTruck();
        return "truck-status";  // Вернуть представление страницы (можно использовать статус или другую страницу)
    }

}
