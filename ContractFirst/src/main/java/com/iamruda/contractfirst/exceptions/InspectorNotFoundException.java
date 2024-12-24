package com.iamruda.contractfirst.exceptions;

public class InspectorNotFoundException extends RuntimeException {
    public InspectorNotFoundException(Long id) {
        super("Inspector with ID " + id + " not found.");
    }
}