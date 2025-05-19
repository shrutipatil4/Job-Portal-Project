package com.capgemini.job_application.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.job_application.entities.Experience;
import com.capgemini.job_application.services.ExperienceService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RequestMapping("/api/experiences")
@Slf4j
@RestController
public class ExperienceController {
	ExperienceService expService;

	@Autowired
	public ExperienceController(ExperienceService expService) {
		super();
		this.expService = expService;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Experience>> getExperienceByUserId(@PathVariable Long userId){
		log.info("Received request to fetch experience with ID: {}", userId);
		List<Experience> experiences = expService.getExperienceByUserId(userId);
		log.debug("Returning {} experiences", experiences.size()); 
		return ResponseEntity.status(HttpStatus.OK).body(experiences);
	}
	@GetMapping
	public ResponseEntity<List<Experience>> getExperiences(){
		List<Experience> experiences = expService.getExpriences();
		log.debug("Returning {} experiences", experiences.size()); 
		return ResponseEntity.status(HttpStatus.OK).body(experiences);
	}
	@PostMapping
	public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience exp,BindingResult result){
		if(result.hasErrors()) {
			log.error("Found error to create expience"); 
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Experience experience = expService.createExperience(exp);
		log.debug("Returning {} experience", experience); 
		return ResponseEntity.status(HttpStatus.CREATED).body(experience);
	}
	
	@DeleteMapping("/delete/experience/{expId}")
	public ResponseEntity<Void> deleteExperienceById(@PathVariable Long expId){
		log.info("Received request to delete experience with ID: {}", expId);
		expService.deleteExperienceById(expId);
		log.info("Experience with ID {} successfully deleted", expId); 
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Void> deleteAllExperiences(@PathVariable Long userId){
		log.info("Received request to delete all experiences with user ID: {}", userId);
		expService.deleteAllExperiencesByUserId(userId);
		log.info("Experiences with ID {} successfully deleted", userId); 
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/{expId}/update/{userId}")
	public ResponseEntity<Experience> updateExperience(@PathVariable Long expId,@PathVariable Long userId,@Valid @RequestBody Experience updatedExp,BindingResult result){
		if(result.hasErrors()) {
			log.error("Found error to update expience"); 
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		log.info("Received request to update experience with user ID: {}", userId);
		Experience experience = expService.updateExperience(expId, userId, updatedExp);
		log.info("Experience with ID {} successfully updated", expId); 
		return ResponseEntity.status(HttpStatus.OK).body(experience);
	}
	
}
