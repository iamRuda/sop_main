package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.models.InspectedPerson;
import com.example.springdatabasicdemo.repositories.InspectedPersonRepository;
import com.example.springdatabasicdemo.services.InspectedPersonService;
import com.iamruda.contractfirst.dtos.InspectedPersonDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InspectedPersonServiceImpl implements InspectedPersonService {

    @Autowired
    private InspectedPersonRepository inspectedPersonRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InspectedPersonDTO register(InspectedPersonDTO inspectedPersonDTO) {
        InspectedPerson inspectedPerson = modelMapper.map(inspectedPersonDTO, InspectedPerson.class);
        return modelMapper.map(inspectedPersonRepository.save(inspectedPerson), InspectedPersonDTO.class);
    }

    @Override
    public void expel(Long id) {
        inspectedPersonRepository.deleteById(id);
    }

    @Override
    public Optional<InspectedPersonDTO> findById(Long id) {
        return inspectedPersonRepository.findById(id).map(person -> modelMapper.map(person, InspectedPersonDTO.class));
    }

    @Override
    public List<InspectedPersonDTO> getAll() {
        return inspectedPersonRepository.findAll().stream()
                .map(person -> modelMapper.map(person, InspectedPersonDTO.class))
                .collect(Collectors.toList());
    }
}
