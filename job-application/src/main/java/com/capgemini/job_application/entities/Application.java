package com.capgemini.job_application.entities;

import java.time.LocalDate;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "application")
public class Application{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "application_id")
	private Long applicationId;
	

	
	@Column(name = "applied_date")
	@NotNull(message = "Applied Date is mandatory")
	private LocalDate appliedDate;
	
	@Column(name = "status")
	@NotBlank(message = "Status is mandatory")
	private String status;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference(value = "user-application")
	private User user;

	@ManyToOne
	@JoinColumn(name = "job_id")
	@JsonBackReference(value = "job-application")
	private Job job;

	
	public Application() {
		super();
	}

	public Application(Long applicationId, User user, Job job, LocalDate appliedDate, String status) {
		super();
		this.applicationId = applicationId;
		this.user = user;
		this.job = job;
		this.appliedDate = appliedDate;
		this.status = status;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", jobId=" + job
				+ ", appliedDate=" + appliedDate + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicationId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Application other = (Application) obj;
		return Objects.equals(applicationId, other.applicationId);
	}
	
}