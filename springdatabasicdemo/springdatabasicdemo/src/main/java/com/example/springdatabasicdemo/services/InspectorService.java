package com.example.springdatabasicdemo.services;

import com.iamruda.contractfirst.dtos.InspectorDTO;

import java.util.List;
import java.util.Optional;

public interface InspectorService {

    InspectorDTO register(InspectorDTO inspector);

    void expel(Long id);

    Optional<InspectorDTO> findById(Long id);

    List<InspectorDTO> getAll();
}
