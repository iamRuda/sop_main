package com.example.springdatabasicdemo.controllers.inspector;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InspectorNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InspectorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String inspectionNotFoundHandler(InspectorNotFoundException ex) {
        return ex.getMessage();
    }
}
