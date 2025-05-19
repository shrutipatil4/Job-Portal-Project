package com.capgemini.job_application.services;

import java.util.List;

import com.capgemini.job_application.entities.Qualification;

public interface QualificationService {

	List<Qualification> getAllQualifications();
	List<Qualification> findByUserId(Long userId);
	void deleteByUserId(Long userId);
	
	Qualification findQualificationById(Long id);
	Qualification createQualification(Qualification qualification);
	Qualification updateQualification(Long id, Qualification qualification);
	Qualification patchQualification(Long id, Qualification qualification);
	void deleteQualification(Long id);
}
