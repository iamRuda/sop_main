package com.example.springdatabasicdemo.controllers.cargo;

import com.example.springdatabasicdemo.services.CargoService;
import com.iamruda.contractfirst.controllers.CargoApi;
import com.iamruda.contractfirst.dtos.Action;
import com.iamruda.contractfirst.dtos.CargoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/cargos")
public class CargoController implements CargoApi {

    private CargoService cargoService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public ResponseEntity<CargoDTO> createCargo(@RequestBody CargoDTO newCargo) {
        CargoDTO createdCargo = cargoService.register(newCargo);
        createdCargo = createLinks(createdCargo);
        return ResponseEntity.ok(createdCargo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDTO> getCargoById(@PathVariable Long id) {
        CargoDTO cargoDTO = cargoService.findById(id)
                .orElseThrow(() -> new CargoNotFoundException(id));
        cargoDTO = createLinks(cargoDTO);
        return ResponseEntity.ok(cargoDTO);
    }

    @GetMapping
    public List<CargoDTO> getAllCargos() {
        List<CargoDTO> cargos = cargoService.getAll();
        return cargos.stream()
                .map(this::createLinks)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargoDTO> updateCargo(@PathVariable Long id, @RequestBody CargoDTO updatedCargo) {
        CargoDTO existingCargo = cargoService.findById(id)
                .orElseThrow(() -> new CargoNotFoundException(id));
        updatedCargo.setId(id); // Ensure the ID is set for the update
        CargoDTO savedCargo = cargoService.register(updatedCargo);
        savedCargo = createLinks(savedCargo);
        return ResponseEntity.ok(savedCargo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        cargoService.expel(id);
        return ResponseEntity.noContent().build();
    }

    private CargoDTO createLinks(CargoDTO cargoDTO) {
        Link selfLink = linkTo(CargoController.class).slash(cargoDTO.getId()).withSelfRel();
        Link allCargosLink = linkTo(CargoController.class).withRel("allCargos");

        cargoDTO.add(selfLink);
        cargoDTO.add(allCargosLink);

        String updateHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CargoController.class).updateCargo(cargoDTO.getId(), null)).toUri().toString();
        String deleteHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CargoController.class).deleteCargo(cargoDTO.getId())).toUri().toString();
        String createHref = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CargoController.class).createCargo(cargoDTO)).toUri().toString();

        cargoDTO.addAction("update", new Action(updateHref, "PUT", "application/json"));
        cargoDTO.addAction("delete", new Action(deleteHref, "DELETE"));
        cargoDTO.addAction("create", new Action(createHref, "POST", "application/json"));

        return cargoDTO;
    }
}