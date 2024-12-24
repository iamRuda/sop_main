package com.example.springdatabasicdemo.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "truck")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "year_of_manufacture", nullable = false)
    private Integer yearOfManufacture;

    @OneToMany(mappedBy = "truck", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Cargo> cargos = new HashSet<>();

    @Column(name = "checked")
    private Boolean checked;

    public Truck() {}

    public Truck(String brand, String model, String licensePlate, Integer yearOfManufacture, Boolean checked) {
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.yearOfManufacture = yearOfManufacture;
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

    public Integer getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Integer yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public Set<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(Set<Cargo> cargos) {
        this.cargos = cargos;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
