package com.capgemini.job_application.controllers;

import com.capgemini.job_application.dtos.CompanyDashBoardDto;

import com.capgemini.job_application.entities.Company;
import com.capgemini.job_application.services.CompanyService;
import com.capgemini.job_application.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

	
	private final CompanyService companyService;
	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<Company>> getAllCompanies() {
		log.info("Received request to fetch all companies");
		List<Company> companies = companyService.getAllCompanies();
		log.debug("Returning {} companies", companies.size());
		return ResponseEntity.status(HttpStatus.OK).body(companies);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Company> getCompany(@PathVariable Long id) {
		log.info("Received request to fetch company with ID: {}", id);
		Company company = companyService.getCompanyById(id);
		log.debug("Fetched company: {}", company);
		return ResponseEntity.status(HttpStatus.OK).body(company);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<Company> getCompanyByUserId(@PathVariable Long userId) {
		log.info("Received request to fetch company with user ID: {}", userId);
		Company company = companyService.getCompanyByUserId(userId);
		log.debug("Fetched company: {}", company);
		return ResponseEntity.status(HttpStatus.OK).body(company);
	}

	@PostMapping
	public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company, BindingResult result) {
		log.info("Received request to create company: {}", company);
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Company saved = companyService.createCompany(company);
		log.debug("Company created with ID: {}", saved.getCompanyId());
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Company> updateCompany(@PathVariable Long id, @Valid @RequestBody Company company,
			BindingResult result) {
		log.info("Received request to update company with ID: {}", id);
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		Company updated = companyService.updateCompany(id, company);
		log.debug("Updated company: {}", updated);
		return ResponseEntity.status(HttpStatus.OK).body(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
		log.info("Received request to delete company with ID: {}", id);
		companyService.deleteCompany(id);
		log.info("Company with ID {} successfully deleted", id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/{companyId}/companyDashBoard")
	public ResponseEntity<CompanyDashBoardDto> getDashboardForCompany(@PathVariable Long companyId) {
		return  ResponseEntity.status(HttpStatus.OK).body(userService.getDashboardForCompany(companyId));
	}
}
