package com.example.springdatabasicdemo.controllers.inspections;

class InspectionNotFoundException extends RuntimeException {
    InspectionNotFoundException(Long id) {
        super("Could not find inspection " + id);
    }
}
