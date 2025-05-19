package com.capgemini.job_application.services;

import com.capgemini.job_application.entities.Company;
import com.capgemini.job_application.exceptions.CompanyNotFoundException;
import com.capgemini.job_application.repositories.CompanyRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        log.info("Fetching all companies");
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        log.info("Fetching company by ID: {}", id);
        return companyRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Company not found with ID: {}", id);
                    return new CompanyNotFoundException("Company not found with ID: " + id);
                });
    }

    @Override
    public Company createCompany(Company company) {
        log.info("Creating new company");
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        log.info("Updating company with ID: {}", id);
        Company existing = getCompanyById(id);

        existing.setCompanyName(company.getCompanyName());
        existing.setUser(company.getUser());
        existing.setCompanyDomain(company.getCompanyDomain());
        existing.setHeadOffice(company.getHeadOffice());

        return companyRepository.save(existing);
    }

    @Override
    public Company patchCompany(Long id, Company company) {
        log.info("Patching company with ID: {}", id);
        Company existing = getCompanyById(id);

        if (company.getCompanyName() != null) {
            existing.setCompanyName(company.getCompanyName());
        }
        if (company.getUser() != null) {
            existing.setUser(company.getUser());
        }
        if (company.getCompanyDomain() != null) {
            existing.setCompanyDomain(company.getCompanyDomain());
        }
        if (company.getHeadOffice() != null) {
            existing.setHeadOffice(company.getHeadOffice());
        }

        return companyRepository.save(existing);
    }

    @Override
    public void deleteCompany(Long id) {
        log.info("Deleting company with ID: {}", id);
        if (!companyRepository.existsById(id)) {
            log.warn("Cannot delete. Company not found with ID: {}", id);
            throw new CompanyNotFoundException("Company not found with ID: " + id);
        }
        companyRepository.deleteById(id);
        log.info("Company deleted successfully with ID: {}", id);
    }

    @Override
    public Company getCompanyByUserId(Long userId) {
        log.info("Fetching company by user ID: {}", userId);
        return companyRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("Company not found for user ID: {}", userId);
                    return new CompanyNotFoundException("Company not found with user ID: " + userId);
                });
    }
}
