package com.spring.hiring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.hiring.utils.JobStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


import java.util.List;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Department is mandatory")
    private String department;

    @NotBlank(message = "Job title is mandatory")
    private String tittle;

    @NotBlank(message = "Job description is mandatory")
    private String description;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotBlank(message = "Requirements are mandatory")
    private String Requirements;

    @JsonIgnore
    private JobStatus status=JobStatus.OPEN;

    @OneToMany(mappedBy = "job")
    private List<JobApplication>applications;

    public Job() {
    }

    public Job(String department, String tittle, String description, String location, String requirements, List<JobApplication> applications) {
        this.department = department;
        this.tittle = tittle;
        this.description = description;
        this.location = location;
        Requirements = requirements;
        this.applications = applications;
    }

    public List<JobApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<JobApplication> applications) {
        this.applications = applications;
    }

    public long getId() {
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequirements() {
        return Requirements;
    }

    public void setRequirements(String requirements) {
        Requirements = requirements;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
