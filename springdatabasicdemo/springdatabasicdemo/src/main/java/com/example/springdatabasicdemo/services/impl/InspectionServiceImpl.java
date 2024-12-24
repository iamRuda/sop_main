package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.models.Inspection;
import com.example.springdatabasicdemo.repositories.InspectionRepository;
import com.example.springdatabasicdemo.services.InspectionService;
import com.iamruda.contractfirst.dtos.InspectionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InspectionServiceImpl implements InspectionService {

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InspectionDTO register(InspectionDTO inspection) {
        var inspectionEntity = modelMapper.map(inspection, Inspection.class);
        var savedInspection = inspectionRepository.save(inspectionEntity);
        return modelMapper.map(savedInspection, InspectionDTO.class);
    }

    @Override
    public void expel(Long id) {
        inspectionRepository.deleteById(id);
    }

    @Override
    public Optional<InspectionDTO> findById(Long id) {
        return inspectionRepository.findById(id)
                .map(inspection -> modelMapper.map(inspection, InspectionDTO.class));
    }

    @Override
    public List<InspectionDTO> getAll() {
        return inspectionRepository.findAll().stream()
                .map(inspection -> modelMapper.map(inspection, InspectionDTO.class))
                .collect(Collectors.toList());
    }
}
