package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.models.Cargo;
import com.example.springdatabasicdemo.repositories.CargoRepository;
import com.example.springdatabasicdemo.services.CargoService;
import com.example.springdatabasicdemo.services.TruckService;
import com.iamruda.contractfirst.dtos.CargoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TruckService truckService;

    @Override
    public CargoDTO register(CargoDTO cargoDTO) {
        if (cargoDTO.getTruckId() != null) {
            truckService.addCargoToTruck(cargoDTO.getTruckId(), cargoDTO);
        }

        Cargo cargo = modelMapper.map(cargoDTO, Cargo.class);
        return modelMapper.map(cargoRepository.save(cargo), CargoDTO.class);
    }

    @Override
    public void expel(Long id) {
        cargoRepository.deleteById(id);
    }

    @Override
    public Optional<CargoDTO> findById(Long id) {
        return cargoRepository.findById(id).map(cargo -> modelMapper.map(cargo, CargoDTO.class));
    }

    @Override
    public List<CargoDTO> getAll() {
        return cargoRepository.findAll().stream()
                .map(cargo -> modelMapper.map(cargo, CargoDTO.class))
                .collect(Collectors.toList());
    }
}
