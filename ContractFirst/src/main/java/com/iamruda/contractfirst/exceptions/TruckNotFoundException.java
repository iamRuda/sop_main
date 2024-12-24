package com.iamruda.contractfirst.exceptions;

public class TruckNotFoundException extends RuntimeException {
    public TruckNotFoundException(Long id) {
        super("Truck with ID " + id + " not found");
    }
}