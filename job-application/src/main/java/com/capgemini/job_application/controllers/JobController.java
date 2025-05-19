package com.capgemini.job_application.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.job_application.entities.Job;
import com.capgemini.job_application.services.JobService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/jobs")
@Slf4j
public class JobController {

	private final JobService jobService;

	@Autowired
	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping
	public ResponseEntity<List<Job>> getAllJobs() {
		log.info("GET /api/jobs - Fetching all jobs");
		List<Job> jobs = jobService.getAllJobs();
		return ResponseEntity.status(HttpStatus.OK).body(jobs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Job> getJob(@PathVariable Long id) {
		log.info("GET /api/jobs/{} - Fetching job by ID", id);
		Job job = jobService.getJobById(id);
		return ResponseEntity.status(HttpStatus.OK).body(job);
	}

	@PostMapping
	public ResponseEntity<Job> createJob(@Valid @RequestBody Job job, BindingResult result) {
		log.info("POST /api/jobs - Creating new job");
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Job saved = jobService.createJob(job);
		return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/api/jobs/" + saved.getJobId()))
				.body(saved);
	}
	
	@GetMapping("/company/{companyId}")
	public ResponseEntity<List<Job>> getJobsByCompany(@PathVariable Long companyId) {
	    log.info("GET /api/jobs/company/{} - Fetching jobs by company ID", companyId);
	    List<Job> jobs = jobService.getJobsByCompanyId(companyId);
	    return ResponseEntity.ok(jobs);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Job> updateJob(@PathVariable Long id, @Valid @RequestBody Job newJob, BindingResult result) {
		log.info("PUT /api/jobs/{} - Updating job", id);
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Job updated = jobService.updateJob(id, newJob);
		return ResponseEntity.status(HttpStatus.OK).body(updated);
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
		log.info("DELETE /api/jobs/{} - Deleting job", id);
		jobService.deleteJob(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	 @GetMapping("/top-salary")
	    public List<Job> getTop5JobsBySalary() {
	        return jobService.getTop5JobsBySalary();
	    }
}
