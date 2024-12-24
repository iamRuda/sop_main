package com.iamruda.contractfirst.handlers;

import com.iamruda.contractfirst.exceptions.CargoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CargoExceptionHandler {

    @ExceptionHandler(CargoNotFoundException.class)
    public ResponseEntity<String> handleTruckNotFound(CargoNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
