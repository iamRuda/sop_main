package com.iamruda.contractfirst.exceptions;

public class InspectedNotFoundException extends RuntimeException {
    public InspectedNotFoundException(Long id) {
        super("Inspected person with ID " + id + " not found");
    }
}
