package com.capgemini.job_application.controllers;

import com.capgemini.job_application.services.ChartService;

import com.capgemini.job_application.dtos.ChartDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/applications-by-location/{userId}")
    public List<ChartDTO> getApplicationsByLocationForUser(@PathVariable Long userId) {
        return chartService.getApplicationsByLocationForUser(userId);
    }

    @GetMapping("/applications-by-job-title/{userId}")
    public List<ChartDTO> getApplicationsByJobTitleForUser(@PathVariable Long userId) {
        return chartService.getApplicationsByJobTitleForUser(userId);
    }

    @GetMapping("/jobs-by-company-domain/{userId}")
    public List<ChartDTO> getJobsByCompanyDomainForUser(@PathVariable Long userId) {
        return chartService.getJobsByCompanyDomainForUser(userId);
    }
}


