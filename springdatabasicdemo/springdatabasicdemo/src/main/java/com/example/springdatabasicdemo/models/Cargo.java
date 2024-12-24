package com.example.springdatabasicdemo.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "volume", nullable = false)
    private Double volume;

    @Column(name = "checked", nullable = false)
    private boolean checked;

    @ElementCollection
    @Column(name = "items", nullable = false)
    private Set<String> items = new HashSet<>();

    // Many-to-one relationship with Truck
    @ManyToOne
    @JoinColumn(name = "truck_id", nullable = false)
    private Truck truck;

    public Cargo() {}

    public Cargo(Double weight, Double volume, Set<String> items, Truck truck, Boolean checked) {
        this.weight = weight;
        this.volume = volume;
        this.checked = checked;
        this.items = items;
        this.truck = truck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Set<String> getItems() {
        return items;
    }

    public void setItems(Set<String> items) {
        this.items = items;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
