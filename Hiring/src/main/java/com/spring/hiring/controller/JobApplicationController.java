package com.spring.hiring.controller;

import com.spring.hiring.entity.JobApplication;
import com.spring.hiring.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getJobApplication(@PathVariable long id) {
        return ResponseEntity.ok(jobApplicationService.getJobApplication(id));
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<List<JobApplication>> getJobApplicationsByJobId(@PathVariable long id) {
        return ResponseEntity.ok(jobApplicationService.getJobApplicationsByJobId(id));
    }

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobApplications() {
        return ResponseEntity.ok(jobApplicationService.getAllJobApplications());
    }

    @PostMapping
    public ResponseEntity<JobApplication> addJobApplication(
            @RequestParam Long jobId,
            @RequestParam Long userId,
            @RequestParam("cv_file") MultipartFile cvFile) throws IOException {
        return ResponseEntity.ok(jobApplicationService.addJobApplication(jobId, userId, cvFile));
    }

    @PutMapping
    public ResponseEntity<JobApplication> updateJobApplication(@Valid @RequestBody JobApplication jobApplication) {
        return ResponseEntity.ok(jobApplicationService.updateJobApplication(jobApplication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobApplication(@PathVariable long id) {
        return ResponseEntity.ok(jobApplicationService.deleteJobApplication(id));
    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<Resource> downloadCV(@PathVariable long id) {
        File file = jobApplicationService.getCvFile(id);
        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}