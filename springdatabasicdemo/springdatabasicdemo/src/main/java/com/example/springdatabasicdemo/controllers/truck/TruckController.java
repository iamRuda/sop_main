package com.example.springdatabasicdemo.controllers.truck;

import com.example.springdatabasicdemo.services.TruckService;
import com.iamruda.contractfirst.controllers.TruckApi;
import com.iamruda.contractfirst.dtos.Action;
import com.iamruda.contractfirst.dtos.TruckDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/trucks")
public class TruckController implements TruckApi {

    private final TruckService truckService;
    private final RabbitTemplate rabbitTemplate;
    private final String truckQueue = "truckQueue";

    @Autowired
    public TruckController(TruckService truckService, RabbitTemplate rabbitTemplate) {
        this.truckService = truckService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<TruckDTO> createTruck(@RequestBody TruckDTO truckDTO) {
        TruckDTO createdTruck = truckService.register(truckDTO);
        createdTruck = createLinks(createdTruck);
        return ResponseEntity.ok(createdTruck);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckDTO> getTruckById(@PathVariable Long id) {
        return truckService.findById(id)
                .map(truckDTO -> {
                    truckDTO = createLinks(truckDTO);
                    return ResponseEntity.ok(truckDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<TruckDTO> getAllTrucks() {
        return truckService.getAll().stream()
                .map(this::createLinks)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TruckDTO> updateTruck(@PathVariable Long id, @RequestBody TruckDTO truckDTO) {
        return truckService.findById(id)
                .map(existingTruck -> {
                    truckDTO.setId(id); // Ensure the ID is set for update
                    TruckDTO updatedTruck = truckService.register(truckDTO);
                    updatedTruck = createLinks(updatedTruck);
                    return ResponseEntity.ok(updatedTruck);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruck(@PathVariable Long id) {
        truckService.expel(id);
        return ResponseEntity.noContent().build();
    }

    private TruckDTO createLinks(TruckDTO truckDTO) {
        Link selfLink = linkTo(TruckController.class).slash(truckDTO.getId()).withSelfRel();
        Link allTrucksLink = linkTo(TruckController.class).withRel("allTrucks");

        truckDTO.add(selfLink);
        truckDTO.add(allTrucksLink);

        String updateHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TruckController.class).updateTruck(truckDTO.getId(), null)).toUri().toString();
        String deleteHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TruckController.class).deleteTruck(truckDTO.getId())).toUri().toString();
        String createHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TruckController.class).createTruck(truckDTO)).toUri().toString(); // Adding the create link

        truckDTO.addAction("update", new Action(updateHref, "PUT", "application/json"));
        truckDTO.addAction("delete", new Action(deleteHref, "DELETE"));
        truckDTO.addAction("create", new Action(createHref, "POST", "application/json")); // Adding action for create

        return truckDTO;
    }

    @GetMapping("/sendToQueue/{id}")
    public ResponseEntity<String> sendTruckToQueue(@PathVariable Long id) {
        return truckService.findById(id)
                .map(truckDTO -> {
                    rabbitTemplate.convertAndSend(truckQueue, truckDTO);
                    return ResponseEntity.ok("Truck with ID " + id + " was sent to the queue.");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
