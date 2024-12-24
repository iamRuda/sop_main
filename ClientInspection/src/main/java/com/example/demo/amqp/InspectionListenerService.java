package com.example.demo.amqp;

import com.example.demo.dtos.InspectionDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectionListenerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public InspectionListenerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${app.queue.inspection}")
    public void receiveTruckMessage(InspectionDTO inspection) {
        System.out.println("Message with Truck:");
        System.out.println("ID: " + inspection.getId());
        System.out.println("Checked: " + inspection.getAccess());

        inspection.setAccess(true);

        System.out.println("Updated Inspectionn (checked = true):");

        rabbitTemplate.convertAndSend("checkedInspectionQueue", inspection);

        System.out.println("Updated Inspection send in queue.");
    }
}
