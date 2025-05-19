package com.capgemini.job_application.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.job_application.dtos.ApplicationInfoDto;
import com.capgemini.job_application.dtos.ApplicationViewDto;
import com.capgemini.job_application.entities.Application;
import com.capgemini.job_application.entities.User;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findByUser(User user);
	
	@Query(value = "SELECT * FROM application a WHERE a.user_id = ?1", nativeQuery = true)
	List<Application> findUserUserId(Long userId);
	@Query(value = "SELECT a.application_id AS applicationId, a.status AS status, a.applied_date AS appliedDate, j.job_id AS jobId, j.job_title AS jobTitle, c.company_name AS companyName, j.job_location AS jobLocation, j.salary AS salary FROM application a JOIN job j ON a.job_id = j.job_id JOIN company c ON j.company_id = c.company_id WHERE a.user_id = ?1", nativeQuery = true)
	List<ApplicationViewDto> findApplicationsByUserId(Long userId);
	
	@Query(value = "SELECT a.* FROM application a JOIN job j ON a.job_id = j.job_id WHERE j.company_id = ?1", nativeQuery = true)
	List<Application> findByCompanyId(Long companyId);
	
	@Query(value = "SELECT a.job_id, COUNT(a.job_id) FROM application a JOIN job j ON a.job_id = j.job_id WHERE j.company_id = ?1 GROUP BY a.job_id", nativeQuery = true)
	List<Object[]> findApplicationByJob(Long companyId);
	
	@Query(value = "SELECT u.gender, COUNT(*) FROM application a JOIN job j ON a.job_id = j.job_id JOIN user u ON a.user_id = u.user_id WHERE j.company_id = ?1 GROUP BY u.gender", nativeQuery = true)
	List<Object[]> getGenderCounts(Long companyId);

	@Query("SELECT a.job.jobLocation, COUNT(a) FROM Application a WHERE a.user.userId = :userId GROUP BY a.job.jobLocation")
	List<Object[]> countApplicationsByLocationForUser(Long userId);

	@Query("SELECT a.job.jobTitle, COUNT(a) FROM Application a WHERE a.user.userId = :userId GROUP BY a.job.jobTitle")
	List<Object[]> countApplicationsByJobTitleForUser(Long userId);

	@Query("SELECT new com.capgemini.job_application.dtos.ApplicationInfoDto(a.applicationId, u.userId, j.jobId, j.jobTitle,a.appliedDate, a.status) " +
		       "FROM Application a JOIN a.user u JOIN a.job j")
		List<ApplicationInfoDto> getAllApplicationDetails();

}
