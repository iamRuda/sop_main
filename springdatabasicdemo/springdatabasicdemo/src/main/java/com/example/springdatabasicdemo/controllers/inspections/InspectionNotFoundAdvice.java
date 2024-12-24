package com.example.springdatabasicdemo.controllers.inspections;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InspectionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InspectionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inspectionNotFoundHandler(InspectionNotFoundException ex) {
        return ex.getMessage();
    }
}
