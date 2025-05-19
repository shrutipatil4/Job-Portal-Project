package com.capgemini.job_application.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApplicationInfoDto {
    private Long applicationId;
    private Long userId;
    private Long jobId;
    private String jobTitle;
    private LocalDate appliDate;
    private String status;
}

