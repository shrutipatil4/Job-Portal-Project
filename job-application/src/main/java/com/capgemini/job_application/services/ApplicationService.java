package com.capgemini.job_application.services;

import java.util.List;

import com.capgemini.job_application.dtos.ApplicationInfoDto;
import com.capgemini.job_application.dtos.ApplicationViewDto;
import com.capgemini.job_application.entities.Application;


public interface ApplicationService {
	List<Application> getAllApplication();

	Application getApplicationById(Long id);

	Application createApplication(Application applicant);

	Application updateApplication(Long id, Application applicant);

	Application patchApplication(Long id, Application applicant);

	void deleteApplication(Long id);
	
	List<Application> findUserUserId(Long userId);
	
	List<ApplicationViewDto> findApplicationsByUserId(Long userId);
	
	List<ApplicationInfoDto> getAllApplicationInfo();
}
