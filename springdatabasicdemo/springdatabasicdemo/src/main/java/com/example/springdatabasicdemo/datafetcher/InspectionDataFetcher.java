package com.example.springdatabasicdemo.datafetcher;

import com.example.springdatabasicdemo.services.InspectionService;
import com.iamruda.contractfirst.dtos.InspectionDTO;
import com.netflix.graphql.dgs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class InspectionDataFetcher {

    private InspectionService inspectionService;

    @Autowired
    public void setInspectionService(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @DgsQuery
    public List<InspectionDTO> inspections(@InputArgument Long id) {
        if (id == null) {
            return inspectionService.getAll();
        }
        InspectionDTO one = inspectionService.findById(id).orElse(new InspectionDTO());
        return List.of(one);
    }

    @DgsMutation
    public InspectionDTO addInspection(@InputArgument InspectionDTO inspectionDTO) {
        return inspectionService.register(inspectionDTO);
    }

    @DgsMutation
    public InspectionDTO updateInspection(@InputArgument Long id, @InputArgument InspectionDTO inspectionDTO) {
        inspectionDTO.setId(id);
        return inspectionService.register(inspectionDTO);
    }

    @DgsMutation
    public String deleteInspection(@InputArgument Long id) {
        try {
            inspectionService.expel(id);
            return "Inspection with ID " + id + " was deleted.";
        } catch (Exception e) {
            return "Error deleting inspection with ID " + id + ": " + e.getMessage();
        }
    }
}
