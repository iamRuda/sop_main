package com.example.springdatabasicdemo.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class InspectedPersonDTO extends BaseDTO{
    @NotBlank(message = "ФИО обязателен")
    private String name;
    @NotBlank(message = "Номер паспорта обязателен")
    private String passportNumber;

    private Boolean checked;

    public InspectedPersonDTO() {}

    public InspectedPersonDTO(Long id, String name, String passportNumber, Boolean checked) {
        this.id = id;
        this.name = name;
        this.passportNumber = passportNumber;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Boolean getChecked() { return checked; }

    public void setChecked(Boolean checked) { this.checked = checked; }

}
