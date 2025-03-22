package com.spring.hiring.service;

import com.spring.hiring.common.exception.JobApplicationNotFoundException;
import com.spring.hiring.entity.Job;
import com.spring.hiring.entity.JobApplication;
import com.spring.hiring.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public JobApplication getJobApplication(long id) {
        return jobApplicationRepository.findById(id).orElseThrow(
                () -> new JobApplicationNotFoundException("Invalid job application with Id: " + id));
    }

    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    public List<JobApplication> getJobApplicationsByJobId(long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }

    public JobApplication addJobApplication(Long jobId, Long userId, MultipartFile cvFile) throws IOException {
        // Validate CV file
        if (cvFile.isEmpty()) {
            throw new IllegalArgumentException("CV file is required");
        }
        if (!cvFile.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("CV file must be a PDF");
        }
        if (cvFile.getSize() > 5 * 1024 * 1024) { // 5MB limit
            throw new IllegalArgumentException("CV file size must not exceed 5MB");
        }

        // Save the CV file to the server
        String uploadDir = "uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + cvFile.getOriginalFilename();
        File file = new File(uploadDir + fileName);
        cvFile.transferTo(file);

        // Create JobApplication
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUserId(userId);
        jobApplication.setJob(new Job());
        jobApplication.setCv(file.getAbsolutePath());

        return jobApplicationRepository.save(jobApplication);
    }

    public String deleteJobApplication(long id) {
        JobApplication application = getJobApplication(id);
        String cvPath = application.getCv();
        // Delete the CV file from the server
        File cvFile = new File(cvPath);
        if (cvFile.exists()) {
            cvFile.delete();
        }

        jobApplicationRepository.deleteById(id);
        return "Job application with Id: " + id + " deleted successfully";
    }

    public JobApplication updateJobApplication(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    public File getCvFile(long applicationId) {
        JobApplication application = getJobApplication(applicationId);
        File cvFile = new File(application.getCv());
        if (!cvFile.exists()) {
            throw new JobApplicationNotFoundException("CV file for application with Id: " + applicationId + " not found");
        }
        return cvFile;
    }
}