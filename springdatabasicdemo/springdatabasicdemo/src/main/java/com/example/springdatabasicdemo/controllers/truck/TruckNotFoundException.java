package com.example.springdatabasicdemo.controllers.truck;

class TruckNotFoundException extends RuntimeException {
    TruckNotFoundException(Long id) {
        super("Could not find inspection " + id);
    }
}
