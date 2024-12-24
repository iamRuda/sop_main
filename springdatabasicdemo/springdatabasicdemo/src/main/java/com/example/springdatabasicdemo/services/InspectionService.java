package com.example.springdatabasicdemo.services;

import com.iamruda.contractfirst.dtos.InspectionDTO;

import java.util.List;
import java.util.Optional;

public interface InspectionService {

    InspectionDTO register(InspectionDTO inspection);

    void expel(Long id);

    Optional<InspectionDTO> findById(Long id);

    List<InspectionDTO> getAll();
}
