package com.capgemini.job_application.services;

import java.util.List;


import com.capgemini.job_application.entities.Job;


public interface JobService {
	
	List<Job> getAllJobs();

	Job getJobById(Long id);

	Job createJob(Job job);

	Job updateJob(Long id, Job job);

	void deleteJob(Long id);
	
	List<Job> getJobsByCompanyId(Long companyId); 

	public List<Job> getTop5JobsBySalary();

}
