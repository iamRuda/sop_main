package com.example.springdatabasicdemo.amqp;

import com.example.springdatabasicdemo.models.InspectedPerson;
import com.example.springdatabasicdemo.repositories.InspectedPersonRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectedListenerService {

    private final InspectedPersonRepository inspectedPersonRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public InspectedListenerService(InspectedPersonRepository inspectedPersonRepository, RabbitTemplate rabbitTemplate) {
        this.inspectedPersonRepository = inspectedPersonRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "checkedInspectedQueue")
    public void receiveTruckMessage(InspectedPerson inspected) {
        System.out.println("Message with Inspected:");
        System.out.println("ID: " + inspected.getId());
        System.out.println("Checked: " + inspected.getChecked());

        InspectedPerson exitingInspectedPerson = inspectedPersonRepository.findById(inspected.getId())
                .orElseThrow(() -> new RuntimeException("Inspected not found with ID: " + inspected.getId()));

        exitingInspectedPerson.setChecked(inspected.getChecked());

        inspectedPersonRepository.save(exitingInspectedPerson);

        System.out.println("Inspected updated: Checked = " + exitingInspectedPerson.getChecked());
    }
}
