package com.iamruda.contractfirst.dtos;

import java.util.Set;

public class CargoDTO extends BaseDTO{
    private Double weight;
    private Double volume;
    private Set<String> items;
    private Long truckId;
    private Boolean checked;

    public CargoDTO() {}

    public CargoDTO(Long id, Double weight, Double volume, Set<String> items, Long truckId, Boolean checked) {
        this.id = id;
        this.weight = weight;
        this.volume = volume;
        this.items = items;
        this.truckId = truckId;
        this.checked = checked;
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

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "CargoDTO{" +
                "id=" + id +
                ", weight=" + weight +
                ", volume=" + volume +
                ", items=" + items +
                ", truckId=" + truckId +
                ", checked=" + checked +
                '}';
    }
}
