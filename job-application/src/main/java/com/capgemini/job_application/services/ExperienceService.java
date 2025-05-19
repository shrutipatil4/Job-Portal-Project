package com.capgemini.job_application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.job_application.entities.Experience;

@Service

public interface ExperienceService {
	List<Experience> getExperienceByUserId(Long userId);
    Experience createExperience(Experience experience);
    void deleteExperienceById(Long experienceId);
    void deleteAllExperiencesByUserId(Long userId);
    List<Experience> getExpriences();
    Experience updateExperience(Long expId, Long userId, Experience updatedExperience);
}

