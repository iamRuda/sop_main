package com.iamruda.contractfirst.handlers;

import com.iamruda.contractfirst.exceptions.InspectedNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InspectedExceptionHandler {

    @ExceptionHandler(InspectedNotFoundException.class)
    public ResponseEntity<String> handleInspectedPersonNotFound(InspectedNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
