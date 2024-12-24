package com.example.springdatabasicdemo.controllers.inspector;

import com.example.springdatabasicdemo.services.InspectorService;
import com.iamruda.contractfirst.controllers.InspectorApi;
import com.iamruda.contractfirst.dtos.Action;
import com.iamruda.contractfirst.dtos.InspectorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/inspectors")
public class InspectorController implements InspectorApi {

    private InspectorService inspectorService;

    @Autowired
    public void setInspectorService(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    @GetMapping
    public List<InspectorDTO> getAllInspectors() {
        List<InspectorDTO> inspectors = inspectorService.getAll();
        return inspectors.stream()
                .map(this::createLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectorDTO> getInspectorById(@PathVariable Long id) throws InspectorNotFoundException {
        InspectorDTO inspectorDTO = inspectorService.findById(id)
                .orElseThrow(() -> new InspectorNotFoundException(id));
        inspectorDTO = createLinks(inspectorDTO);
        return ResponseEntity.ok(inspectorDTO);
    }

    @PostMapping
    public ResponseEntity<InspectorDTO> createInspector(@RequestBody InspectorDTO newInspector) {
        InspectorDTO createdInspector = inspectorService.register(newInspector);
        createdInspector = createLinks(createdInspector);
        return ResponseEntity.ok(createdInspector);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspectorDTO> updateInspector(@PathVariable Long id, @RequestBody InspectorDTO updatedInspector) throws InspectorNotFoundException {
        InspectorDTO existingInspector = inspectorService.findById(id)
                .orElseThrow(() -> new InspectorNotFoundException(id));
        updatedInspector.setId(id); // Ensure the ID is set for the update
        InspectorDTO savedInspector = inspectorService.register(updatedInspector);
        savedInspector = createLinks(savedInspector);
        return ResponseEntity.ok(savedInspector);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspector(@PathVariable Long id) {
        inspectorService.expel(id);
        return ResponseEntity.noContent().build();
    }

    private InspectorDTO createLinks(InspectorDTO inspectorDTO) {
        Link selfLink = linkTo(InspectorController.class).slash(inspectorDTO.getId()).withSelfRel();
        Link allInspectorsLink = linkTo(InspectorController.class).withRel("allInspectors");

        inspectorDTO.add(selfLink);
        inspectorDTO.add(allInspectorsLink);

        String updateHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectorController.class).updateInspector(inspectorDTO.getId(), null)).toUri().toString();
        String deleteHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectorController.class).deleteInspector(inspectorDTO.getId())).toUri().toString();
        String createHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InspectorController.class).createInspector(inspectorDTO)).toUri().toString();

        inspectorDTO.addAction("update", new Action(updateHref, "PUT", "application/json"));
        inspectorDTO.addAction("delete", new Action(deleteHref, "DELETE"));
        inspectorDTO.addAction("create", new Action(createHref, "POST", "application/json"));

        return inspectorDTO;
    }
}
