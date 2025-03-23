package com.spring.hiring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.hiring.utils.JobLocation;
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
    private String title;

    @NotBlank(message = "Job description is mandatory")
    private String description;

    @NotBlank(message = "Location is mandatory")
    private JobLocation location;

    @NotBlank(message = "Requirements are mandatory")
    private String Requirements;

    @JsonIgnore
    private JobStatus status=JobStatus.OPEN;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private List<JobApplication>applications;

    public Job() {
    }


    public Job(String department, String title, String description, JobLocation location, String requirements, List<JobApplication> applications) {
        this.department = department;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobLocation getLocation() {
        return location;
    }

    public void setLocation(JobLocation location) {
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
