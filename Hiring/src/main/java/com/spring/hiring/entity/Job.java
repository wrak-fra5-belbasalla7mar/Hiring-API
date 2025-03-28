package com.spring.hiring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.hiring.utils.Location;
import com.spring.hiring.utils.JobStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private int createdBy;

    @NotBlank(message = "Job title is mandatory")
    private String title;

    @NotBlank(message = "Department is mandatory")
    private String department;

    @NotBlank(message = "Job description is mandatory")
    private String description;

    @NotNull(message = "Location is mandatory")
    private Location location= Location.REMOTE;

    @NotBlank(message = "Requirements are mandatory")
    private String Requirements;

    @JsonIgnore
    private JobStatus status=JobStatus.OPEN;

    private LocalDateTime createdAt =LocalDateTime.now();
    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private List<JobApplication>applications;

    public Job() {
    }

    public Job(int createdBy, String title, String department, String description, Location location, String requirements, LocalDateTime createdAt) {
        this.createdBy = createdBy;
        this.title = title;
        this.department = department;
        this.description = description;
        this.location = location;
        Requirements = requirements;
        this.createdAt = createdAt;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
