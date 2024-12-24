package com.example.demo.amqp;

import com.example.demo.dtos.InspectedPersonDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectedListenerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public InspectedListenerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${app.queue.inspected}")
    public void receiveTruckMessage(InspectedPersonDTO inspected) {
        System.out.println("Message with Truck:");
        System.out.println("ID: " + inspected.getId());
        System.out.println("Checked: " + inspected.getChecked());

        inspected.setChecked(true);

        System.out.println("Updated Inspected (checked = true):");

        rabbitTemplate.convertAndSend("checkedInspectedQueue", inspected);

        System.out.println("Updated Inspected send in queue.");
    }
}
