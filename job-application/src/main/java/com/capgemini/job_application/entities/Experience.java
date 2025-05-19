package com.capgemini.job_application.entities;

import java.time.LocalDate;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "experience")
@Slf4j
public class Experience {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "experience_id")
	private Long experienceId;


	@NotBlank(message = "Role is mandatory")
	@Column(name = "role")
	private String role;

	@NotBlank(message = "Company name is mandatory")
	@Column(name = "company_name")
	private String companyName;

	@NotNull(message = "Start is mandatory")
	@Column(name = "start_date")
	private LocalDate startDate;

	@NotNull(message = "Date of end must be provided")
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name="user_id" ,referencedColumnName = "user_id")
	@JsonBackReference(value="user-experience")
	private User user;
	
	
	public Experience(Long experienceId, User user, String role, String companyName, LocalDate startDate, LocalDate endDate) {
		this.experienceId = experienceId;
		this.user = user;
		this.role = role;
		this.companyName = companyName;
		this.startDate = startDate;
		this.endDate = endDate;
		log.info("Experience object created with ID: {}", experienceId);
	}

	public Experience(User user, String role, String companyName, LocalDate startDate, LocalDate endDate) {
		this.user = user;
		this.role = role;
		this.companyName = companyName;
		this.startDate = startDate;
		this.endDate = endDate;
		log.info("Experience object created for user ID: {}", user);
	}

	public Experience() {
		log.debug("Empty Experience object created");
	}

	
	public Long getExperienceId() {
		return experienceId;
	}
	public void setExperienceId(Long experienceId) {
		this.experienceId = experienceId;
		log.debug("Set experienceId to {}", experienceId);
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
		log.debug("Set userId to {}", user);
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
		log.debug("Set role to {}", role);
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
		log.debug("Set companyName to {}", companyName);
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		log.debug("Set startDate to {}", startDate);
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		log.debug("Set endDate to {}", endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyName, endDate, experienceId, role, startDate, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Experience other = (Experience) obj;
		return Objects.equals(companyName, other.companyName)
			&& Objects.equals(endDate, other.endDate)
			&& Objects.equals(experienceId, other.experienceId)
			&& Objects.equals(role, other.role)
			&& Objects.equals(startDate, other.startDate)
			&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		log.debug("toString() called on Experience object");
		return "Experience [experienceId=" + experienceId + ", role=" + role
				+ ", companyName=" + companyName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
}
