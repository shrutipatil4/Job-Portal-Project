package com.capgemini.job_application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.job_application.entities.Qualification;

public interface QualificationRepository extends JpaRepository<Qualification, Long>{
	@Query(value="SELECT * FROM qualification q WHERE q.user_id = ?1 ", nativeQuery= true)
	List<Qualification> findByUserId(Long userId);
	
	@Query(value= "SELECT * FROM qualification q WHERE q.user_id = ?1 ", nativeQuery=true)
	void deleteByUserId(Long userId);

}
