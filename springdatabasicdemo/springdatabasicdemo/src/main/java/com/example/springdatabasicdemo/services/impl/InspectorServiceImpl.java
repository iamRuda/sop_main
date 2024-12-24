package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.models.Inspector;
import com.example.springdatabasicdemo.repositories.InspectorRepository;
import com.example.springdatabasicdemo.services.InspectorService;
import com.iamruda.contractfirst.dtos.InspectorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InspectorServiceImpl implements InspectorService {

    @Autowired
    private InspectorRepository inspectorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InspectorDTO register(InspectorDTO inspectorDTO) {
        Inspector inspector = modelMapper.map(inspectorDTO, Inspector.class);
        return modelMapper.map(inspectorRepository.save(inspector), InspectorDTO.class);
    }

    @Override
    public void expel(Long id) {
        inspectorRepository.deleteById(id);
    }

    @Override
    public Optional<InspectorDTO> findById(Long id) {
        return inspectorRepository.findById(id).map(inspector -> modelMapper.map(inspector, InspectorDTO.class));
    }

    @Override
    public List<InspectorDTO> getAll() {
        return inspectorRepository.findAll().stream()
                .map(inspector -> modelMapper.map(inspector, InspectorDTO.class))
                .collect(Collectors.toList());
    }
}
