package com.spring.hiring.service;

import com.spring.hiring.client.UserClient;
import com.spring.hiring.dto.UserDTO;
import com.spring.hiring.exception.JobNotFoundException;
import com.spring.hiring.entity.Job;
import com.spring.hiring.repository.JobRepository;
import com.spring.hiring.utils.JobStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
public class JobService {

    private final JobRepository jobRepository;
    private final UserClient userClient;


    public JobService(JobRepository jobRepository, UserClient userClient) {
        this.jobRepository = jobRepository;
        this.userClient = userClient;
    }

    public Job addJob(Job job, int createdBy) {
        UserDTO user = userClient.getUserById(createdBy).block();
        if (user == null) {
            throw new IllegalStateException(" user with id " + createdBy + " does not exist");
        }
        if (!Objects.equals(user.getDepartment().getName(), "HR")) {
            throw new IllegalArgumentException("Only users in the HR department can post jobs");
        }
            job.setCreatedBy(createdBy);
            job.setCreatedAt(LocalDateTime.now());
            job.setStatus(JobStatus.OPEN);
            job.setLocation(job.getLocation());
            job.setDepartment(job.getDepartment());
            return jobRepository.save(job);
    }

    public Job getJobDetails(long id) {
        return jobRepository.findById(id).orElseThrow(
                () -> new JobNotFoundException("Job with id " + id + " does not exist")
        );
    }

    public List<Job> getAllJobs() {

        return jobRepository.findAll();
    }

    public String deleteJob(Long id) {
        jobRepository.deleteById(id);
        return "Job with id " + id + " has been deleted";
    }

    public Job updateJob(Job job) {

        Job existingJob = jobRepository.findById(job.getId()).orElseThrow(
                () -> new JobNotFoundException("Job with id " + job.getId() + " does not exist"));
        existingJob.setTitle(job.getTitle());
        existingJob.setDescription(job.getDescription());
        existingJob.setRequirements(job.getRequirements());
        existingJob.setLocation(job.getLocation());
        existingJob.setDepartment(job.getDepartment());
        existingJob.setStatus(job.getStatus());
        return jobRepository.save(existingJob);
    }

    public String openJob(long id) {
        Job job = jobRepository.findById(id).orElseThrow(
                () -> new JobNotFoundException("Job with id " + id + " does not exist"));
        job.setStatus(JobStatus.OPEN);
        jobRepository.save(job);
        return "Job with id " + id + " has been opened";
    }

    public String closeJob(long id) {
        Job job = jobRepository.findById(id).orElseThrow(
                () -> new JobNotFoundException("Job with id " + id + " does not exist"));
        job.setStatus(JobStatus.CLOSED);
        jobRepository.save(job);
        return "Job with id " + id + " has been closed";
    }

    public List<Job> findOpenJobs() {
        return jobRepository.findByOpenStatus();

    }
}