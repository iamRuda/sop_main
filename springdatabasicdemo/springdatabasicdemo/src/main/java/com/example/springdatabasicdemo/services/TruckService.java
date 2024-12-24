package com.example.springdatabasicdemo.services;

import com.example.springdatabasicdemo.models.Truck;
import com.iamruda.contractfirst.dtos.CargoDTO;
import com.iamruda.contractfirst.dtos.TruckDTO;
import com.iamruda.contractfirst.dtos.TruckHALDTO;

import java.util.List;
import java.util.Optional;

public interface TruckService {

    TruckDTO convertToDTO(Truck truck);

    TruckHALDTO convertToHALDTO(Truck truck);

    TruckDTO register(TruckDTO truck);

    TruckHALDTO registerHAL(TruckDTO truckDTO);

    void expel(Long id);

    Optional<TruckDTO> findById(Long id);

    List<TruckDTO> getAll();

    List<TruckHALDTO> getAllHAL();

    Optional<TruckHALDTO> findByIdHAL(Long id);

    void addCargoToTruck(Long truckId, CargoDTO cargoDTO);
}
