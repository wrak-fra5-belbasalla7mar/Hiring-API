package com.spring.hiring.repository;

import com.spring.hiring.entity.Job;
import com.spring.hiring.utils.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {

    List<Job> findByStatus(JobStatus status);

    default List<Job> findByOpenStatus() {
        return findByStatus(JobStatus.OPEN);
    }
}
