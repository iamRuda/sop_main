package com.example.springdatabasicdemo.controllers.cargo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CargoNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CargoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inspectionNotFoundHandler(CargoNotFoundException ex) {
        return ex.getMessage();
    }
}
