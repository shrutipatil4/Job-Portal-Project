package com.capgemini.job_application.entities;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long companyId;


	@NotBlank(message = "Company name is required")
	@Column(name = "company_name", nullable = false, length = 100)
	private String companyName;

	@NotBlank(message = "Company domain is required")
	@Column(name = "company_domain", nullable = false, length = 100)
	private String companyDomain;

	@NotBlank(message = "Head office location is required")
	@Column(name = "head_office", nullable = false, length = 100)
	private String headOffice;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference(value = "user-company")
	private User user;

	
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference(value = "company-job")
	private List<Job> jobs = new ArrayList<>();


 	
	
	public Company() {
	}

	public Company(Long companyId, User user, String companyName, String companyDomain, String headOffice) {
		this.companyId = companyId;
		this.user = user;
		this.companyName = companyName;
		this.companyDomain = companyDomain;
		this.headOffice = headOffice;
	}


	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyDomain() {
		return companyDomain;
	}

	public void setCompanyDomain(String companyDomain) {
		this.companyDomain = companyDomain;
	}

	public String getHeadOffice() {
		return headOffice;
	}

	public void setHeadOffice(String headOffice) {
		this.headOffice = headOffice;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName
				+ ", companyDomain=" + companyDomain + ", headOffice=" + headOffice + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Company company = (Company) o;
		return Objects.equals(companyId, company.companyId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyId);
	}

}
