package com.spring.hiring.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User Id is required")
    private int userId;

    @NotBlank(message = "Upload CV is required!")
    private String  cv;
    private LocalDateTime submittedAt =LocalDateTime.now();
    private String notes;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

}
