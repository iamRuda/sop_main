package com.example.springdatabasicdemo.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inspection")
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector_id")
    private Inspector inspector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspected_person_id")
    private InspectedPerson inspectedPerson;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @Column(name = "inspection_date", nullable = false)
    private LocalDate inspectionDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "access")
    private Boolean access;

    public Inspection() {}

    public Inspection(Inspector inspector, InspectedPerson inspectedPerson, Truck truck, LocalDate inspectionDate, String status, Boolean access) {
        this.inspector = inspector;
        this.inspectedPerson = inspectedPerson;
        this.truck = truck;
        this.inspectionDate = inspectionDate;
        this.status = status;
        this.access = access;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inspector getInspector() {
        return inspector;
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
    }

    public InspectedPerson getInspectedPerson() {
        return inspectedPerson;
    }

    public void setInspectedPerson(InspectedPerson inspectedPerson) {
        this.inspectedPerson = inspectedPerson;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }
}
