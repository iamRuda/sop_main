package com.example.springdatabasicdemo.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class TruckDTO extends BaseDTO {
    @NotBlank(message = "Бренд обязательно")
    private String brand;
    @NotBlank(message = "Бренд обязательно")
    private String model;
    @NotBlank(message = "Номер лицензии обязательна")
    private String licensePlate;
    @NotBlank(message = "Год выпуска обезателен")
    private int yearOfManufacture;
    private Set<Long> cargoIds;
    private Boolean checked;

    public TruckDTO() {}

    public TruckDTO(Long id, String brand, String model, String licensePlate, int yearOfManufacture, Set<Long> cargoIds, Boolean checked) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.yearOfManufacture = yearOfManufacture;
        this.cargoIds = cargoIds;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public Set<Long> getCargoIds() {
        return cargoIds;
    }

    public void setCargoIds(Set<Long> cargoIds) {
        this.cargoIds = cargoIds;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
