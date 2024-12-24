package com.example.demo.dtos;



public class InspectionDTO extends BaseDTO {
    private String status;
    private Boolean access;
    public InspectionDTO() {}

    public InspectionDTO(Long id, String status, Boolean access) {
        this.id = id;
        this.status = status;
        this.access = access;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
