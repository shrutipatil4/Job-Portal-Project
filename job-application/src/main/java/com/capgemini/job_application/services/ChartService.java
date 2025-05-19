package com.capgemini.job_application.services;

import com.capgemini.job_application.dtos.ChartDTO;
import com.capgemini.job_application.repositories.ApplicationRepository;
import com.capgemini.job_application.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ChartService(ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    public List<ChartDTO> getApplicationsByLocationForUser(Long userId) {
        return mapToChartDTO(applicationRepository.countApplicationsByLocationForUser(userId));
    }

    public List<ChartDTO> getApplicationsByJobTitleForUser(Long userId) {
        return mapToChartDTO(applicationRepository.countApplicationsByJobTitleForUser(userId));
    }

    public List<ChartDTO> getJobsByCompanyDomainForUser(Long userId) {
        return mapToChartDTO(jobRepository.countJobsByCompanyDomainForUser(userId));
    }

    private List<ChartDTO> mapToChartDTO(List<Object[]> rawData) {
        return rawData.stream()
            .filter(obj -> obj.length >= 2 && obj[0] instanceof String && obj[1] instanceof Long)
            .map(obj -> new ChartDTO((String) obj[0], (Long) obj[1]))
            .collect(Collectors.toList());
    }
}
