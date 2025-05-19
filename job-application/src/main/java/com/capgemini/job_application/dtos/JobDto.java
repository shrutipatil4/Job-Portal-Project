package com.capgemini.job_application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
	private Long jobId;
    private String jobTitle;
    private String companyName;
    private String jobLocation;
    private Double salary;
}
