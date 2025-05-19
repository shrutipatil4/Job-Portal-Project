package com.capgemini.job_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.job_application.entities.Qualification;
import com.capgemini.job_application.services.QualificationService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/qualifications")
@Slf4j
public class QualificationController {

	public final QualificationService qualificationService;

	@Autowired
	public QualificationController(QualificationService qualificationService) {
		this.qualificationService = qualificationService;
	}

	@GetMapping
	public ResponseEntity<List<Qualification>> getAllQualifications() {
		log.info("Received request to fetch all qualifications");
		List<Qualification> qualifications = qualificationService.getAllQualifications();
		log.debug("Returning {} qualifications", qualifications.size());
		return ResponseEntity.status(HttpStatus.OK).body(qualificationService.getAllQualifications());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Qualification> findQualificationById(@PathVariable Long id) {
		log.info("Received request to fetch qualification with ID: {}", id);
		Qualification qualification = qualificationService.findQualificationById(id);
		log.debug("Qualification fetched: {}", qualification);
		return ResponseEntity.status(HttpStatus.OK).body(qualificationService.findQualificationById(id));
	}

	@PostMapping
	public ResponseEntity<Qualification> createQualification(@Valid @RequestBody Qualification qualification,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		log.info("Received request to create qualification: {}", qualification);
		Qualification savedQualification = qualificationService.createQualification(qualification);
		log.debug("Qualification created with ID: {}", savedQualification.getQualificationId());
		return ResponseEntity.status(HttpStatus.CREATED).body(qualificationService.createQualification(qualification));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Qualification> updateQualification(@PathVariable Long id,
			@Valid @RequestBody Qualification qualification, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		log.info("Qualification with ID {} successfully updated", id);
		return ResponseEntity.status(HttpStatus.OK).body(qualificationService.updateQualification(id, qualification));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteQualification(@PathVariable Long id) {
		log.info("Received request to delete qualification with ID: {}", id);
		qualificationService.deleteQualification(id);
		log.info("Qualification with ID {} successfully deleted", id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Qualification> patchQualification(@PathVariable Long id,
			@Valid @RequestBody Qualification qualification, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Data Found");
		}
		log.info("Qualification with ID {} successfully patched", id);
		return ResponseEntity.status(HttpStatus.OK).body(qualificationService.patchQualification(id, qualification));
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<List<Qualification>> getQualificationsByUserId(@PathVariable Long userId) {
	    List<Qualification> qualifications = qualificationService.findByUserId(userId);
	    return ResponseEntity.ok(qualifications);
	}

	 @DeleteMapping("/user/{userId}")
	    public ResponseEntity<String> deleteQualificationsByUserId(@PathVariable Long userId) {
	        qualificationService.deleteByUserId(userId);
	        return ResponseEntity.ok("All qualifications deleted for userId: " + userId);
	    }

}
