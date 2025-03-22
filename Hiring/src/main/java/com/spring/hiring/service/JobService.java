package com.spring.hiring.service;

import com.spring.hiring.common.exception.JobNotFoundException;
import com.spring.hiring.entity.Job;
import com.spring.hiring.repository.JobRepository;
import com.spring.hiring.utils.JobStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job addJob(Job job) {
        return jobRepository.save(job);
    }
    public Job getJob(long id) {
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
        existingJob.setStatus(JobStatus.OPEN);
        existingJob.setRequirements(job.getRequirements());
        existingJob.setLocation(job.getLocation());
        existingJob.setTitle(job.getTitle());
        existingJob.setDescription(job.getDescription());
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

}
