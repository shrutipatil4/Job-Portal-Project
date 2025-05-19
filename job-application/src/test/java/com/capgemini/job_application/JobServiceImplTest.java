package com.capgemini.job_application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.job_application.entities.Job;
import com.capgemini.job_application.entities.Company;
import com.capgemini.job_application.repositories.JobRepository;
import com.capgemini.job_application.services.JobServiceImpl;

class JobServiceImplTest {

	 @Mock
	    private JobRepository jobRepository;

	    @InjectMocks
	    private JobServiceImpl jobService;

	    private Job job;
	    
	    @Autowired
	    public JobServiceImplTest(JobRepository jobRepository, JobServiceImpl jobService, Job job,Company company) {
			this.jobRepository = jobRepository;
			this.jobService = jobService;
			this.job = job;
		}

		@BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        Company company=new Company();
	        company.setCompanyId(100L);
	        job = new Job(1L, company, "Developer", 80000.0,"Java Job", "Bangalore",LocalDate.of(2025, 6, 30),LocalDate.of(2025, 6, 30));
	    }

	    @Test
	    void testGetAllJobs() {
	        when(jobRepository.findAll()).thenReturn(Arrays.asList(job));
	        
	        List<Job> jobs = jobService.getAllJobs();
	        
	        assertEquals(1, jobs.size());
	        verify(jobRepository, times(1)).findAll();  
	    }


	    @Test
	    void testGetJobByIdFound() {
	        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
	        Job found = jobService.getJobById(1L);
	        assertEquals("Developer", found.getJobTitle());
	    }

	    @Test
	    void testGetJobByIdNotFound() {
	        when(jobRepository.findById(1L)).thenReturn(Optional.empty());
	        Exception ex = assertThrows(RuntimeException.class, () -> jobService.getJobById(1L));
	        assertTrue(ex.getMessage().contains("Job not found"));
	    }

	    @Test
	    void testCreateJob() {
	        when(jobRepository.save(job)).thenReturn(job);
	        Job created = jobService.createJob(job);
	        assertEquals("Developer", created.getJobTitle());
	    }

	    @Test
	    void testUpdateJob() {
	    	Company company=new Company();
	        company.setCompanyId(102L);
	        Job updated = new Job(1L, company, "Senior Developer", 90000.0,"Updated", "Mumbai",LocalDate.of(2025, 6, 30),LocalDate.of(2025, 6, 30));
	        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));
	        when(jobRepository.save(any(Job.class))).thenReturn(updated);

	        Job result = jobService.updateJob(1L, updated);
	        assertEquals("Senior Developer", result.getJobTitle());
	    }

	   
	    @Test
	    void testDeleteJobFound() {
	        when(jobRepository.existsById(1L)).thenReturn(true);
	        doNothing().when(jobRepository).deleteById(1L);
	        assertDoesNotThrow(() -> jobService.deleteJob(1L));
	    }

	    @Test
	    void testDeleteJobNotFound() {
	        when(jobRepository.existsById(1L)).thenReturn(false);
	        assertThrows(RuntimeException.class, () -> jobService.deleteJob(1L));
	    }
}
