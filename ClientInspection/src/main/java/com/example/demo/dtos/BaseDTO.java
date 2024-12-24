package com.example.demo.dtos;

public class BaseDTO {
    protected Long id;

    public BaseDTO(Long id) {
        this.id = id;
    }

    public BaseDTO() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
