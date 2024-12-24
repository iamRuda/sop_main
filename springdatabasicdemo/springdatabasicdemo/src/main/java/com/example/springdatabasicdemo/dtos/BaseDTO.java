package com.example.springdatabasicdemo.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.HashMap;
import java.util.Map;

public class BaseDTO extends RepresentationModel<BaseDTO> {
    protected Long id;

    public BaseDTO(Long id) {
        this.id = id;
    }

    public BaseDTO() {
    }

    private final Map<String, Action> actions = new HashMap<>();

    public Map<String, Action> getActions() {
        return actions;
    }
    public void addAction(String rel, Action action) {
        this.actions.put(rel, action);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
