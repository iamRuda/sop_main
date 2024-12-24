package com.example.springdatabasicdemo.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inspected_person")
public class InspectedPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "checked")
    private Boolean checked;

    @OneToMany(mappedBy = "inspectedPerson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Inspection> inspections = new HashSet<>();

    public InspectedPerson() {}

    public InspectedPerson(String name, String passportNumber, Boolean checked) {
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

    public Set<Inspection> getInspections() {
        return inspections;
    }

    public void setInspections(Set<Inspection> inspections) {
        this.inspections = inspections;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
