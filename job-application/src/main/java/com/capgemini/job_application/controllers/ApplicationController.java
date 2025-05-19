package com.capgemini.job_application.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.capgemini.job_application.dtos.ApplicationInfoDto;
import com.capgemini.job_application.dtos.ApplicationViewDto;
import com.capgemini.job_application.entities.Application;
import com.capgemini.job_application.services.ApplicationService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/application")
@Slf4j
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@Valid @RequestBody Application application, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Invalid application data: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }
        log.info("Creating new application: {}", application);
        Application created = applicationService.createApplication(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        log.info("Fetching all applications");
        return ResponseEntity.status(HttpStatus.FOUND).body(applicationService.getAllApplication());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        log.info("Fetching application with ID: {}", id);
        return ResponseEntity.status(HttpStatus.FOUND).body(applicationService.getApplicationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id,  @RequestBody Application application, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Invalid application update data: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }
        log.info("Updating application with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.updateApplication(id, application));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Application> patchApplication(@PathVariable Long id, @Valid @RequestBody Application application, BindingResult result) {
        if (result.hasErrors()) {
            log.error("Invalid patch data: {}", result.getAllErrors());
            throw new IllegalArgumentException(result.getFieldErrors().toString());
        }
        log.info("Patching application with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(applicationService.patchApplication(id, application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.info("Deleting application with ID: {}", id);
        applicationService.deleteApplication(id);
        log.info("Successfully deleted application with ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @GetMapping("/applicant/{userId}")
    public ResponseEntity<List<Application>> findUserUserId(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(applicationService.findUserUserId(userId));
	}
    
    @GetMapping("/viewDto/{userId}")
    public ResponseEntity<List<ApplicationViewDto>> findApplicationsByUserId(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(applicationService.findApplicationsByUserId(userId));
	}
    
    @GetMapping("/applicationInfo")
    public ResponseEntity<List<ApplicationInfoDto>> getApplicationInfo() {
        return ResponseEntity.status(200).body(applicationService.getAllApplicationInfo());
    }

}
