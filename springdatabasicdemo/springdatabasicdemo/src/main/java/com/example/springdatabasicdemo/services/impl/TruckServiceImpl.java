package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.controllers.cargo.CargoController;
import com.example.springdatabasicdemo.models.Cargo;
import com.example.springdatabasicdemo.models.Truck;
import com.example.springdatabasicdemo.repositories.TruckRepository;
import com.example.springdatabasicdemo.services.TruckService;
import com.iamruda.contractfirst.dtos.CargoDTO;
import com.iamruda.contractfirst.dtos.TruckDTO;
import com.iamruda.contractfirst.dtos.TruckHALDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TruckServiceImpl implements TruckService {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TruckDTO convertToDTO(Truck truck) {
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setId(truck.getId());
        truckDTO.setBrand(truck.getBrand());
        truckDTO.setModel(truck.getModel());
        truckDTO.setLicensePlate(truck.getLicensePlate());
        truckDTO.setYearOfManufacture(truck.getYearOfManufacture());
        truckDTO.setChecked(truck.getChecked());

        // Populate cargoIds
        Set<Long> cargoIds = truck.getCargos().stream()
                .map(Cargo::getId)
                .collect(Collectors.toSet());
        truckDTO.setCargoIds(cargoIds);

        return truckDTO;
    }

    @Override
    public TruckHALDTO convertToHALDTO(Truck truck) {
        TruckHALDTO truckDTO = new TruckHALDTO();
        truckDTO.setId(truck.getId());
        truckDTO.setBrand(truck.getBrand());
        truckDTO.setModel(truck.getModel());
        truckDTO.setLicensePlate(truck.getLicensePlate());
        truckDTO.setYearOfManufacture(truck.getYearOfManufacture());
        truckDTO.setChecked(truck.getChecked());

        Set<Long> cargoIds = truck.getCargos().stream()
                .map(Cargo::getId)
                .collect(Collectors.toSet());
        truckDTO.setCargoIds(cargoIds);


        List<Cargo> cargoList = new ArrayList<>(truck.getCargos());
        List<Link> cargoLinks = IntStream.range(0, cargoList.size())
                .mapToObj(i -> {
                    Long cargoId = cargoList.get(i).getId();
                    return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CargoController.class).getCargoById(cargoId)).withRel("cargo" + i);
                })
                .collect(Collectors.toList());

        truckDTO.set_links(cargoLinks);

        return truckDTO;
    }



    @Override
    public TruckDTO register(TruckDTO truckDTO) {
        Truck truck = modelMapper.map(truckDTO, Truck.class);
        truck = truckRepository.save(truck);
        return convertToDTO(truck);
    }

    @Override
    public TruckHALDTO registerHAL(TruckDTO truckDTO) {
        Truck truck = modelMapper.map(truckDTO, Truck.class);
        truck = truckRepository.save(truck);
        return convertToHALDTO(truck);
    }

    @Override
    public void expel(Long id) {
        truckRepository.deleteById(id);
    }

    @Override
    public Optional<TruckDTO> findById(Long id) {
        return truckRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public List<TruckDTO> getAll() {
        return truckRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TruckHALDTO> getAllHAL() {
        return truckRepository.findAll().stream()
                .map(this::convertToHALDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TruckHALDTO> findByIdHAL(Long id) {
        return truckRepository.findById(id)
                .map(this::convertToHALDTO);
    }

    @Override
    public void addCargoToTruck(Long truckId, CargoDTO cargoDTO) {
        Truck truck = truckRepository.findById(truckId)
                .orElseThrow(() -> new EntityNotFoundException("Truck not found with id: " + truckId));

        // Map CargoDTO to Cargo entity
        Cargo cargo = modelMapper.map(cargoDTO, Cargo.class);
        cargo.setTruck(truck);

        truck.getCargos().add(cargo);

        truckRepository.save(truck);
    }
}
