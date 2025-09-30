package com.jobportal.repository;

import com.jobportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    
    // Custom query methods
    List<Job> findByLocationContainingIgnoreCase(String location);
    List<Job> findByCompanyNameContainingIgnoreCase(String companyName);
    List<Job> findByTitleContainingIgnoreCase(String title);
    
    @Query("SELECT j FROM Job j WHERE j.salary >= ?1")
    List<Job> findJobsWithMinimumSalary(Double minSalary);
    
    List<Job> findByJobTypeAndExperience(String jobType, String experience);
}