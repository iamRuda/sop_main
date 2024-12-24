package com.example.springdatabasicdemo.controllers.inspectedPerson;

class InspectedNotFoundException extends RuntimeException {
    InspectedNotFoundException(Long id) {
        super("Could not find inspection " + id);
    }
}
