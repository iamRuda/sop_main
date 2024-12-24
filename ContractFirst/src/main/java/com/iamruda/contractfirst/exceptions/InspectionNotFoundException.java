package com.iamruda.contractfirst.exceptions;

public class InspectionNotFoundException extends RuntimeException {
    public InspectionNotFoundException(Long id) {
        super("Inspection with ID " + id + " not found.");
    }
}