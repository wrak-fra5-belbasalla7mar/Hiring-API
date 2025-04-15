package com.spring.hiring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.hiring.utils.Location;
import com.spring.hiring.utils.JobStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @OneToMany(mappedBy = "job",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<JobApplication>applications;

}
