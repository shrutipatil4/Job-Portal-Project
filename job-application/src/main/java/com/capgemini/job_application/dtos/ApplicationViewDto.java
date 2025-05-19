package com.capgemini.job_application.dtos;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class ApplicationViewDto {
    private Long applicationId;
    private String status;
    private LocalDate appliedDate;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String jobLocation;
    private Double salary;

    public ApplicationViewDto(Long applicationId, String status, Date appliedDate, Long jobId,
                              String jobTitle, String companyName, String jobLocation, Double salary) {
        this.applicationId = applicationId;
        this.status = status;
        this.appliedDate = appliedDate != null ? appliedDate.toLocalDate() : null;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.jobLocation = jobLocation;
        this.salary = salary;
    }
}


