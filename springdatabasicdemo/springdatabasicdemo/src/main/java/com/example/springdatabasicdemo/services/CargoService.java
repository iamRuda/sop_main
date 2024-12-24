package com.example.springdatabasicdemo.services;

import com.iamruda.contractfirst.dtos.CargoDTO;

import java.util.List;
import java.util.Optional;

public interface CargoService {

    CargoDTO register(CargoDTO cargo);

    void expel(Long id);

    Optional<CargoDTO> findById(Long id);

    List<CargoDTO> getAll();
}
