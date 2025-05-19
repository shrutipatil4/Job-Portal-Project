package com.capgemini.job_application.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.capgemini.job_application.entities.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
	
	
	@Query(value="SELECT * FROM experience WHERE user_id = ?1 ", nativeQuery= true)
	List<Experience> findByUserId(Long userId);

	@Query(value= "SELECT * FROM experience WHERE user_id = ?1 ", nativeQuery=true)
    void deleteByUserId(Long userId);
}
