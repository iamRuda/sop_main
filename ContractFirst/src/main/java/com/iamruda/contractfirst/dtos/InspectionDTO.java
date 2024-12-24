package com.iamruda.contractfirst.dtos;

import java.sql.Date;
import java.time.LocalDate;

public class InspectionDTO extends BaseDTO {
    private Long inspectorId;
    private Long inspectedPersonId;
    private Long truckId;
    private LocalDate inspectionDate;
    private String status;
    private Boolean access;
    public InspectionDTO() {}

    public InspectionDTO(Long id, InspectorDTO inspector, InspectedPersonDTO inspectedPerson, TruckDTO truck, Date inspectionDate, String status, Boolean access) {
        this.id = id;
        this.inspectorId = inspector != null ? inspector.getId() : null;
        this.inspectedPersonId = inspectedPerson != null ? inspectedPerson.getId() : null;
        this.truckId = truck != null ? truck.getId() : null; // Default value or handle as needed
        this.inspectionDate = (inspectionDate != null) ? inspectionDate.toLocalDate() : null;
        this.status = status;
        this.access = access;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Long inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Long getInspectedPersonId() {
        return inspectedPersonId;
    }

    public void setInspectedPersonId(Long inspectedPersonId) {
        this.inspectedPersonId = inspectedPersonId;
    }

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
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
