package com.example.springdatabasicdemo.controllers.inspectedPerson;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InspectedNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InspectedNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inspectionNotFoundHandler(InspectedNotFoundException ex) {
        return ex.getMessage();
    }
}
