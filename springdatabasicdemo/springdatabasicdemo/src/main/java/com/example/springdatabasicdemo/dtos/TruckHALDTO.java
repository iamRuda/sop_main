package com.example.springdatabasicdemo.dtos;

import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Set;

public class TruckHALDTO extends BaseDTO {
    private String brand;
    private String model;
    private String licensePlate;
    private int yearOfManufacture;
    private Set<Long> cargoIds;
    private Boolean checked;
    private List<Link> _links;

    public TruckHALDTO() {}

    public TruckHALDTO(Long id, String brand, String model, String licensePlate, int yearOfManufacture, Set<Long> cargoIds, Boolean checked) {
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

    public List<Link> get_links() {
        return _links;
    }

    public void set_links(List<Link> _links) {
        this._links = _links;
    }
}
