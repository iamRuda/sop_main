package com.iamruda.contractfirst.handlers;

import com.iamruda.contractfirst.exceptions.InspectorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InspectorExceptionHandler {

    @ExceptionHandler(InspectorNotFoundException.class)
    public ResponseEntity<String> handleTruckNotFound(InspectorNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
