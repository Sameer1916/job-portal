package com.jobportal.service;

import com.jobportal.model.Job;
import com.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }
    
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }
    
    public Job updateJob(Long id, Job jobDetails) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
        
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setCompanyName(jobDetails.getCompanyName());
        job.setLocation(jobDetails.getLocation());
        job.setSalary(jobDetails.getSalary());
        job.setJobType(jobDetails.getJobType());
        job.setExperience(jobDetails.getExperience());
        
        return jobRepository.save(job);
    }
    
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
    
    public List<Job> searchJobsByLocation(String location) {
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }
    
    public List<Job> searchJobsByCompany(String companyName) {
        return jobRepository.findByCompanyNameContainingIgnoreCase(companyName);
    }
    
    public List<Job> searchJobsWithMinimumSalary(Double minSalary) {
        return jobRepository.findJobsWithMinimumSalary(minSalary);
    }
}