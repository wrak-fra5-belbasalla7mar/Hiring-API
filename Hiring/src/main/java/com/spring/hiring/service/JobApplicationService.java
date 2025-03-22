package com.spring.hiring.service;

import com.spring.hiring.common.exception.JobApplicationNotFoundException;
import com.spring.hiring.entity.Job;
import com.spring.hiring.entity.JobApplication;
import com.spring.hiring.repository.JobApplicationRepository;
import com.spring.hiring.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final String uploadDir;
    private final JobRepository jobRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository,
                                 @Value("${file.upload-dir}") String uploadDir,
                                 JobRepository jobRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.uploadDir = uploadDir;
        this.jobRepository = jobRepository;
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
        if (cvFile.isEmpty()) {
            throw new IllegalArgumentException("CV file is required");
        }
        if (!cvFile.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("CV file must be a PDF");
        }
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = cvFile.getOriginalFilename();
        File file = new File(uploadDir + File.separator + fileName);
        cvFile.transferTo(file);

        JobApplication jobApplication = new JobApplication();
        jobApplication.setUserId(userId);
        jobApplication.setJob(jobRepository.findById(jobId).orElseThrow(
                () -> new JobApplicationNotFoundException("Invalid job with Id: " + jobId)));
        jobApplication.setCv(file.getAbsolutePath());

        return jobApplicationRepository.save(jobApplication);
    }

    public String deleteJobApplication(long id) {
        JobApplication application = getJobApplication(id);
        String cvPath = application.getCv();

        File cvFile = new File(cvPath);
        if (cvFile.exists()) {
            cvFile.delete();
        }

        jobApplicationRepository.deleteById(id);
        return "Job application with Id: " + id + " deleted successfully";
    }

    public JobApplication update(JobApplication jobApplication) {
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