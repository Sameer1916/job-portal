package com.jobportal.controller;

import com.jobportal.model.Job;
import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*") // Allow all origins for development
@Validated
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    // GET all jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }
    
    // GET job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new job
    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job) {
        Job savedJob = jobService.saveJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }
    
    // PUT update job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, 
                                       @Valid @RequestBody Job jobDetails) {
        try {
            Job updatedJob = jobService.updateJob(id, jobDetails);
            return ResponseEntity.ok(updatedJob);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET jobs by location
    @GetMapping("/search/location/{location}")
    public ResponseEntity<List<Job>> getJobsByLocation(@PathVariable String location) {
        List<Job> jobs = jobService.searchJobsByLocation(location);
        return ResponseEntity.ok(jobs);
    }
    
    // GET jobs by company
    @GetMapping("/search/company/{companyName}")
    public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable String companyName) {
        List<Job> jobs = jobService.searchJobsByCompany(companyName);
        return ResponseEntity.ok(jobs);
    }
    
    // GET jobs with minimum salary
    @GetMapping("/search/salary/{minSalary}")
    public ResponseEntity<List<Job>> getJobsWithMinimumSalary(@PathVariable Double minSalary) {
        List<Job> jobs = jobService.searchJobsWithMinimumSalary(minSalary);
        return ResponseEntity.ok(jobs);
    }
}