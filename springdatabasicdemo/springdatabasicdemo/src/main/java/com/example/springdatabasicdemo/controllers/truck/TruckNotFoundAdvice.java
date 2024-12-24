package com.example.springdatabasicdemo.controllers.truck;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TruckNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TruckNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inspectionNotFoundHandler(TruckNotFoundException ex) {
        return ex.getMessage();
    }
}
