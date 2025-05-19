package com.capgemini.job_application.repositories;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.job_application.dtos.JobDto;
import com.capgemini.job_application.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserEmail(String email);

	Optional<User> findByUserNameOrUserEmail(String name, String email);

	Optional<User> findByUserName(String name);

	boolean existsByUserName(String name);

	boolean existsByUserEmail(String email);
	
	@Query(value = "SELECT j.job_id AS jobId, j.job_title AS jobTitle, c.company_name AS companyName, j.job_location AS jobLocation, j.salary AS salary FROM job j JOIN company c ON j.company_id = c.company_id WHERE j.job_id NOT IN (SELECT job_id FROM application WHERE user_id = ?1)", nativeQuery = true)
	List<JobDto> findJobsToApply( Long userId);


}
