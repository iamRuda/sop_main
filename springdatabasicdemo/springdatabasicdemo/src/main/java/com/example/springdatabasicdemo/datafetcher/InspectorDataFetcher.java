package com.example.springdatabasicdemo.datafetcher;

import com.example.springdatabasicdemo.services.InspectorService;
import com.iamruda.contractfirst.dtos.InspectorDTO;
import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@DgsComponent
public class InspectorDataFetcher {
    private InspectorService inspectorService;

    @Autowired
    public void setInspectorService(InspectorService inspectorService) {
        this.inspectorService = inspectorService;
    }

    @DgsQuery
    public List<InspectorDTO> inspectors(@InputArgument Long id) {
        if (id == null) {
            return inspectorService.getAll();
        }
        InspectorDTO one = inspectorService.findById(id).orElse(new InspectorDTO());
        return List.of(one);
    }

    @DgsMutation
    public InspectorDTO addInspector(@InputArgument InspectorDTO inspectorDTO) {
        return inspectorService.register(inspectorDTO);
    }

    @DgsMutation
    public InspectorDTO updateInspector(@InputArgument Long id, @InputArgument InspectorDTO inspectorDTO) {
        inspectorDTO.setId(id);
        return inspectorService.register(inspectorDTO);
    }

    @DgsMutation
    public String deleteInspector(@InputArgument Long id) {
        try {
            inspectorService.expel(id);
            return "Inspector with ID " + id + " was deleted.";
        } catch (Exception e) {
            return "Error deleting inspector with ID " + id + ": " + e.getMessage();
        }
    }
}
