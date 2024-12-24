package com.example.springdatabasicdemo.controllers.inspections;

import com.example.springdatabasicdemo.controllers.inspectedPerson.InspectedController;
import com.example.springdatabasicdemo.controllers.inspector.InspectorController;
import com.example.springdatabasicdemo.controllers.truck.TruckController;
import com.example.springdatabasicdemo.services.InspectionService;
import com.iamruda.contractfirst.controllers.InspectionApi;
import com.iamruda.contractfirst.dtos.Action;
import com.iamruda.contractfirst.dtos.InspectionDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/inspections")
public class InspectionController implements InspectionApi {

    private InspectionService inspectionService;

    private RabbitTemplate rabbitTemplate;

    private final String inspectionQueue = "inspectionQueue";

    @Autowired
    public void setInspectionService(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping
    public ResponseEntity<InspectionDTO> createInspection(@RequestBody InspectionDTO newInspection) {
        InspectionDTO createdInspection = inspectionService.register(newInspection);
        createdInspection = createLinks(createdInspection);
        return ResponseEntity.ok(createdInspection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectionDTO> getInspectionById(@PathVariable Long id) {
        InspectionDTO inspectionDTO = inspectionService.findById(id)
                .orElseThrow(() -> new InspectionNotFoundException(id));
        inspectionDTO = createLinks(inspectionDTO);
        return ResponseEntity.ok(inspectionDTO);
    }

    @GetMapping
    public List<InspectionDTO> getAllInspections() {
        List<InspectionDTO> inspections = inspectionService.getAll();
        return inspections.stream()
                .map(this::createLinks)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectionDTO> updateInspection(@PathVariable Long id, @RequestBody InspectionDTO updatedInspection) {
        InspectionDTO existingInspection = inspectionService.findById(id)
                .orElseThrow(() -> new InspectionNotFoundException(id));
        updatedInspection.setId(id); // Ensure the ID is set for the update
        InspectionDTO savedInspection = inspectionService.register(updatedInspection);
        savedInspection = createLinks(savedInspection);
        return ResponseEntity.ok(savedInspection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspection(@PathVariable Long id) {
        inspectionService.expel(id);
        return ResponseEntity.noContent().build();
    }

    private InspectionDTO createLinks(InspectionDTO inspectionDTO) {
        Link selfLink = linkTo(InspectionController.class).slash(inspectionDTO.getId()).withSelfRel();
        Link allInspectionsLink = linkTo(InspectionController.class).withRel("allInspections");
        Link inspectorLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectorController.class).getInspectorById(inspectionDTO.getInspectorId())).withRel("inspector");
        Link inspectedPersonLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectedController.class).getInspectedPersonById(inspectionDTO.getInspectedPersonId())).withRel("inspectedPerson");
        Link truckLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TruckController.class).getTruckById(inspectionDTO.getTruckId())).withRel("truck");

        inspectionDTO.add(selfLink);
        inspectionDTO.add(allInspectionsLink);
        inspectionDTO.add(inspectorLink);
        inspectionDTO.add(inspectedPersonLink);
        inspectionDTO.add(truckLink);

        // Create action links
        String updateHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectionController.class).updateInspection(inspectionDTO.getId(), null)).toUri().toString();
        String deleteHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectionController.class).deleteInspection(inspectionDTO.getId())).toUri().toString();
        String createHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectionController.class).createInspection(inspectionDTO)).toUri().toString();

        inspectionDTO.addAction("update", new Action(updateHref, "PUT", "application/json"));
        inspectionDTO.addAction("delete", new Action(deleteHref, "DELETE"));
        inspectionDTO.addAction("create", new Action(createHref, "POST", "application/json"));

        return inspectionDTO;
    }

    @GetMapping("/sendToQueue/{id}")
    public ResponseEntity<String> sendInspectionToQueue(@PathVariable Long id) {
        return inspectionService.findById(id)
                .map(inspectionDTO -> {
                    // Отправляем TruckDTO в очередь RabbitMQ
                    rabbitTemplate.convertAndSend(inspectionQueue, inspectionDTO);
                    return ResponseEntity.ok("Inspection with ID " + id + " was sent to the queue.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
