package com.example.springdatabasicdemo.datafetcher;

import com.example.springdatabasicdemo.services.CargoService;
import com.iamruda.contractfirst.dtos.CargoDTO;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class CargoDataFetcher {
    private CargoService cargoService;

    @Autowired
    public void setCargoService(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @DgsQuery
    public List<CargoDTO> cargos(@InputArgument Long id) {
        if (id == null) {
            return cargoService.getAll();
        }
        CargoDTO one = cargoService.findById(id).orElse(new CargoDTO());
        return List.of(one);
    }

    @DgsMutation
    public CargoDTO addCargo(@InputArgument CargoDTO cargoDTO) {
        return cargoService.register(cargoDTO);
    }

    @DgsMutation
    public CargoDTO updateCargo(@InputArgument Long id, @InputArgument CargoDTO cargoDTO) {
        cargoDTO.setId(id);
        return cargoService.register(cargoDTO);
    }

    @DgsMutation
    public String deleteCargo(@InputArgument Long id) {
        try {
            cargoService.expel(id);
            return "Cargo with ID " + id + " was deleted.";
        } catch (Exception e) {
            return "Error deleting cargo with ID " + id + ": " + e.getMessage();
        }
    }

}
