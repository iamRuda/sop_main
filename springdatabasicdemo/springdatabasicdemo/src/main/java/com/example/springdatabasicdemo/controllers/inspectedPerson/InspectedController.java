package com.example.springdatabasicdemo.controllers.inspectedPerson;

import com.example.springdatabasicdemo.services.InspectedPersonService;
import com.iamruda.contractfirst.controllers.InspectedApi;
import com.iamruda.contractfirst.dtos.Action;
import com.iamruda.contractfirst.dtos.InspectedPersonDTO;
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
@RequestMapping("/inspected")
public class InspectedController implements InspectedApi {

    private InspectedPersonService inspectedPersonService;

    private RabbitTemplate rabbitTemplate;

    private final String inspectedQueue = "inspectedQueue";

    @Autowired
    public void setInspectedPersonService(InspectedPersonService inspectedPersonService, RabbitTemplate rabbitTemplate) {
        this.inspectedPersonService = inspectedPersonService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<InspectedPersonDTO> createInspectedPerson(@RequestBody InspectedPersonDTO newInspectedPerson) {
        InspectedPersonDTO createdInspectedPerson = inspectedPersonService.register(newInspectedPerson);
        createdInspectedPerson = createLinks(createdInspectedPerson);
        return ResponseEntity.ok(createdInspectedPerson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectedPersonDTO> getInspectedPersonById(@PathVariable Long id) {
        InspectedPersonDTO inspectedPersonDTO = inspectedPersonService.findById(id)
                .orElseThrow(() -> new InspectedNotFoundException(id));
        inspectedPersonDTO = createLinks(inspectedPersonDTO);
        return ResponseEntity.ok(inspectedPersonDTO);
    }

    @GetMapping
    public List<InspectedPersonDTO> getAllInspectedPersons() {
        List<InspectedPersonDTO> inspectedPersons = inspectedPersonService.getAll();
        return inspectedPersons.stream()
                .map(this::createLinks)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectedPersonDTO> updateInspectedPerson(@PathVariable Long id, @RequestBody InspectedPersonDTO updatedInspectedPerson) {
        InspectedPersonDTO existingInspectedPerson = inspectedPersonService.findById(id)
                .orElseThrow(() -> new InspectedNotFoundException(id));
        updatedInspectedPerson.setId(id); // Ensure the ID is set for the update
        InspectedPersonDTO savedInspectedPerson = inspectedPersonService.register(updatedInspectedPerson);
        savedInspectedPerson = createLinks(savedInspectedPerson);
        return ResponseEntity.ok(savedInspectedPerson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspectedPerson(@PathVariable Long id) {
        inspectedPersonService.expel(id);
        return ResponseEntity.noContent().build();
    }

    private InspectedPersonDTO createLinks(InspectedPersonDTO inspectedPersonDTO) {
        Link selfLink = linkTo(InspectedController.class).slash(inspectedPersonDTO.getId()).withSelfRel();
        Link allInspectedPersonsLink = linkTo(InspectedController.class).withRel("allInspectedPersons");

        inspectedPersonDTO.add(selfLink);
        inspectedPersonDTO.add(allInspectedPersonsLink);

        String updateHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectedController.class).updateInspectedPerson(inspectedPersonDTO.getId(), null)).toUri().toString();
        String deleteHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectedController.class).deleteInspectedPerson(inspectedPersonDTO.getId())).toUri().toString();
        String createHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectedController.class).createInspectedPerson(inspectedPersonDTO)).toUri().toString();

        inspectedPersonDTO.addAction("update", new Action(updateHref, "PUT", "application/json"));
        inspectedPersonDTO.addAction("delete", new Action(deleteHref, "DELETE"));
        inspectedPersonDTO.addAction("create", new Action(createHref, "POST", "application/json"));

        return inspectedPersonDTO;
    }

    @GetMapping("/sendToQueue/{id}")
    public ResponseEntity<String> sendInspectedToQueue(@PathVariable Long id) {
        return inspectedPersonService.findById(id)
                .map(inspectedPersonDTO -> {
                    // Отправляем TruckDTO в очередь RabbitMQ
                    rabbitTemplate.convertAndSend(inspectedQueue, inspectedPersonDTO);
                    return ResponseEntity.ok("Inspected with ID " + id + " was sent to the queue.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
