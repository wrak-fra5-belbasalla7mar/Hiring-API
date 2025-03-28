package com.spring.hiring.controller;


import com.spring.hiring.entity.Job;
import com.spring.hiring.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private  final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable long id) {
        return ResponseEntity.ok(jobService.getJob(id));
    }
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobRequests() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PostMapping
    public ResponseEntity<Job> addJob(@RequestBody Job job, @RequestParam int createdBy) {
        return ResponseEntity.ok(jobService.addJob(job, createdBy));
    }

    @PutMapping
    public ResponseEntity<Job> updateJob(@RequestBody Job job) {
        return ResponseEntity.ok(jobService.updateJob(job));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable long id) {
        return ResponseEntity.ok(jobService.deleteJob(id));
    }
    @PutMapping("/open/{id}")
    public ResponseEntity<String> openJob(@PathVariable long id) {
        return ResponseEntity.ok(jobService.openJob(id));
    }
    @PutMapping("/close/{id}")
    public ResponseEntity<String> closeJob(@PathVariable long id) {
        return ResponseEntity.ok(jobService.closeJob(id));
    }
}
