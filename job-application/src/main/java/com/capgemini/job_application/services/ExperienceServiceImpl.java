package com.capgemini.job_application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.job_application.entities.Experience;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.exceptions.ExperienceNotFoundException;
import com.capgemini.job_application.exceptions.UserNotFoundException;
import com.capgemini.job_application.repositories.ExperienceRepository;
import com.capgemini.job_application.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExperienceServiceImpl implements ExperienceService {
	ExperienceRepository expRepo;
	UserRepository userRepository;

	@Autowired
	public ExperienceServiceImpl(ExperienceRepository expRepo, UserRepository userRepository) {
		super();
		this.expRepo = expRepo;
		this.userRepository =userRepository;
	}

	@Override
	public List<Experience> getExperienceByUserId(Long userId) {
        log.info("Fetching all experiences for user ID: {}", userId);
        
        List<Experience> experiences = expRepo.findByUserId(userId);
        log.debug("Found {} experiences for user ID: {}", experiences.size(), userId);
        return experiences;
    }

	@Override
	public Experience createExperience(Experience experience) {
		log.info("Creating new experience for user ID: {}", experience.getUser());

	    Long userId = experience.getUser().getUserId();
	    User existingUser = userRepository.findById(userId)
	        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

	    experience.setUser(existingUser);

	    Experience savedExperience = expRepo.save(experience);
	    log.debug("Experience created with ID: {}", savedExperience.getExperienceId());
	    return savedExperience;
    }

	@Override
	public void deleteExperienceById(Long experienceId) {
        log.info("Deleting experience with ID: {}", experienceId);
        expRepo.deleteById(experienceId);
        log.debug("Experience with ID {} deleted successfully", experienceId);
    }

	@Override
	public void deleteAllExperiencesByUserId(Long userId) {
        log.info("Deleting all experiences for user ID: {}", userId);
        List<Experience> experiences = expRepo.findByUserId(userId);
        expRepo.deleteAll(experiences);
        log.debug("All experiences for user ID {} deleted", userId);
    }

	@Override
	public Experience updateExperience(Long expId, Long userId, Experience updatedExperience) {
        log.info("Updating experience ID: {} for user ID: {}", expId, userId);
        Experience existingExp = expRepo.findById(expId)
            .orElseThrow(() -> {
                log.warn("Experience not found with ID: {}", expId);
                return new ExperienceNotFoundException("Experience with that ID does not exist");
            });

        existingExp.setStartDate(updatedExperience.getStartDate());
        existingExp.setEndDate(updatedExperience.getEndDate());
        existingExp.setCompanyName(updatedExperience.getCompanyName());
        existingExp.setRole(updatedExperience.getRole());

        Experience saved = expRepo.save(existingExp);
        log.debug("Experience with ID {} updated successfully", saved.getExperienceId());
        return saved;
    }

	@Override
	 public List<Experience> getExpriences() {
        log.info("Fetching all experiences from the repository");
        List<Experience> experiences = expRepo.findAll();
        log.debug("Found {} total experiences", experiences.size());
        return experiences;
    }
}
