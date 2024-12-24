package com.example.demo.amqp;

import com.example.demo.dtos.TruckDTO;
import com.example.demo.models.Truck;
import com.example.demo.repositories.TruckRepository;
import com.example.demo.websocket.WebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class TruckListenerService {

    private final TruckRepository truckRepository;
    private final WebSocketHandler webSocketHandler;

    private static final Logger logger = LoggerFactory.getLogger(TruckListenerService.class);

    @Autowired
    public TruckListenerService(TruckRepository truckRepository, WebSocketHandler webSocketHandler) {
        this.truckRepository = truckRepository;
        this.webSocketHandler = webSocketHandler;
    }

    @RabbitListener(queues = "${app.queue.truck}")
    public void receiveTruckMessage(TruckDTO truckDTO) {
        logger.info("Received truck DTO: {}", truckDTO);

        if (truckDTO == null || truckDTO.getId() == null) {
            logger.error("Received an invalid truck DTO, id is missing.");
            return;
        }

        Truck truckEntity = truckRepository.findById(truckDTO.getId())
                .map(existingTruck -> {
                    existingTruck.setBrand(truckDTO.getBrand());
                    existingTruck.setModel(truckDTO.getModel());
                    existingTruck.setLicensePlate(truckDTO.getLicensePlate());
                    existingTruck.setYearOfManufacture(truckDTO.getYearOfManufacture());
                    existingTruck.setChecked(truckDTO.getChecked());
                    return existingTruck;
                })
                .orElseGet(() -> {
                    logger.info("Truck not found. Creating new truck with ID: {}", truckDTO.getId());
                    Truck newTruck = new Truck(truckDTO.getBrand(), truckDTO.getModel(), truckDTO.getLicensePlate(), truckDTO.getYearOfManufacture(), truckDTO.getChecked());
                    newTruck.setId(truckDTO.getId());
                    return newTruck;
                });

        truckRepository.save(truckEntity);
        logger.info("Truck saved or updated: {}", truckEntity);

        List<Truck> allTrucks = truckRepository.findAll();
        logger.info("Current list of trucks in the repository: ");
        allTrucks.forEach(t -> logger.info("Truck ID: {}, Brand: {}, Model: {}", t.getId(), t.getBrand(), t.getModel()));

        if (webSocketHandler.isConnectionEstablished()) {
            webSocketHandler.sendNextTruck();
        }

    }
}