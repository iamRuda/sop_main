package com.iamruda.contractfirst.handlers;

import com.iamruda.contractfirst.exceptions.InspectionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InspectionExceptionHandler {

    @ExceptionHandler(InspectionNotFoundException.class)
    public ResponseEntity<String> handleTruckNotFound(InspectionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
