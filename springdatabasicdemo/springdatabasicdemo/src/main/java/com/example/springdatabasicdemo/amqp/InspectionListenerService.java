package com.example.springdatabasicdemo.amqp;

import com.example.springdatabasicdemo.models.InspectedPerson;
import com.example.springdatabasicdemo.models.Inspection;
import com.example.springdatabasicdemo.repositories.InspectedPersonRepository;
import com.example.springdatabasicdemo.repositories.InspectionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InspectionListenerService {

    private final InspectionRepository inspectionRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public InspectionListenerService(InspectionRepository inspectionRepository, RabbitTemplate rabbitTemplate) {
        this.inspectionRepository = inspectionRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "checkedInspectionQueue")
    public void receiveTruckMessage(Inspection inspection) {
        System.out.println("Message with Inspection:");
        System.out.println("ID: " + inspection.getId());
        System.out.println("Access: " + inspection.getAccess());

        Inspection exitingInspection = inspectionRepository.findById(inspection.getId())
                .orElseThrow(() -> new RuntimeException("Inspected not found with ID: " + inspection.getId()));

        inspection.setAccess(inspection.getAccess());

        inspectionRepository.save(exitingInspection);

        System.out.println("Inspection updated: Access = " + exitingInspection.getAccess());
    }
}
