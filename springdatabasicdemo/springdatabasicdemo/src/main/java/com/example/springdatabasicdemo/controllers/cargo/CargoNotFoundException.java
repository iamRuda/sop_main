package com.example.springdatabasicdemo.controllers.cargo;

class CargoNotFoundException extends RuntimeException {
    CargoNotFoundException(Long id) {
        super("Could not find cargo " + id);
    }
}
