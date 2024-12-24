package com.iamruda.contractfirst.exceptions;

public class CargoNotFoundException extends RuntimeException {
    public CargoNotFoundException(Long id) {
        super("Cargo with ID " + id + " not found.");
    }
}