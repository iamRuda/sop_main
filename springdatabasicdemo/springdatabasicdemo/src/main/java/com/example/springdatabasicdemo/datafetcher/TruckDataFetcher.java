package com.example.springdatabasicdemo.datafetcher;

import com.example.springdatabasicdemo.services.TruckService;
import com.iamruda.contractfirst.dtos.TruckDTO;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@DgsComponent
public class TruckDataFetcher {

    private TruckService truckService;

    @Autowired
    public void setTruckService(TruckService truckService) {
        this.truckService = truckService;
    }

    @DgsQuery
    public List<TruckDTO> trucks(@InputArgument Long id) {
        if (id == null) {
            return truckService.getAll();
        }
        TruckDTO one = truckService.findById(id).orElse(new TruckDTO());
        return List.of(one);
    }

    @DgsMutation
    public TruckDTO addTruck(@InputArgument TruckDTO truckDTO) {
        return truckService.register(truckDTO);
    }

    @DgsMutation
    public TruckDTO updateTruck(@InputArgument Long id, @InputArgument TruckDTO truckDTO) {
        truckDTO.setId(id);
        return truckService.register(truckDTO);
    }

    @DgsMutation
    public String deleteTruck(@InputArgument Long id) {
        try {
            truckService.expel(id);
            return "Truck with ID " + id + " was deleted.";
        } catch (Exception e) {
            return "Error deleting truck with ID " + id + ": " + e.getMessage();
        }
    }
}
