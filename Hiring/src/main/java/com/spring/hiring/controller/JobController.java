package com.spring.hiring.controller;


import com.spring.hiring.entity.Job;
import com.spring.hiring.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable long id) {
        return ResponseEntity.ok(jobService.getJobDetails(id));
    }

    @GetMapping("/open")
    public ResponseEntity<List<Job>> getOpenJobs() {
        return ResponseEntity.ok(jobService.findOpenJobs());
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobRequests() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<Job>> getJobsByCreator(@PathVariable Long userId) {
        return ResponseEntity.ok(jobService.findJobsByCreator(userId));
    }


    @PostMapping
    public ResponseEntity<Job> addJob(@RequestBody Job job, @RequestParam int createdBy) {
        job.setCreatedBy(createdBy);
        return ResponseEntity.ok(jobService.addJob(job, createdBy));
    }
    @PutMapping
    public ResponseEntity<Job> updateJob(@RequestBody Job job) {
        return ResponseEntity.ok(jobService.updateJob(job));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Job deleted successfully!");
        return ResponseEntity.ok(response);
    }
    @PutMapping("/open/{id}")
    public ResponseEntity<Map<String, String>> openJob(@PathVariable long id) {
        jobService.openJob(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Job opened successfully!");
        return ResponseEntity.ok(response);
    }
    @PutMapping("/close/{id}")
    public ResponseEntity<Map<String, String>> closeJob(@PathVariable long id) {
        jobService.closeJob(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Job closed successfully!");
        return ResponseEntity.ok(response);
    }
}








