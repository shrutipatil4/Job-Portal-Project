package com.capgemini.job_application.services;

import java.util.List;

import com.capgemini.job_application.entities.Company;

public interface CompanyService {

	List<Company> getAllCompanies();

	Company getCompanyById(Long id);

	Company createCompany(Company company);

	Company updateCompany(Long id, Company company);

	Company patchCompany(Long id, Company company);

	void deleteCompany(Long id);

	Company getCompanyByUserId(Long userId);


}
