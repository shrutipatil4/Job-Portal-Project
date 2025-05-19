package com.capgemini.job_application.dtos;

import java.util.List;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDashBoardDto {

	

	private Long totalApplications;
    private Long totalJobs;
    private Long maleCount;
    private Long femaleCount;
    private List<Map<Long, Long>> applicationsPerJob;

}
