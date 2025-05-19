package com.capgemini.job_application.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.job_application.entities.Job;
import com.capgemini.job_application.exceptions.JobNotFoundException;
import com.capgemini.job_application.repositories.JobRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;

	@Autowired
	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
	
	@Override
	public List<Job> getJobsByCompanyId(Long companyId) {
	    return jobRepository.findByCompanyCompanyId(companyId);
	} 

	@Override
	public List<Job> getAllJobs() {
		log.info("Fetching all jobs");
		return jobRepository.findAll();
	}

	@Override
	public Job getJobById(Long id) {
		log.info("Fetching job with ID: {}", id);
		return jobRepository.findById(id)
				.orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
	}

	@Override
	public Job createJob(Job job) {
		log.info("Creating job: {}", job);
		job.setPostingDate(LocalDate.now());
		return jobRepository.save(job);
	}

	@Override
	public Job updateJob(Long id, Job updated) {
		log.info("Updating job with ID: {}", id);
		Job existing = jobRepository.findById(id)
				.orElseThrow(() -> new JobNotFoundException("Job not found with Id:" + id));
		existing.setCompany(updated.getCompany());
		existing.setJobTitle(updated.getJobTitle());
		existing.setDescription(updated.getDescription());
		existing.setSalary(updated.getSalary());
		existing.setJobLocation(updated.getJobLocation());
		existing.setDeadlineDate(updated.getDeadlineDate());
		return jobRepository.save(existing);
	}


	@Override
	public void deleteJob(Long id) {
		log.info("Deleting job with ID: {}", id);
		if (!jobRepository.existsById(id)) {
			throw new JobNotFoundException("Cannot Delete. Booking not found with ID:" + id);
		}
		jobRepository.deleteById(id);
	}

	@Override
	public List<Job> getTop5JobsBySalary() {
		 return jobRepository.findTop5ByOrderBySalaryDesc();
	}
}
