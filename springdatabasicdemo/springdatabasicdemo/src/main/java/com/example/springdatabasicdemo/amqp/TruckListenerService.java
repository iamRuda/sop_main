package com.example.springdatabasicdemo.amqp;

import com.example.springdatabasicdemo.models.Truck;
import com.example.springdatabasicdemo.repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TruckListenerService {

    private final TruckRepository truckRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public TruckListenerService(TruckRepository truckRepository, RabbitTemplate rabbitTemplate) {
        this.truckRepository = truckRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "checkedTruckQueue")
    public void receiveTruckMessage(Truck truck) {
        System.out.println("Message with Truck:");
        System.out.println("ID: " + truck.getId());
        System.out.println("Brand: " + truck.getBrand());
        System.out.println("Model: " + truck.getModel());
        System.out.println("License Plate: " + truck.getLicensePlate());
        System.out.println("Year Of Manufacture: " + truck.getYearOfManufacture());
        System.out.println("Checked: " + truck.getChecked());

        Truck existingTruck = truckRepository.findById(truck.getId())
                .orElseThrow(() -> new RuntimeException("Truck not found with ID: " + truck.getId()));

        existingTruck.setChecked(truck.getChecked());

        truckRepository.save(existingTruck);

        System.out.println("Truck updated: Checked = " + existingTruck.getChecked());
    }
}
