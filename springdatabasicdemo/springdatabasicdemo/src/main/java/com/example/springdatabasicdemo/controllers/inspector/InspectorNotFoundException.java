package com.example.springdatabasicdemo.controllers.inspector;

class InspectorNotFoundException extends RuntimeException {
    InspectorNotFoundException(Long id) {
        super("Could not find inspection " + id);
    }
}
