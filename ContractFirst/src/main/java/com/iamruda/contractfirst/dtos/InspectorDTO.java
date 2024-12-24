package com.iamruda.contractfirst.dtos;

import java.util.Set;

public class InspectorDTO extends BaseDTO {
    private String name;
    private String employeeNumber;
    private Set<Long> inspectionIds;

    public InspectorDTO() {}

    public InspectorDTO(Long id, String name, String employeeNumber) {
        this.id = id;
        this.name = name;
        this.employeeNumber = employeeNumber;
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

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Set<Long> getInspectionIds() {
        return inspectionIds;
    }

    public void setInspectionIds(Set<Long> inspectionIds) {
        this.inspectionIds = inspectionIds;
    }
}
