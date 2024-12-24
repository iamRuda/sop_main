package com.example.springdatabasicdemo.datafetcher;

import com.example.springdatabasicdemo.services.InspectedPersonService;
import com.iamruda.contractfirst.dtos.InspectedPersonDTO;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@DgsComponent
public class InspectedDataFetcher {

    private InspectedPersonService inspectedService;

    @Autowired
    public void setInspectedService(InspectedPersonService inspectedService) {
        this.inspectedService = inspectedService;
    }

    @DgsQuery
    public List<InspectedPersonDTO> inspecteds(@InputArgument Long id) {
        if (id == null) {
            return inspectedService.getAll();
        }
        InspectedPersonDTO one = inspectedService.findById(id).orElse(new InspectedPersonDTO());
        return List.of(one);
    }

    @DgsMutation
    public InspectedPersonDTO addInspected(@InputArgument InspectedPersonDTO inspectedPersonDTO) {
        return inspectedService.register(inspectedPersonDTO);
    }

    @DgsMutation
    public InspectedPersonDTO updateInspected(@InputArgument Long id, @InputArgument InspectedPersonDTO inspectedPersonDTO) {
        inspectedPersonDTO.setId(id);
        return inspectedService.register(inspectedPersonDTO);
    }

    @DgsMutation
    public String deleteInspected(@InputArgument Long id) {
        try {
            inspectedService.expel(id);
            return "InspectedPerson with ID " + id + " was deleted.";
        } catch (Exception e) {
            return "Error deleting inspected person with ID " + id + ": " + e.getMessage();
        }
    }
}
