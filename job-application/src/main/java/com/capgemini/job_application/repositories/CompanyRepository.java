package com.capgemini.job_application.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.job_application.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("SELECT c FROM Company c WHERE c.user.userId = :userId")
    Optional<Company> findByUserId(@Param("userId") Long userId);

}
