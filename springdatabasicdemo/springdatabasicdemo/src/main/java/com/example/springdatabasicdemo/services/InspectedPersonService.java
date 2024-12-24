package com.example.springdatabasicdemo.services;

import com.iamruda.contractfirst.dtos.InspectedPersonDTO;

import java.util.List;
import java.util.Optional;

public interface InspectedPersonService {

    InspectedPersonDTO register(InspectedPersonDTO inspectedPerson);

    void expel(Long id);

    Optional<InspectedPersonDTO> findById(Long id);

    List<InspectedPersonDTO> getAll();
}
